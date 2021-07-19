package com.example.privateclinic.service.doctors.interfaces;

import com.example.privateclinic.models.Doctor;
import com.example.privateclinic.models.User;

import java.util.List;

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
	List<Doctor> getAll();

	Doctor getById(String id);

	void deleteById(String id);

	Doctor create(Doctor doctor);

	Doctor update(Doctor doctor);
}
