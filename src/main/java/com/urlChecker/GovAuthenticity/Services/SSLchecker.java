/*
package com.urlChecker.GovAuthenticity.Services;
import com.urlChecker.GovAuthenticity.DTO.SSLDetailsDTO;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
@Service
public class SSLchecker {

    public SSLDetailsDTO getSslDetailsDTO(String urlString) {
        SSLDetailsDTO sslDetailsDTO = new SSLDetailsDTO();
        try{
            URL url=new URL(urlString);
            HttpsURLConnection conn=(HttpsURLConnection) url.openConnection();
            conn.connect();

            Certificate[] certs=conn.getServerCertificates();
            if(certs.length>0 && certs[0] instanceof X509Certificate)
            {
                X509Certificate cert=(X509Certificate) certs[0];
                sslDetailsDTO.setIssuer(cert.getIssuerDN().toString());
                sslDetailsDTO.setValidFrom(cert.getNotBefore().toString());
                sslDetailsDTO.setValidTo(cert.getNotAfter().toString());
                sslDetailsDTO.setTrusted(true);
            }



        }
        catch (Exception e)
        {
            sslDetailsDTO.setTrusted(false);
        }
        return sslDetailsDTO;

    }
}
*/
