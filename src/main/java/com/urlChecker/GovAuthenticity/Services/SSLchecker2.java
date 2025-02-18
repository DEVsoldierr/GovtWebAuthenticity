package com.urlChecker.GovAuthenticity.Services;



import com.urlChecker.GovAuthenticity.DTO.SSLDetailsDTO;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLSession;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.concurrent.CompletableFuture;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.HttpsURLConnection;

@Service
public class SSLchecker2 {

    private final HttpClient httpClient;

    public SSLchecker2() {
        this.httpClient = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NORMAL)  // Follows redirects
                .build();
    }

    public CompletableFuture<SSLDetailsDTO> getSslDetailsDTOAsync(String urlString) {
        SSLDetailsDTO sslDetailsDTO = new SSLDetailsDTO();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlString))
                .GET()
                .build();

        // Send request asynchronously
        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.discarding())
                .thenApply(response -> {
                    try {
                        SSLSession sslSession = response.sslSession()
                                .orElseThrow(() -> new RuntimeException("No SSL session"));

                        Certificate[] certs = sslSession.getPeerCertificates();
                        if (certs.length > 0 && certs[0] instanceof X509Certificate) {
                            X509Certificate cert = (X509Certificate) certs[0];
                            sslDetailsDTO.setIssuer(cert.getIssuerDN().toString());
                            sslDetailsDTO.setValidFrom(cert.getNotBefore().toString());
                            sslDetailsDTO.setValidTo(cert.getNotAfter().toString());
                            sslDetailsDTO.setTrusted(true);
                        }
                    } catch (SSLPeerUnverifiedException e) {
                        sslDetailsDTO.setTrusted(false);
                    } catch (Exception e) {
                        sslDetailsDTO.setTrusted(false);
                    }
                    return sslDetailsDTO;
                });
    }
}
