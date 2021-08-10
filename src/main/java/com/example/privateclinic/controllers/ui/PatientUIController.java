package com.example.privateclinic.controllers.ui;

import com.example.privateclinic.models.*;
import com.example.privateclinic.security.CustomUserDetails;
import com.example.privateclinic.service.patients.impls.PatientServiceImpl;
import com.example.privateclinic.service.users.impls.UserServiceImpl;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * privateClinic.PatientUIController
 *
 * @author Nazar Palijchuk
 * @version PatientUIController: 1.0
 * @since 21.07.2021|17:30
 */

@Controller
@RequestMapping("ui/patient")
public class PatientUIController
{
	@Autowired
	UserServiceImpl userService;

	@Autowired
	PatientServiceImpl patientService;

	@PreAuthorize(value = "hasRole('ROLE_ADMIN') or hasRole('ROLE_DOCTOR')")
	@RequestMapping(value = "/showAll")
	public String showPatients(Model model)
	{
		model.addAttribute("patients", patientService.getAll());
		return "patientPages/showAll";
	}

	@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
	@GetMapping(value = "/create")
	public String create(Model model)
	{
		model.addAttribute("patient",
				Patient.builder()
						.id("")
						.user(
								User.builder()
										.username("")
										.password("")
										.build()
						)
						.person(
								Person.builder()
										.email("")
										.telephoneNumber("")
										.sex(Sex.MALE)
										.fullName("")
										.dateOfBirth(Date.from(Instant.now()))
										.address("")
										.build()
						)
						.build());

		return "patientPages/patientForm";
	}

	@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
	@PostMapping(value = "/create")
	public String create(
			@RequestParam("username") String username,
			@RequestParam("password") String password,
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
					.roles(Collections.singletonList(Roles.ROLE_PATIENT))
					.username(username)
					.password(new BCryptPasswordEncoder().encode(password))
					.id(new ObjectId().toString())
					.build();

			userService.create(newUser);

			Patient patient = Patient.builder()
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
					.user(newUser)
					.id(new ObjectId().toString())
					.build();

			patientService.create(patient);

			return "redirect:/ui/patient/showAll";
		} catch (Exception e)
		{
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went " +
					"wrong");
		}
	}

	@PreAuthorize(value = "hasRole('ROLE_ADMIN') or hasRole('ROLE_PATIENT')")
	@GetMapping("/update/{id}")
	public String update(Model model, @PathVariable String id)
	{
		model.addAttribute("patient", patientService.getById(id));

		return "patientPages/patientForm";
	}

	@PreAuthorize(value = "hasRole('ROLE_ADMIN') or hasRole('ROLE_PATIENT')")
	@PostMapping("/update/{id}")
	public String update(
			@PathVariable String id,
			@RequestParam("username") String username,
			@RequestParam("password") String password,
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
			Patient oldPatient = patientService.getById(id);

			User oldUser = userService.findUserByUsername(
					oldPatient.getUser().getUsername()
			).orElse(null);

			assert oldUser != null;
			oldUser.setUsername(username);
			oldUser.setPassword(new BCryptPasswordEncoder().encode(password));

			userService.update(oldUser);

			oldPatient.setPerson(
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

			oldPatient.setUser(
					oldUser
			);

			patientService.update(oldPatient);

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

			if (!userDetails.getAuthorities().contains(Roles.ROLE_ADMIN))
			{
				userDetails.setUsername(username);
				userDetails.setPassword(new BCryptPasswordEncoder().encode(password));
			}

			return "redirect:/main";
		} catch (Exception e)
		{
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went " +
					"wrong");
		}
	}
}