package com.example.privateclinic.controllers.rest;

import com.example.privateclinic.service.doctors.impls.DoctorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by IntelliJ IDEA.
 * privateClinic.DoctorRestController
 *
 * @author Nazar Palijchuk
 * @version DoctorRestController: 1.0
 * @since 19.07.2021|4:12
 */

@Controller
@RequestMapping("doctor/")
public class DoctorRestController
{
	@Autowired
	DoctorServiceImpl doctorService;

	@PreAuthorize(value = "hasRole('ROLE_ADMIN') or hasRole('ROLE_PATIENT')")
	@RequestMapping("/showAll")
	public String showAllDoctors(Model model)
	{
		model.addAttribute("doctors", doctorService.getAll());

		return "doctorPages/showAll";
	}
}