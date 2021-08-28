package com.example.privateclinic.controllers.ui;

import com.example.privateclinic.models.Application;
import com.example.privateclinic.models.Roles;
import com.example.privateclinic.security.CustomUserDetails;
import com.example.privateclinic.service.applications.impls.ApplicationServiceImpl;
import com.example.privateclinic.service.doctors.impls.DoctorServiceImpl;
import com.example.privateclinic.service.patients.impls.PatientServiceImpl;
import com.example.privateclinic.service.users.impls.UserServiceImpl;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * privateClinic.ApplicationUIController
 *
 * @author Nazar Palijchuk
 * @version ApplicationUIController: 1.0
 * @since 21.07.2021|22:30
 */

@Controller
@RequestMapping("ui/application")
public class ApplicationUIController
{
	@Autowired
	DoctorServiceImpl doctorService;

	@Autowired
	PatientServiceImpl patientService;

	@Autowired
	UserServiceImpl userService;

	@Autowired
	ApplicationServiceImpl applicationService;

	@RequestMapping("/showAll")
	public String showApplications(
			Model model,
			@AuthenticationPrincipal CustomUserDetails userDetails
	)
	{
		if (userDetails.getAuthorities()
				.contains(new SimpleGrantedAuthority(Roles.ROLE_ADMIN.toString())))
		{
			model.addAttribute("applications", applicationService.getAll());
		} else if (userDetails.getAuthorities()
				.contains(new SimpleGrantedAuthority(Roles.ROLE_PATIENT.toString())))
		{
			model.addAttribute("applications",
					applicationService.findPatientsApplications(
							userService.findUserByUsername(userDetails.getUsername()).get()
					));

		} else if (userDetails.getAuthorities()
				.contains(new SimpleGrantedAuthority(Roles.ROLE_DOCTOR.toString())))
		{
			model.addAttribute("applications",
					applicationService.findDoctorsApplications(
							userService.findUserByUsername(userDetails.getUsername()).get()
					));
		}

		return "applicationsPages/showAll";
	}

	@GetMapping(value = "/create/{doctorId}")
	public String create(
			@PathVariable String doctorId, Model model,
			@AuthenticationPrincipal CustomUserDetails userDetails
	)
	{
		model.addAttribute("doctor", doctorService.getById(doctorId));
		model.addAttribute("patient",
				patientService.findByUser(userService.findUserByUsername(userDetails.getUsername())
						.get()
				).get());

		return "applicationsPages/appForm";
	}

	@PreAuthorize(value = "hasRole('ROLE_PATIENT') and !hasRole('ROLE_ADMIN')")
	@PostMapping(value = "/create/{doctorId}")
	public String create(
			@PathVariable String doctorId,
			@RequestParam("patientId") String patientId,
			@RequestParam("date") String date,
			@RequestParam("time") String time
	)
	{

		try
		{
			Application application = Application.builder()
					.id(new ObjectId().toString())
					.patient(patientService.getById(patientId))
					.doctor(doctorService.getById(doctorId))
					.date(convertToLocalDateTime(
							new SimpleDateFormat("yyyy-MM-dd").parse(
									date), LocalTime.parse(time))
					)
					.build();

			applicationService.create(application);

			return "redirect:/ui/application/showAll";
		} catch (Exception e)
		{
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went " +
					"wrong");
		}
	}

	private LocalDateTime convertToLocalDateTime(Date date, LocalTime time)
	{
		return date.toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDateTime()
				.withHour(time.getHour())
				.withMinute(time.getMinute());
	}
}