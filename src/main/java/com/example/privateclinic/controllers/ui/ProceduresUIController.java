package com.example.privateclinic.controllers.ui;

import com.example.privateclinic.models.*;
import com.example.privateclinic.service.procedures.impls.ProcedureServiceImpl;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.text.SimpleDateFormat;
import java.util.Collections;

/**
 * Created by IntelliJ IDEA.
 * privateClinic.ProceduresUIController
 *
 * @author Nazar Palijchuk
 * @version ProceduresUIController: 1.0
 * @since 18.08.2021|17:26
 */

@Controller
@RequestMapping("ui/procedures")
public class ProceduresUIController
{
	@Autowired
	ProcedureServiceImpl procedureService;

	@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/showAll")
	public String showPatients(Model model)
	{
		model.addAttribute("procedures", procedureService.getAll());
		return "procedures/showAll";
	}

	@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
	@GetMapping(value = "/create")
	public String create(Model model)
	{
		model.addAttribute("procedure", Procedure.builder()
				.id("")
				.procedureName("")
				.price(0d)
				.description("")
				.build());

		return "procedures/procedureForm";
	}

	@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
	@PostMapping(value = "/create")
	public String create(
			@RequestParam("name") String name,
			@RequestParam("description") String description,
			@RequestParam("price") String price
	)
	{
		try
		{
			Procedure procedure = Procedure.builder()
					.description(description)
					.id(new ObjectId().toString())
					.price(Double.parseDouble(price))
					.procedureName(name)
					.build();

			procedureService.create(procedure);

			return "redirect:/ui/procedures/showAll";
		}
		catch (Exception e)
		{
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went " +
					"wrong");
		}

	}

	@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
	@GetMapping(value = "/update/{id}")
	public String update(Model model, @PathVariable String id)
	{
		model.addAttribute("procedure", procedureService.getById(id));

		return "procedures/procedureForm";
	}

	@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
	@PostMapping(value = "/update/{id}")
	public String update(
			@PathVariable String id,
			@RequestParam("name") String name,
			@RequestParam("description") String description,
			@RequestParam("price") String price
	)
	{
		try
		{
			Procedure procedure = Procedure.builder()
					.description(description)
					.id(id)
					.price(Double.parseDouble(price))
					.procedureName(name)
					.build();

			procedureService.update(procedure);

			return "redirect:/ui/procedures/showAll";
		}
		catch (Exception e)
		{
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went " +
					"wrong");
		}

	}
}