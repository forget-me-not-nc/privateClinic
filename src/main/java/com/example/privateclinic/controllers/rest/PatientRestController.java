package com.example.privateclinic.controllers.rest;

import com.example.privateclinic.service.patients.impls.PatientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by IntelliJ IDEA.
 * privateClinic.PatientRestContoller
 *
 * @author Nazar Palijchuk
 * @version PatientRestContoller: 1.0
 * @since 19.07.2021|2:17
 */

@Controller
@RequestMapping("patient/")
public class PatientRestController
{
	@Autowired
	PatientServiceImpl patientService;

	@PreAuthorize(value = "hasRole('ROLE_ADMIN') or hasRole('ROLE_DOCTOR')")
	@RequestMapping(value = "/showAll")
	public String showAllPatients(Model model)
	{
		model.addAttribute("patients", patientService.getAll());
		return "patientPages/showAll";
	}

	@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/delete/{id}")
	public String delete(@PathVariable String id)
	{
		patientService.deleteById(id);

		return "redirect:/patient/showAll";
	}
}