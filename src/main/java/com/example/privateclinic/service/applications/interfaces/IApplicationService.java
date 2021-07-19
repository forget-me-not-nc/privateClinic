package com.example.privateclinic.service.applications.interfaces;

import com.example.privateclinic.models.Application;
import com.example.privateclinic.models.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * privateClinic.IApplicationService
 *
 * @author Nazar Palijchuk
 * @version IApplicationService: 1.0
 * @since 18.07.2021|20:43
 */

public interface IApplicationService
{
	Application getById(String id);

	void deleteById(String id);

	Application create(Application user);

	Application update(Application user);
}
