package ru.aizen.health.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {


	@RequestMapping(value = "/health",
			method = RequestMethod.GET,
			produces = {MediaType.APPLICATION_JSON_VALUE}
	)
	public HealthResponse health() {
		return new HealthResponse("OK");
	}

}
