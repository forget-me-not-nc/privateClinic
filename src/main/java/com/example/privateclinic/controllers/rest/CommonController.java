package com.example.privateclinic.controllers.rest;

import com.example.privateclinic.models.*;
import com.example.privateclinic.security.CustomUserDetails;
import com.example.privateclinic.service.doctors.impls.DoctorServiceImpl;
import com.example.privateclinic.service.patients.impls.PatientServiceImpl;
import com.example.privateclinic.service.users.impls.UserServiceImpl;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * privateClinic.CommonController
 *
 * @author Nazar Palijchuk
 * @version CommonController: 1.0
 * @since 07.07.2021|21:15
 */

@Controller
public class CommonController
{
	@Autowired
	PatientServiceImpl patientService;

	@Autowired
	DoctorServiceImpl doctorService;

	@Autowired
	UserServiceImpl userService;

	@RequestMapping(value = "/login")
	public String login()
	{
		return "loginRegisterPages/login";
	}

	@GetMapping(value = "/registration")
	public String register()
	{
		return "loginRegisterPages/registration";
	}

	@PostMapping(value = "/registration")
	public String tryRegister(
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
			User user = userService.create(User.builder()
					.password(new BCryptPasswordEncoder().encode(password))
					.username(username)
					.roles(Collections.singletonList(
							Roles.ROLE_PATIENT
					))
					.build());

			Patient patient = Patient.builder()
					.id(new ObjectId().toString())
					.person(
							Person.builder()
									.address(address)
									.dateOfBirth(new SimpleDateFormat("yyyy-MM-dd").parse(
											dateOfBirth))
									.email(eMail)
									.fullName(fullName)
									.telephoneNumber(phone)
									.sex(Sex.valueOf(sex))
									.build()
					)
					.user(user)
					.build();

			patientService.create(patient);

			return "redirect:/login";
		} catch (Exception e)
		{
			return "redirect:/loginRegisterPages/registration";
		}
	}

	@RequestMapping(value = "/main")
	public String mainPage(@AuthenticationPrincipal CustomUserDetails userDetails, Model model)
	{
		if (userDetails.getAuthorities()
				.contains(new SimpleGrantedAuthority(Roles.ROLE_ADMIN.toString())))
		{
			model.addAttribute("user", null);
		} else if (userDetails.getAuthorities()
				.contains(new SimpleGrantedAuthority(Roles.ROLE_PATIENT.toString())))
		{
			Optional<User> user = userService.findUserByUsername(userDetails.getUsername());

			if (user.isPresent())
			{
				Optional<Patient> patient = patientService.findByUser(user.get());

				if (patient.isPresent())
				{
					model.addAttribute("user", patient.get().getPerson());
					model.addAttribute("id", patient.get().getId());
				} else throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
			} else throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);

		} else if (userDetails.getAuthorities()
				.contains(new SimpleGrantedAuthority(Roles.ROLE_DOCTOR.toString())))
		{
			Optional<User> user = userService.findUserByUsername(userDetails.getUsername());

			if (user.isPresent())
			{
				Optional<Doctor> doctor = doctorService.findByUser(user.get());

				if (doctor.isPresent())
				{
					model.addAttribute("user", doctor.get().getPerson());
					model.addAttribute("doctorInfo", doctor.get());
					model.addAttribute("id", doctor.get().getId());
				} else throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
			} else throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		} else throw new ResponseStatusException(HttpStatus.NOT_FOUND);

		return "main-page";
	}

}