package com.example.privateclinic.service.procedures.interfaces;

import com.example.privateclinic.models.Procedure;
import com.example.privateclinic.service.IGenericService;

import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * privateClinic.IProcedureService
 *
 * @author Nazar Palijchuk
 * @version IProcedureService: 1.0
 * @since 18.08.2021|17:02
 */

public interface IProcedureService extends IGenericService<Procedure>
{
	Optional<Procedure> findByName(String name);
}
