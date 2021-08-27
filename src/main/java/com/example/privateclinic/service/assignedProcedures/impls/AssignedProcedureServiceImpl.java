package com.example.privateclinic.service.assignedProcedures.impls;

import com.example.privateclinic.models.AssignedProcedure;
import com.example.privateclinic.repository.AssignedProcedureRepository;
import com.example.privateclinic.service.assignedProcedures.interfaces.IAssignedProcedureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * privateClinic.AssignedProcedureServiceImpl
 *
 * @author Nazar Palijchuk
 * @version AssignedProcedureServiceImpl: 1.0
 * @since 18.08.2021|17:10
 */

@Service
public class AssignedProcedureServiceImpl implements IAssignedProcedureService
{
	@Autowired
	AssignedProcedureRepository assignedProcedureRepository;

	@Override
	public List<AssignedProcedure> getAll()
	{
		return assignedProcedureRepository.findAll();
	}

	@Override
	public AssignedProcedure getById(String id)
	{
		return assignedProcedureRepository.findById(id).orElse(null);
	}

	@Override
	public void deleteById(String id)
	{
		assignedProcedureRepository.deleteById(id);
	}

	@Override
	public AssignedProcedure create(AssignedProcedure object)
	{
		return assignedProcedureRepository.save(object);
	}

	@Override
	public AssignedProcedure update(AssignedProcedure object)
	{
		return assignedProcedureRepository.save(object);
	}

	@Override
	public List<AssignedProcedure> findAllForPatient(String id)
	{
		return assignedProcedureRepository.findAllByPatient_Id(id);
	}
}