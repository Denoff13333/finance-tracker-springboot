
package com.example.finance;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class HelloController {

    @GetMapping("/health")
    public String health() {
        return "Finance Tracker Running";
    }
}
