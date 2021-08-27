package com.example.privateclinic.repository;

import com.example.privateclinic.models.Procedure;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * privateClinic.ProcedureRepository
 *
 * @author Nazar Palijchuk
 * @version ProcedureRepository: 1.0
 * @since 18.08.2021|16:40
 */

@Repository
public interface ProcedureRepository extends MongoRepository<Procedure, String>
{
	Optional<Procedure> findByProcedureName(String name);
}
