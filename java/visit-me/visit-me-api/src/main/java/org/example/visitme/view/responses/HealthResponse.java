package org.example.visitme.view.responses;

import lombok.ToString;

@ToString
public class HealthResponse {
        
    private final String status;

        public  HealthResponse(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }
}
