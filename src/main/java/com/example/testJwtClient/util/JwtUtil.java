package com.example.testJwtClient.util;

import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.util.Base64;
import java.security.KeyFactory;

@Component
public class JwtUtil {
	
	 private PublicKey publicKey;

	    public JwtUtil() {
	        try {
	            // Fetch public key from the auth server
	            String publicKeyString = fetchPublicKeyFromAuthServer();
	            this.publicKey = decodePublicKey(publicKeyString);
	        } catch (Exception e) {
	            throw new RuntimeException("Error initializing JWT utility: " + e.getMessage());
	        }
	    }

	    private String fetchPublicKeyFromAuthServer() {
	        // Replace with your actual auth server URL
	        String authServerUrl = "http://localhost:8080/api/v1/auth/public-key";
	        try {
	            // Simple HTTP GET request (can use RestTemplate, WebClient, etc.)
	            return new org.springframework.web.client.RestTemplate().getForObject(authServerUrl, String.class);
	        } catch (Exception e) {
	            throw new RuntimeException("Error fetching public key from auth server: " + e.getMessage());
	        }
	    }

	    private PublicKey decodePublicKey(String publicKeyString) throws Exception {
	        String publicKeyPEM = publicKeyString
	                .replace("-----BEGIN PUBLIC KEY-----", "")
	                .replace("-----END PUBLIC KEY-----", "")
	                .replaceAll("\\s", "");
	        byte[] decoded = Base64.getDecoder().decode(publicKeyPEM);
	        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decoded);
	        return KeyFactory.getInstance("RSA").generatePublic(keySpec);
	    }

	    public Claims validateToken(String token) {
	        return Jwts.parserBuilder()
	                .setSigningKey(publicKey)
	                .build()
	                .parseClaimsJws(token)
	                .getBody();
	    }

}
