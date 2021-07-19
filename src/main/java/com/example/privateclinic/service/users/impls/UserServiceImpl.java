package com.example.privateclinic.service.users.impls;

import com.example.privateclinic.models.User;
import com.example.privateclinic.repository.UserRepository;
import com.example.privateclinic.service.users.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * privateClinic.UserServiceImpl
 *
 * @author Nazar Palijchuk
 * @version UserServiceImpl: 1.0
 * @since 03.07.2021|21:40
 */

@Service
public class UserServiceImpl implements IUserService
{
	@Autowired
	UserRepository userRepository;

	@Override
	public Optional<User> findUserByUsername(String username)
	{
		return userRepository.findByUsername(username);
	}

	@Override
	public User getById(String id)
	{
		return userRepository.findById(id).orElse(null);
	}

	@Override
	public void deleteById(String id)
	{
		userRepository.deleteById(id);
	}

	@Override
	public User create(User user)
	{
		return userRepository.save(user);
	}

	@Override
	public User update(User user)
	{
		return userRepository.save(user);
	}

}