package com.urlChecker.GovAuthenticity.Controller;




import com.urlChecker.GovAuthenticity.DTO.SSLDetailsDTO;
import com.urlChecker.GovAuthenticity.Services.DomainNameExtractor;
import com.urlChecker.GovAuthenticity.Services.SSLchecker2;
import com.urlChecker.GovAuthenticity.Services.WHOisChecker;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/combined-check")
public class CombinedController {

    private final SSLchecker2 sslChecker;
    private final WHOisChecker whoisChecker;
    private final DomainNameExtractor domainNameExtractor;

    // Constructor Injection
    public CombinedController(SSLchecker2 sslChecker, WHOisChecker whoisChecker,DomainNameExtractor domainNameExtractor) {
        this.sslChecker = sslChecker;
        this.whoisChecker = whoisChecker;
        this.domainNameExtractor=domainNameExtractor;
    }

    @GetMapping("/check")
    public CompletableFuture<ResponseEntity<Map<String, Object>>> checkWebsite(@RequestParam String url) throws URISyntaxException {
        Map<String, Object> response = new HashMap<>();

        // Fetch SSL Details Asynchronously
        CompletableFuture<SSLDetailsDTO> sslFuture = sslChecker.getSslDetailsDTOAsync(url);

        // Fetch WHOIS Data Synchronously
        String domain=domainNameExtractor.getDomainName(url);
        String whoisData = whoisChecker.getWhoisData(domain);

        // Combine results and wait for SSL completion
        return sslFuture.thenApply(sslDetails -> {
            response.put("SSL_Details",  sslDetails);
            response.put("WHOIS_Data", whoisData);
            return ResponseEntity.ok(response);
        });
    }
}
