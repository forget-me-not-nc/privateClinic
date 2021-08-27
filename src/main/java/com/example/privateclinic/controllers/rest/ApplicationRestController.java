package com.example.privateclinic.controllers.rest;

import com.example.privateclinic.Utils.LocalDateTimeUtils;
import com.example.privateclinic.models.Application;
import com.example.privateclinic.models.Doctor;
import com.example.privateclinic.service.applications.impls.ApplicationServiceImpl;
import com.example.privateclinic.service.doctors.impls.DoctorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * privateClinic.ApplicationRestController
 *
 * @author Nazar Palijchuk
 * @version ApplicationRestController: 1.0
 * @since 10.08.2021|21:08
 */

@RestController
@RequestMapping("api/application")
public class ApplicationRestController
{
	@Autowired
	LocalDateTimeUtils localDateTimeUtils;

	@Autowired
	ApplicationServiceImpl applicationService;

	@Autowired
	DoctorServiceImpl doctorService;

	@PreAuthorize(value = "hasRole('ROLE_DOCTOR')")
	@RequestMapping(value = "/delete/{id}")
	void delete(HttpServletResponse response, @PathVariable String id) throws IOException
	{
		applicationService.deleteById(id);

		response.sendRedirect("/ui/application/showAll");
	}

	@GetMapping(value = "/getAvailableTime")
	public List<String> getAvailableTime(
			@RequestParam String date,
			@RequestParam String doctorId
	) throws
			ParseException
	{
		List<String> time = new ArrayList<>();

		Doctor doctor = doctorService.getById(doctorId);

		List<Application> applications = applicationService.findAvailableTime(
				doctorId,
				localDateTimeUtils.convertToLocalDateTime(new SimpleDateFormat("yyyy-MM-dd").parse(
						date), LocalTime.parse("00:00")),
				localDateTimeUtils.convertToLocalDateTime(new SimpleDateFormat("yyyy-MM-dd").parse(
						date), LocalTime.parse("23:59"))
		);

		for (LocalTime iter = doctor.getStartHour(); iter != doctor.getEndHour(); iter =
				iter.plusMinutes(30))
		{
			LocalTime finalIter = iter;
			if (applications.stream().noneMatch(
					el -> el.getDate().getHour() == finalIter.getHour() &&
							el.getDate().getMinute() == finalIter.getMinute()
			))
			{
				time.add(iter.getHour() + ":" + (iter.getMinute() == 0 ? "00" : "30"));
			}
		}

		return time;
	}

	@Scheduled(cron = "0 0 23 * * *")
	public void clearApps()
	{
		List<Application> applications = applicationService.getAll();

		applications.forEach(el -> {
			if(el.getDate().isBefore(LocalDateTime.now().minusDays(30)))
			{
				applicationService.deleteById(el.getId());
			}
		});


	}
}