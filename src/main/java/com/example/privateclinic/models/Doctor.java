package com.example.privateclinic.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalTime;

/**
 * Created by IntelliJ IDEA.
 * privateClinic.Doctor
 *
 * @author Nazar Palijchuk
 * @version Doctor: 1.0
 * @since 09.07.2021|1:04
 */

@Document("doctors")
@Builder
@Data
public class Doctor
{
	private String id;
	private Person person;
	private String specialty;
	private LocalTime startHour;
	private LocalTime endHour;
	@DBRef
	private User user;
}