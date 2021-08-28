package com.example.privateclinic.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by IntelliJ IDEA.
 * privateClinic.Procedure
 *
 * @author Nazar Palijchuk
 * @version Procedure: 1.0
 * @since 18.08.2021|16:38
 */

@Document("procedures")
@Builder
@Data
public class Procedure
{
	private String id;
	private String procedureName;
	private String description;
	private double price;
}