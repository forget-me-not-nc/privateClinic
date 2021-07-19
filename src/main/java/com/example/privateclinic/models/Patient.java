package com.example.privateclinic.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by IntelliJ IDEA.
 * privateClinic.Patient
 *
 * @author Nazar Palijchuk
 * @version Patient: 1.0
 * @since 09.07.2021|0:59
 */

@Document("patients")
@Builder
@Data
public class Patient
{
	private String id;
	private Person person;
	@DBRef
	private User user;
}