package com.example.privateclinic.service.applications.interfaces;

import com.example.privateclinic.models.Application;
import com.example.privateclinic.models.User;
import com.example.privateclinic.service.IGenericService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * privateClinic.IApplicationService
 *
 * @author Nazar Palijchuk
 * @version IApplicationService: 1.0
 * @since 18.07.2021|20:43
 */

public interface IApplicationService extends IGenericService<Application>
{
	Application getById(String id);

	void deleteById(String id);

	Application create(Application user);

	Application update(Application user);

	List<Application> findDoctorsApplications(User user);

	List<Application> findPatientsApplications(User user);

	List<Application> findAvailableTime(String id, LocalDateTime start, LocalDateTime end);

	List<Application> findByDoctorId(String id);
}
