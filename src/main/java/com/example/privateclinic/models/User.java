package com.example.privateclinic.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * privateClinic.User
 *
 * @author Nazar Palijchuk
 * @version User: 1.0
 * @since 03.07.2021|21:27
 */

@Document("users")
@Builder
@Data
public class User
{
	private String id;
	private List<Roles> roles;
	private String username;
	private String password;
}