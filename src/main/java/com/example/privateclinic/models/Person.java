package com.example.privateclinic.models;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * privateClinic.Person
 *
 * @author Nazar Palijchuk
 * @version Person: 1.0
 * @since 09.07.2021|1:01
 */

@Builder
@Data
public class Person
{
	private String fullName;
	private Date dateOfBirth;
	private String address;
	private String telephoneNumber;
	private String eMail;
	private Sex sex;
}