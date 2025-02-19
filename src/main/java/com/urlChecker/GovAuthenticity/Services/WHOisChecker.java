        package com.urlChecker.GovAuthenticity.Services;



        import org.springframework.stereotype.Service;
        import java.io.BufferedReader;
        import java.io.InputStreamReader;
        import java.net.Socket;
        import java.net.UnknownHostException;
        import java.util.Arrays;
        import java.util.List;
        import java.util.LinkedHashMap;
        import java.util.Map;

        @Service
        public class WHOisChecker {
            private static final List<String> WHOIS_SERVER = Arrays.asList(
                    "whois.iana.org",
                    "whois.verisign-grs.com",
                            "whois.nic.in"
                            ) ;// Default WHOIS lookup server

            public String getWhoisData(String domain) {
                StringBuilder result = new StringBuilder();
                for (String server : WHOIS_SERVER) {
                    try (Socket socket = new Socket(server, 43)) {
                        socket.setSoTimeout(5000); // 5-second timeout
                        socket.getOutputStream().write((domain + "\n").getBytes());
                        socket.getOutputStream().flush();

                        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            result.append(line).append("\n");
                        }
                        if (result.length() > 0) {
                            return result.toString().trim();

                        }
                    }
                    catch(Exception e){
                        System.out.println("Failed to get WHOIS data from " + server + ": " + e.getMessage());
                    }
                }
                return "none of the server have Whois information for"+domain;
            }
            }



