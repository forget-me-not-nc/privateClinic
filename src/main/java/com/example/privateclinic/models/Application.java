package com.example.privateclinic.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * Created by IntelliJ IDEA.
 * privateClinic.Application
 *
 * @author Nazar Palijchuk
 * @version Application: 1.0
 * @since 09.07.2021|1:18
 */

@Document("applications")
@Builder
@Data
public class Application
{
	private String id;
	private Patient patient;
	private Doctor doctor;
	private LocalDateTime date;
}