package com.example.privateclinic.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * Created by IntelliJ IDEA.
 * privateClinic.AssignedProcedure
 *
 * @author Nazar Palijchuk
 * @version AssignedProcedure: 1.0
 * @since 18.08.2021|16:55
 */

@Document("assignedProcedures")
@Data
@Builder
public class AssignedProcedure
{
	private String id;
	private Patient patient;
	private Procedure procedure;
	private LocalDateTime date;
}