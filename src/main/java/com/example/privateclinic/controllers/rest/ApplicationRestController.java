package com.example.privateclinic.controllers.rest;

import com.example.privateclinic.models.Application;
import com.example.privateclinic.models.Doctor;
import com.example.privateclinic.service.applications.impls.ApplicationServiceImpl;
import com.example.privateclinic.service.doctors.impls.DoctorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
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
	ApplicationServiceImpl applicationService;

	@Autowired
	DoctorServiceImpl doctorService;

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
				convertToLocalDateTime(new SimpleDateFormat("yyyy-MM-dd").parse(
						date), LocalTime.parse("00:00")),
				convertToLocalDateTime(new SimpleDateFormat("yyyy-MM-dd").parse(
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

	private LocalDateTime convertToLocalDateTime(Date date, LocalTime time)
	{
		return date.toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDateTime()
				.withHour(time.getHour())
				.withMinute(time.getMinute());
	}
}