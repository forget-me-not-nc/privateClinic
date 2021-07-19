package com.example.privateclinic.controllers.rest;

import com.example.privateclinic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by IntelliJ IDEA.
 * privateClinic.UserRestController
 *
 * @author Nazar Palijchuk
 * @version UserRestController: 1.0
 * @since 18.07.2021|21:21
 */

@RestController
@RequestMapping("/user")
public class UserRestController
{
	@Autowired
	UserRepository userRepository;

	@GetMapping("/exist")
	public boolean userExist(@RequestParam String username)
	{
		return userRepository.findByUsername(username).isPresent();
	}

}