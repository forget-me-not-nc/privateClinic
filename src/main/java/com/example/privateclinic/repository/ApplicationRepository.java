package com.example.privateclinic.repository;

import com.example.privateclinic.models.Application;
import com.example.privateclinic.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * privateClinic.ApplicationRepository
 *
 * @author Nazar Palijchuk
 * @version ApplicationRepository: 1.0
 * @since 18.07.2021|20:39
 */

@Repository
public interface ApplicationRepository extends MongoRepository<Application, String>
{
	List<Application> findByPatient_User(User user);

	@Query("{\n" +
			"    $and: [\n" +
			"        {\n" +
			"            'doctor._id': ?0\n" +
			"        },\n" +
			"        {\n" +
			"            date : \n" +
			"            {\n" +
			"                $gte: {$date:?1},\n" +
			"                $lte: {$date:?2},\n" +
			"            } \n" +
			"        }\n" +
			"    ]\n" +
			"}")
	List<Application> findAvailableTime(String id, LocalDateTime start, LocalDateTime end);

	List<Application> findByDoctor_User(User user);
}
