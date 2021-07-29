package com.example.privateclinic.repository;

import com.example.privateclinic.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * privateClinic.UserRepository
 *
 * @author Nazar Palijchuk
 * @version UserRepository: 1.0
 * @since 03.07.2021|21:32
 */

@Repository
public interface UserRepository extends MongoRepository<User, String>
{
	Optional<User> findByUsername(String username);

	void deleteByUsername(String username);
}