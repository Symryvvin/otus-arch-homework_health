package ru.aizen.health.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

	@Value("${app.version}")
	private String version;

	@RequestMapping(value = "/health",
			method = RequestMethod.GET,
			produces = {MediaType.APPLICATION_JSON_VALUE}
	)
	public HealthResponse health() {
		return new HealthResponse("OK");
	}

	@RequestMapping(value = "/version",
			method = RequestMethod.GET,
			produces = {MediaType.TEXT_PLAIN_VALUE}
	)
	public String version() {
		return version;
	}

	@GetMapping("/")
	public ResponseEntity<String> live() {
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
