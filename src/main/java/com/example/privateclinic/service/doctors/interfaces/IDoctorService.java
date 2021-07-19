package com.example.privateclinic.service.doctors.interfaces;

import com.example.privateclinic.models.Doctor;
import com.example.privateclinic.models.User;

import java.util.List;
import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * privateClinic.IDoctorService
 *
 * @author Nazar Palijchuk
 * @version IDoctorService: 1.0
 * @since 18.07.2021|20:48
 */

public interface IDoctorService
{
	Optional<Doctor> findByUser(User user);

	List<Doctor> getAll();

	Doctor getById(String id);

	void deleteById(String id);

	Doctor create(Doctor doctor);

	Doctor update(Doctor doctor);
}
