package com.example.privateclinic.service.applications.impls;

import com.example.privateclinic.models.Application;
import com.example.privateclinic.models.User;
import com.example.privateclinic.repository.ApplicationRepository;
import com.example.privateclinic.service.applications.interfaces.IApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * privateClinic.ApplicationServiceImpl
 *
 * @author Nazar Palijchuk
 * @version ApplicationServiceImpl: 1.0
 * @since 18.07.2021|20:44
 */

@Repository
public class ApplicationServiceImpl implements IApplicationService
{
	@Autowired
	ApplicationRepository applicationRepository;

	@Override
	public List<Application> getAll()
	{
		return applicationRepository.findAll();
	}

	@Override
	public Application getById(String id)
	{
		return applicationRepository.findById(id).orElse(null);
	}

	@Override
	public void deleteById(String id)
	{
		applicationRepository.deleteById(id);
	}

	@Override
	public Application create(Application application)
	{
		return applicationRepository.save(application);
	}

	@Override
	public Application update(Application application)
	{
		return applicationRepository.save(application);
	}

	@Override
	public List<Application> findDoctorsApplications(User user)
	{
		return applicationRepository.findByDoctor_User(user);
	}

	@Override
	public List<Application> findPatientsApplications(User user)
	{
		return applicationRepository.findByPatient_User(user);
	}

	@Override
	public List<Application> findAvailableTime(
			String id, LocalDateTime start, LocalDateTime end
	)
	{
		return applicationRepository.findAvailableTime(id, start, end);
	}


}