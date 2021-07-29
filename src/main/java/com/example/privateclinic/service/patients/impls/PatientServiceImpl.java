package com.example.privateclinic.service.patients.impls;

import com.example.privateclinic.models.Patient;
import com.example.privateclinic.models.User;
import com.example.privateclinic.repository.PatientRepository;
import com.example.privateclinic.service.patients.interfaces.IPatientService;
import com.example.privateclinic.service.users.impls.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * privateClinic.IPatientServiceImpl
 *
 * @author Nazar Palijchuk
 * @version IPatientServiceImpl: 1.0
 * @since 18.07.2021|21:00
 */

@Service
public class PatientServiceImpl implements IPatientService
{
	@Autowired
	PatientRepository patientRepository;

	@Autowired
	UserServiceImpl userService;

	@Override
	public Optional<Patient> findByUser(User user)
	{
		return patientRepository.findByUser(user);
	}

	@Override
	public List<Patient> getAll()
	{
		return patientRepository.findAll();
	}

	@Override
	public Patient getById(String id)
	{
		return patientRepository.findById(id).orElse(null);
	}

	@Override
	public void deleteById(String id)
	{
		Patient patient = getById(id);

		userService.deleteByUsername(patient.getUser().getUsername());

		patientRepository.deleteById(id);
	}

	@Override
	public Patient create(Patient patient)
	{
		return patientRepository.save(patient);
	}

	@Override
	public Patient update(Patient patient)
	{
		return patientRepository.save(patient);
	}

	@Override
	public Optional<Patient> findByPhone(String phone)
	{
		return patientRepository.findByPerson_TelephoneNumber(phone);
	}

	@Override
	public Optional<Patient> findByEmail(String email)
	{
		return patientRepository.findByPerson_Email(email);
	}

}