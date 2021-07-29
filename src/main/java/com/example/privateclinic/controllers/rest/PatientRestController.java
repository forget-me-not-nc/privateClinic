package com.example.privateclinic.controllers.rest;

import com.example.privateclinic.service.patients.impls.PatientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * privateClinic.PatientRestController
 *
 * @author Nazar Palijchuk
 * @version PatientRestController: 1.0
 * @since 19.07.2021|2:17
 */

@RestController
@RequestMapping("api/patient")
public class PatientRestController
{
	@Autowired
	PatientServiceImpl patientService;

	@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/delete/{id}")
	void delete(HttpServletResponse response, @PathVariable String id) throws IOException
	{
		patientService.deleteById(id);

		response.sendRedirect("/ui/patient/showAll");
	}

	@GetMapping(value = "/isPhoneValid")
	boolean isPhoneValid(@RequestParam String phone)
	{
		return patientService.findByPhone(phone).isPresent();
	}

	@GetMapping(value = "/isEmailValid")
	boolean isEmailValid(@RequestParam String email)
	{
		return patientService.findByEmail(email).isPresent();
	}
}