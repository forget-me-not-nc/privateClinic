package com.example.privateclinic.repository;

import com.example.privateclinic.models.Doctor;
import com.example.privateclinic.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * privateClinic.DoctorRepository
 *
 * @author Nazar Palijchuk
 * @version DoctorRepository: 1.0
 * @since 18.07.2021|20:47
 */

@Repository
public interface DoctorRepository extends MongoRepository<Doctor, String>
{
	Optional<Doctor> findByUser(User user);
}
