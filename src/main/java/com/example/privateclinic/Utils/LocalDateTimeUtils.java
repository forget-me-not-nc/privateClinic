package com.example.privateclinic.Utils;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * privateClinic.LocalDateTimeUtils
 *
 * @author Nazar Palijchuk
 * @version LocalDateTimeUtils: 1.0
 * @since 18.08.2021|19:42
 */

@Service
public class LocalDateTimeUtils
{
	public LocalDateTime convertToLocalDateTime(Date date, LocalTime time)
	{
		return date.toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDateTime()
				.withHour(time.getHour())
				.withMinute(time.getMinute());
	}
}