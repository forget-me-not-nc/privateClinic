package com.example.privateclinic.controllers.rest;

import com.example.privateclinic.models.Procedure;
import com.example.privateclinic.service.procedures.impls.ProcedureServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 * privateClinic.ProcedureRestController
 *
 * @author Nazar Palijchuk
 * @version ProcedureRestController: 1.0
 * @since 18.08.2021|18:35
 */

@RestController
@RequestMapping("api/procedures")
public class ProcedureRestController
{
	@Autowired
	ProcedureServiceImpl procedureService;

	@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/delete/{id}")
	void delete(HttpServletResponse response, @PathVariable String id) throws IOException
	{
		procedureService.deleteById(id);

		response.sendRedirect("/ui/procedures/showAll");
	}

	@GetMapping(value = "/getAllProcedures")
	List<String> getAll()
	{
		return procedureService.getAll().stream().map(Procedure::getProcedureName).collect(Collectors.toList());
	}

}