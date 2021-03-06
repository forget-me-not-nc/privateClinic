package com.example.privateclinic.service.doctors.impls;

import com.example.privateclinic.models.Doctor;
import com.example.privateclinic.models.User;
import com.example.privateclinic.repository.DoctorRepository;
import com.example.privateclinic.service.doctors.interfaces.IDoctorService;
import com.example.privateclinic.service.users.impls.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * privateClinic.IDoctorServiceImpl
 *
 * @author Nazar Palijchuk
 * @version IDoctorServiceImpl: 1.0
 * @since 18.07.2021|20:49
 */

@Service
public class DoctorServiceImpl implements IDoctorService
{
	@Autowired
	DoctorRepository doctorRepository;

	@Autowired
	UserServiceImpl userService;

	@Override
	public Optional<Doctor> findByUser(User user)
	{
		return doctorRepository.findByUser(user);
	}

	@Override
	public List<Doctor> getAll()
	{
		return doctorRepository.findAll();
	}

	@Override
	public Doctor getById(String id)
	{
		return doctorRepository.findById(id).orElse(null);
	}

	@Override
	public void deleteById(String id)
	{
		Doctor doctor = getById(id);

		userService.deleteByUsername(doctor.getUser().getUsername());

		doctorRepository.deleteById(id);
	}

	@Override
	public Doctor create(Doctor doctor)
	{
		return doctorRepository.insert(doctor);
	}

	@Override
	public Doctor update(Doctor doctor)
	{
		return doctorRepository.save(doctor);
	}

	@Override
	public Optional<Doctor> findByPhone(String phone)
	{
		return doctorRepository.findByPerson_TelephoneNumber(phone);
	}

	@Override
	public Optional<Doctor> findByEmail(String email)
	{
		return doctorRepository.findByPerson_Email(email);
	}
}