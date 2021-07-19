package com.example.privateclinic.repository;

import com.example.privateclinic.models.Patient;
import com.example.privateclinic.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * privateClinic.PatientRepository
 *
 * @author Nazar Palijchuk
 * @version PatientRepository: 1.0
 * @since 18.07.2021|20:51
 */

@Repository
public interface PatientRepository extends MongoRepository<Patient, String>
{
	Optional<Patient> findByUser(User user);
}
