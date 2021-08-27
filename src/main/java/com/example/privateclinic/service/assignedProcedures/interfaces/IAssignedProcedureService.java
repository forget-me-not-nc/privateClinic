package com.example.privateclinic.service.assignedProcedures.interfaces;

import com.example.privateclinic.models.AssignedProcedure;
import com.example.privateclinic.service.IGenericService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * privateClinic.IAssignedProcedureService
 *
 * @author Nazar Palijchuk
 * @version IAssignedProcedureService: 1.0
 * @since 18.08.2021|17:09
 */

@Service
public interface IAssignedProcedureService extends IGenericService<AssignedProcedure>
{
	List<AssignedProcedure> findAllForPatient(String id);
}
