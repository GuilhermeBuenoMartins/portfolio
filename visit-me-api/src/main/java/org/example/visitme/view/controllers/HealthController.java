package org.example.visitme.view.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.example.visitme.view.responses.HealthResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/v1")
public class HealthController {

    @GetMapping("/health")
    public ResponseEntity<HealthResponse> getHealth() {
        HealthResponse responseBody = new HealthResponse("UP");
        return new ResponseEntity<HealthResponse>(responseBody, HttpStatusCode.valueOf(200));
    }
}
