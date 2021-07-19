package com.example.privateclinic.service.customUserDetails;

import com.example.privateclinic.security.CustomUserDetails;
import com.example.privateclinic.service.users.interfaces.IUserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * privateClinic.MyCustomUserDetailsService
 *
 * @author Nazar Palijchuk
 * @version MyCustomUserDetailsService: 1.0
 * @since 03.07.2021|22:10
 */

@Service
public class MyCustomUserDetailsService implements UserDetailsService
{
	@Autowired
	IUserService userService;

	@Override
	@SneakyThrows
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException
	{
		return new CustomUserDetails(userService.findUserByUsername(s).orElseThrow(Exception::new));
	}
}