package com.example.privateclinic.service.applications.impls;

import com.example.privateclinic.models.Application;
import com.example.privateclinic.repository.ApplicationRepository;
import com.example.privateclinic.service.applications.interfaces.IApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
}