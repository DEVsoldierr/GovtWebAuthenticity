package com.urlChecker.GovAuthenticity.Services;


import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class DomainNameExtractor{


    public  String getDomainName(String url) throws URISyntaxException {

        try {
            URI uri = new URI(url);
            System.out.println(uri.getPort());
            return uri.getHost();
        } catch (Exception e) {
            System.err.println("Invalid URL: " + e.getMessage());
        }
        return "no WHOIS info";
        // Extracts the domain
    }
}
