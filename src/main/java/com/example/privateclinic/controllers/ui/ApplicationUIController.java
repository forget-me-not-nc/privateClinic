package com.example.privateclinic.controllers.ui;

import com.example.privateclinic.security.CustomUserDetails;
import com.example.privateclinic.service.applications.impls.ApplicationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by IntelliJ IDEA.
 * privateClinic.ApplicationUIController
 *
 * @author Nazar Palijchuk
 * @version ApplicationUIController: 1.0
 * @since 21.07.2021|22:30
 */

@Controller
@RequestMapping("ui/application")
public class ApplicationUIController
{
	@Autowired
	ApplicationServiceImpl applicationService;

	@RequestMapping("/showAll")
	public String showApplications(
			Model model,
			@AuthenticationPrincipal CustomUserDetails userDetails
	)
	{
		model.addAttribute("applications", applicationService.getAll());

		return "applicationsPages/showAll";
	}
}