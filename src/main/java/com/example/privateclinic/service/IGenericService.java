package com.example.privateclinic.service;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * privateClinic.IGenericService
 *
 * @author Nazar Palijchuk
 * @version IGenericService: 1.0
 * @since 21.07.2021|17:58
 */

public interface IGenericService<T>
{
	List<T> getAll();

	T getById(String id);

	void deleteById(String id);

	T create(T user);

	T update(T user);
}
