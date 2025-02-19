package com.urlChecker.GovAuthenticity.Controller;




import com.urlChecker.GovAuthenticity.Services.WHOisChecker;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/whois")
public class WhoisController {
    private final WHOisChecker whOisChecker;

    public WhoisController(WHOisChecker whOisChecker) {
        this.whOisChecker = whOisChecker;
    }

    @GetMapping("/{domain}")
    public ResponseEntity<String> getWhois(@PathVariable String domain) {
        String whoisData = whOisChecker.getWhoisData(domain);
        return ResponseEntity.ok(whoisData);
    }
}
