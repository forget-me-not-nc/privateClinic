package com.example.privateclinic.controllers.ui;

import com.example.privateclinic.Utils.LocalDateTimeUtils;
import com.example.privateclinic.models.AssignedProcedure;
import com.example.privateclinic.models.Patient;
import com.example.privateclinic.security.CustomUserDetails;
import com.example.privateclinic.service.assignedProcedures.impls.AssignedProcedureServiceImpl;
import com.example.privateclinic.service.patients.impls.PatientServiceImpl;
import com.example.privateclinic.service.procedures.impls.ProcedureServiceImpl;
import com.example.privateclinic.service.users.impls.UserServiceImpl;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.text.SimpleDateFormat;
import java.time.LocalTime;

/**
 * Created by IntelliJ IDEA.
 * privateClinic.AssignedProceduresUIController
 *
 * @author Nazar Palijchuk
 * @version AssignedProceduresUIController: 1.0
 * @since 18.08.2021|18:45
 */

@Controller
@RequestMapping("ui/assignedProcedures")
public class AssignedProceduresUIController
{
	@Autowired
	LocalDateTimeUtils localDateTimeUtils;

	@Autowired
	AssignedProcedureServiceImpl assignedProcedureService;

	@Autowired
	UserServiceImpl userService;

	@Autowired
	ProcedureServiceImpl procedureService;

	@Autowired
	PatientServiceImpl patientService;

	@PreAuthorize(value = "hasRole('ROLE_PATIENT') and !hasRole('ROLE_DOCTOR')")
	@GetMapping(value = "/showAll")
	public String show(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model)
	{
		Patient patient = patientService.findByUser(
				userService.findUserByUsername(customUserDetails.getUsername()).get()
		).get();

		model.addAttribute("procedures",
				assignedProcedureService.findAllForPatient(patient.getId()));

		return "assignedProcedures/showAll";
	}

	@PreAuthorize(value = "hasRole('ROLE_DOCTOR')")
	@GetMapping(value = "/create/{patientId}")
	public String create(@PathVariable String patientId, Model model)
	{
		model.addAttribute("patient", patientService.getById(patientId));

		return "assignedProcedures/form";
	}

	@PreAuthorize(value = "hasRole('ROLE_DOCTOR')")
	@PostMapping(value = "/create/{patientId}")
	public String create(
			@PathVariable String patientId,
			@RequestParam("procedureName") String name,
			@RequestParam("date") String date,
			@RequestParam("time") String time
	)
	{
		try
		{
			AssignedProcedure assignedProcedure = AssignedProcedure.builder()
					.id(new ObjectId().toString())
					.procedure(procedureService.findByName(name).get())
					.date(localDateTimeUtils.convertToLocalDateTime(
							new SimpleDateFormat("yyyy-MM-dd").parse(
									date),
							LocalTime.parse(time)
					))
					.patient(patientService.getById(patientId))
					.build();

			assignedProcedureService.create(assignedProcedure);
		} catch (Exception e)
		{
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went " +
					"wrong");
		}

		return "redirect:/ui/patient/showAll";
	}
}
