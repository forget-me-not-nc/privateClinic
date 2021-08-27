package com.example.privateclinic.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalTime;
import java.util.Objects;

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

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Doctor doctor = (Doctor) o;
		return id.equals(doctor.id);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(id);
	}
}