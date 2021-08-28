package com.example.privateclinic.controllers.ui;

import com.example.privateclinic.models.*;
import com.example.privateclinic.security.CustomUserDetails;
import com.example.privateclinic.service.doctors.impls.DoctorServiceImpl;
import com.example.privateclinic.service.users.impls.UserServiceImpl;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalTime;
import java.util.Collections;

/**
 * Created by IntelliJ IDEA.
 * privateClinic.DoctorUIController
 *
 * @author Nazar Palijchuk
 * @version DoctorUIController: 1.0
 * @since 21.07.2021|17:28
 */

@Controller
@RequestMapping("ui/doctor")
public class DoctorUIController
{
	@Autowired
	UserServiceImpl userService;

	@Autowired
	DoctorServiceImpl doctorService;

	@RequestMapping("/showAll")
	public String showDoctors(Model model, @AuthenticationPrincipal CustomUserDetails userDetails)
	{
		model.addAttribute("doctors", doctorService.getAll());

		return "doctorPages/showAll";
	}

	@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
	@GetMapping("/create")
	public String create(Model model)
	{
		model.addAttribute("doctor",
				Doctor.builder()
						.id("")
						.specialty("")
						.user(
								User.builder()
										.password("")
										.username("")
										.build())
						.endHour(LocalTime.of(23, 0))
						.startHour(LocalTime.of(5, 0))
						.person(
								Person.builder()
										.address("")
										.dateOfBirth(Date.from(Instant.now()))
										.fullName("")
										.sex(Sex.MALE)
										.telephoneNumber("")
										.email("")
										.build()
						).build());

		return "doctorPages/doctorForm";
	}

	@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
	@PostMapping(value = "/create")
	public String create(
			@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam("specialty") String specialty,
			@RequestParam("startTime") String startTime,
			@RequestParam("endTime") String endTime,
			@RequestParam("phone") String phone,
			@RequestParam("address") String address,
			@RequestParam("date") String dateOfBirth,
			@RequestParam("mail") String eMail,
			@RequestParam("fullName") String fullName,
			@RequestParam("sex") String sex
	)
	{
		try
		{
			User newUser = User.builder()
					.roles(Collections.singletonList(Roles.ROLE_DOCTOR))
					.username(username)
					.password(new BCryptPasswordEncoder().encode(password))
					.id(new ObjectId().toString())
					.build();

			userService.create(newUser);

			Doctor doctor = Doctor.builder()
					.id(new ObjectId().toString())
					.user(newUser)
					.person(
							Person.builder()
									.address(address)
									.dateOfBirth(new SimpleDateFormat("yyyy-MM-dd").parse(
											dateOfBirth))
									.sex(Sex.valueOf(sex))
									.email(eMail)
									.fullName(fullName)
									.telephoneNumber(phone)
									.build()
					)
					.specialty(specialty)
					.endHour(LocalTime.parse(endTime))
					.startHour(LocalTime.parse(startTime))
					.build();

			doctorService.create(doctor);

			return "redirect:/ui/doctor/showAll";
		} catch (Exception e)
		{
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went " +
					"wrong");
		}
	}

	@PreAuthorize(value = "hasRole('ROLE_ADMIN') or hasRole('ROLE_DOCTOR')")
	@GetMapping(value = "/update/{id}")
	public String update(Model model, @PathVariable String id)
	{
		model.addAttribute("doctor", doctorService.getById(id));

		return "doctorPages/doctorForm";
	}

	@PreAuthorize(value = "hasRole('ROLE_ADMIN') or hasRole('ROLE_DOCTOR')")
	@PostMapping(value = "/update/{id}")
	public String update(
			@PathVariable String id,
			@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam("specialty") String specialty,
			@RequestParam("startTime") String startTime,
			@RequestParam("endTime") String endTime,
			@RequestParam("phone") String phone,
			@RequestParam("address") String address,
			@RequestParam("date") String dateOfBirth,
			@RequestParam("mail") String eMail,
			@RequestParam("fullName") String fullName,
			@RequestParam("sex") String sex
	)
	{
		try
		{
			Doctor oldDoctor = doctorService.getById(id);

			User oldUser = userService.findUserByUsername(
					oldDoctor.getUser().getUsername()
			).orElse(null);

			assert oldUser != null;
			oldUser.setUsername(username);
			oldUser.setPassword(new BCryptPasswordEncoder().encode(password));

			userService.update(oldUser);

			oldDoctor.setPerson(
					Person.builder()
							.address(address)
							.dateOfBirth(new SimpleDateFormat("yyyy-MM-dd").parse(
									dateOfBirth))
							.sex(Sex.valueOf(sex))
							.email(eMail)
							.fullName(fullName)
							.telephoneNumber(phone)
							.build()
			);

			oldDoctor.setUser(
					oldUser
			);

			oldDoctor.setSpecialty(specialty);
			oldDoctor.setStartHour(LocalTime.parse(startTime));

			oldDoctor.setEndHour(LocalTime.parse(endTime));

			doctorService.update(oldDoctor);

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

			if (!userDetails.getAuthorities()
					.contains(new SimpleGrantedAuthority(Roles.ROLE_ADMIN.toString())))
			{
				userDetails.setUsername(username);
				userDetails.setPassword(new BCryptPasswordEncoder().encode(password));
			}

			return "redirect:/ui/doctor/showAll";
		} catch (Exception e)
		{
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went " +
					"wrong");
		}
	}
}