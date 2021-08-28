package com.example.privateclinic.repository;

import com.example.privateclinic.models.AssignedProcedure;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * privateClinic.AssignedProcedureRepository
 *
 * @author Nazar Palijchuk
 * @version AssignedProcedureRepository: 1.0
 * @since 18.08.2021|16:58
 */

@Repository
public interface AssignedProcedureRepository extends MongoRepository<AssignedProcedure, String>
{
	List<AssignedProcedure> findAllByPatient_Id(String id);
}
