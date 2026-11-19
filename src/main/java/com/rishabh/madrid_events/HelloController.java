package com.rishabh.madrid_events;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello from Madrid Events!";
    }

    @GetMapping("/api/health")
    public HealthResponse health(){
        return new HealthResponse("UP", "madrid-events");
    }

    public record  HealthResponse(String status , String app) {
    }

}
