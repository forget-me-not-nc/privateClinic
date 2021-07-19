package com.example.privateclinic.service.users.interfaces;

import com.example.privateclinic.models.User;

import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * privateClinic.IUserService
 *
 * @author Nazar Palijchuk
 * @version IUserService: 1.0
 * @since 03.07.2021|21:38
 */

public interface IUserService
{
	Optional<User> findUserByUsername(String username);

	User getById(String id);

	void deleteById(String id);

	User create(User user);

	User update(User user);
}
