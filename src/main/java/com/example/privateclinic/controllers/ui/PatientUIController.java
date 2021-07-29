package com.example.privateclinic.controllers.ui;

import com.example.privateclinic.service.patients.impls.PatientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
	PatientServiceImpl patientService;

	@PreAuthorize(value = "hasRole('ROLE_ADMIN') or hasRole('ROLE_DOCTOR')")
	@RequestMapping(value = "/showAll")
	public String showPatients(Model model)
	{
		model.addAttribute("patients", patientService.getAll());
		return "patientPages/showAll";
	}

	@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/create")
	public String create()
	{
		return "patientPages/patientForm";
	}
}