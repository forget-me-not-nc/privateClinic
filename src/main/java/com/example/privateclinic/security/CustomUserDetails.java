package com.example.privateclinic.security;

import com.example.privateclinic.models.Roles;
import com.example.privateclinic.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 * privateClinic.CustomUserDetails
 *
 * @author Nazar Palijchuk
 * @version CustomUserDetails: 1.0
 * @since 03.07.2021|21:26
 */

public class CustomUserDetails implements UserDetails
{
	private String username;
	private String password;
	private List<Roles> roles;

	public CustomUserDetails(User user)
	{
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.roles = user.getRoles();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities()
	{
		return roles.stream()
				.map(el -> new SimpleGrantedAuthority(el.toString()))
				.collect(Collectors.toList());
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public void setRoles(List<Roles> roles)
	{
		this.roles = roles;
	}

	@Override
	public String getPassword()
	{
		return password;
	}

	@Override
	public String getUsername()
	{
		return username;
	}

	@Override
	public boolean isAccountNonExpired()
	{
		return true;
	}

	@Override
	public boolean isAccountNonLocked()
	{
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired()
	{
		return true;
	}

	@Override
	public boolean isEnabled()
	{
		return true;
	}
}