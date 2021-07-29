package com.example.privateclinic.controllers.rest;

import com.example.privateclinic.service.doctors.impls.DoctorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * privateClinic.DoctorRestController
 *
 * @author Nazar Palijchuk
 * @version DoctorRestController: 1.0
 * @since 19.07.2021|4:12
 */

@RestController
@RequestMapping("api/doctor")
public class DoctorRestController
{
	@Autowired
	DoctorServiceImpl doctorService;

	@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/delete/{id}")
	void delete(HttpServletResponse response, @PathVariable String id) throws IOException
	{
		doctorService.deleteById(id);

		response.sendRedirect("/ui/doctor/showAll");
	}

	@GetMapping(value = "/isPhoneValid")
	boolean isPhoneValid(@RequestParam String phone)
	{
		return doctorService.findByPhone(phone).isPresent();
	}

	@GetMapping(value = "/isEmailValid")
	boolean isEmailValid(@RequestParam String email)
	{
		return doctorService.findByEmail(email).isPresent();
	}
}