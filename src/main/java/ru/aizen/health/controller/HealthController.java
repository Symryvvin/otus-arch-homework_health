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
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<HealthResponse> health() {
		return new ResponseEntity<>(new HealthResponse("OK"), HttpStatus.OK);
	}

	@RequestMapping(value = "/version",
			method = RequestMethod.GET,
			produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> version() {
		return new ResponseEntity<>(version, HttpStatus.OK);
	}

	@GetMapping("/ready")
	public ResponseEntity<String> ready() {
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
