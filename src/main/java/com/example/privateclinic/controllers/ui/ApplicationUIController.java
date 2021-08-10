package com.example.privateclinic.controllers.ui;

import com.example.privateclinic.models.Roles;
import com.example.privateclinic.security.CustomUserDetails;
import com.example.privateclinic.service.applications.impls.ApplicationServiceImpl;
import com.example.privateclinic.service.doctors.impls.DoctorServiceImpl;
import com.example.privateclinic.service.users.impls.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
	public String create(@PathVariable String doctorId, Model model)
	{
		model.addAttribute("doctor", doctorService.getById(doctorId));

		return "applicationsPages/appForm";
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