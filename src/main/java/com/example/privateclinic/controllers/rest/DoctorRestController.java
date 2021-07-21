package com.example.privateclinic.controllers.rest;

import com.example.privateclinic.security.CustomUserDetails;
import com.example.privateclinic.service.doctors.impls.DoctorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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

	@RequestMapping("/showAll")
	public String showAllDoctors(Model model, @AuthenticationPrincipal CustomUserDetails userDetails)
	{
		model.addAttribute("doctors", doctorService.getAll());

		return "doctorPages/showAll";
	}

	@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/delete/{id}")
	public String delete(@PathVariable String id)
	{
		doctorService.deleteById(id);

		return "redirect:/doctor/showAll";
	}
}