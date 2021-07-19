package com.example.privateclinic.controllers.rest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by IntelliJ IDEA.
 * privateClinic.CustomErrorController
 *
 * @author Nazar Palijchuk
 * @version CustomErrorController: 1.0
 * @since 16.07.2021|21:37
 */

@Controller
public class CustomErrorController implements ErrorController
{
	@RequestMapping("/error")
	public String handleError(HttpServletRequest request)
	{
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

		if (status != null)
		{
			int statusCode = Integer.parseInt(status.toString());

			if (statusCode == HttpStatus.FORBIDDEN.value())
			{
				return "errorPages/403";
			} else if (statusCode == HttpStatus.NOT_FOUND.value())
			{
				return "errorPages/404";
			} else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value())
			{
				return "errorPages/500";
			}
		}
		return "errorPages/404";
	}

}