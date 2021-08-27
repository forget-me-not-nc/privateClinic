package com.example.privateclinic.service.procedures.impls;

import com.example.privateclinic.models.Procedure;
import com.example.privateclinic.repository.ProcedureRepository;
import com.example.privateclinic.service.procedures.interfaces.IProcedureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * privateClinic.ProcedureServiceImpl
 *
 * @author Nazar Palijchuk
 * @version ProcedureServiceImpl: 1.0
 * @since 18.08.2021|17:03
 */

@Service
public class ProcedureServiceImpl implements IProcedureService
{
	@Autowired
	ProcedureRepository procedureRepository;

	@Override
	public List<Procedure> getAll()
	{
		return procedureRepository.findAll();
	}

	@Override
	public Procedure getById(String id)
	{
		return procedureRepository.findById(id).orElse(null);
	}

	@Override
	public void deleteById(String id)
	{
		procedureRepository.deleteById(id);
	}

	@Override
	public Procedure create(Procedure object)
	{
		return procedureRepository.save(object);
	}

	@Override
	public Procedure update(Procedure object)
	{
		return procedureRepository.save(object);
	}

	@Override
	public Optional<Procedure> findByName(String name)
	{
		return procedureRepository.findByProcedureName(name);
	}
}