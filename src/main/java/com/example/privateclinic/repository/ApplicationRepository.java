package com.example.privateclinic.repository;

import com.example.privateclinic.models.Application;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

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
}
