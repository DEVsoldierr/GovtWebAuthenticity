package com.urlChecker.GovAuthenticity.Controller;

import com.urlChecker.GovAuthenticity.DTO.SSLDetailsDTO;

import com.urlChecker.GovAuthenticity.Services.SSLchecker2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/auth")
public class WebsiteAuthController {

    private final SSLchecker2 ssLchecker2;

    // Constructor Dependency Injection
    public WebsiteAuthController(SSLchecker2 ssLchecker) {
        this.ssLchecker2 = ssLchecker;
    }

    @GetMapping(value = "/ssl-details", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<ResponseEntity<SSLDetailsDTO>> getSSL(@RequestParam String url) {
        return ssLchecker2.getSslDetailsDTOAsync(url)
                .thenApply(sslDetailsDTO -> {
                    if (sslDetailsDTO == null) {
                        return ResponseEntity.notFound().build();
                    }
                    return ResponseEntity.ok().body(sslDetailsDTO);
                });
    }
}