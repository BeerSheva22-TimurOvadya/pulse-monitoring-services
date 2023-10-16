package telran.monitoring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import telran.monitoring.dto.EmailNotificationData;

import telran.monitoring.service.*;

@RestController
@RequestMapping("data")

public class EmailDataProviderController {
	@Autowired
	EmailProviderService dataProvider;

	@GetMapping("/{patientId}")
	EmailNotificationData getNotificationData(@PathVariable long patientId) throws Exception {
		return  dataProvider.getEmailNotificationData(patientId);
	}

	
}