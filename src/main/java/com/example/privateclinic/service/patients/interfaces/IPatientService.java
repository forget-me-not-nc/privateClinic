package com.example.privateclinic.service.patients.interfaces;

import com.example.privateclinic.models.Patient;
import com.example.privateclinic.models.User;

import java.util.List;
import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * privateClinic.IPatientService
 *
 * @author Nazar Palijchuk
 * @version IPatientService: 1.0
 * @since 18.07.2021|20:52
 */

public interface IPatientService
{
	Optional<Patient> findByUser(User user);

	List<Patient> getAll();

	Patient getById(String id);

	void deleteById(String id);

	Patient create(Patient patient);

	Patient update(Patient patient);
}
