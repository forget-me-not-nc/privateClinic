package com.example.privateclinic.controllers.ui;

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
	DoctorServiceImpl doctorService;

	@RequestMapping("/showAll")
	public String showDoctors(Model model, @AuthenticationPrincipal CustomUserDetails userDetails)
	{
		model.addAttribute("doctors", doctorService.getAll());

		return "doctorPages/showAll";
	}

	@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
	@RequestMapping("/create")
	public String create()
	{
		return "doctorPages/doctorForm";
	}

	@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
	@RequestMapping("/update/{id}")
	public String update(Model model, @PathVariable String id)
	{
		model.addAttribute("doctor", doctorService.getById(id));

		return "doctorPages/doctorForm";
	}
}