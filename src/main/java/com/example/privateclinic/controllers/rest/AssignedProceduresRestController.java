package com.example.privateclinic.controllers.rest;

import com.example.privateclinic.models.Application;
import com.example.privateclinic.models.AssignedProcedure;
import com.example.privateclinic.service.assignedProcedures.impls.AssignedProcedureServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * privateClinic.AssignedProceduresRestController
 *
 * @author Nazar Palijchuk
 * @version AssignedProceduresRestController: 1.0
 * @since 19.08.2021|21:29
 */

@RestController
@RequestMapping("api/assignedProcedures")
public class AssignedProceduresRestController
{
	@Autowired
	AssignedProcedureServiceImpl assignedProcedureService;

	@Scheduled(cron = "0 0 23 * * *")
	public void clearApps()
	{
		List<AssignedProcedure> applications = assignedProcedureService.getAll();

		applications.forEach(el -> {
			if(el.getDate().isBefore(LocalDateTime.now().minusDays(15)))
			{
				assignedProcedureService.deleteById(el.getId());
			}
		});


	}
}