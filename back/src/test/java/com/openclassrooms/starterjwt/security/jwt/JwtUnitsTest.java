package com.openclassrooms.starterjwt.security.jwt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUnitsTest {
    @Test
    public void testGetUserNameFromJwtToken() {

		JwtUtils jwtUtils = new JwtUtils();
        String jwtSecret = "testSecret";
        ReflectionTestUtils.setField(jwtUtils, "jwtSecret", jwtSecret);

        String username = "testUser";

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + 86400000)) // 1 jour d'expiration
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

        String returnedUsername = jwtUtils.getUserNameFromJwtToken(token);

        assertEquals(username, returnedUsername);
    }
	
	@Test
    public void testValidateJwtToken() {
 
        JwtUtils jwtUtils = new JwtUtils();
        String jwtSecret = "testSecret";

        ReflectionTestUtils.setField(jwtUtils, "jwtSecret", jwtSecret);

        String username = "testUser";

        String validToken = Jwts.builder()
                .setSubject(username) 
                .setIssuedAt(new Date()) 
                .setExpiration(new Date((new Date()).getTime() + 86400000)) 
                .signWith(SignatureAlgorithm.HS512, jwtSecret) 
                .compact(); 

        String invalidToken = "invalidToken";


        boolean isValid = jwtUtils.validateJwtToken(validToken);
        boolean isInvalid = jwtUtils.validateJwtToken(invalidToken);

        assertTrue(isValid);
        assertFalse(isInvalid);
    }
	
	
	@Test
    public void testValidateJwtTokenWithInvalidSignature() {
      
        JwtUtils jwtUtils = new JwtUtils();
        String jwtSecret = "testSecret";
        ReflectionTestUtils.setField(jwtUtils, "jwtSecret", jwtSecret);

        String invalidSignatureToken = Jwts.builder()
                .setSubject("testUser")
                .signWith(SignatureAlgorithm.HS512, "wrongSecret")
                .compact();
    
        assertFalse(jwtUtils.validateJwtToken(invalidSignatureToken));
    }
	
	 @Test
	 public void testValidateJwtTokenWithExpiredToken() {

	     JwtUtils jwtUtils = new JwtUtils();
	     String jwtSecret = "testSecret";
	     ReflectionTestUtils.setField(jwtUtils, "jwtSecret", jwtSecret);
	     String expiredToken = Jwts.builder()
	             .setSubject("testUser")
	             .setExpiration(new Date(System.currentTimeMillis() - 1000))
	             .signWith(SignatureAlgorithm.HS512, jwtSecret)
	             .compact();
	    
	        assertFalse(jwtUtils.validateJwtToken(expiredToken));
	}
	 
	@Test
	public void testValidateJwtTokenWithEmptyClaims() {

	     JwtUtils jwtUtils = new JwtUtils();
	     String jwtSecret = "testSecret";
	     ReflectionTestUtils.setField(jwtUtils, "jwtSecret", jwtSecret);

	     String emptyClaimsToken = "";
	    
	     assertFalse(jwtUtils.validateJwtToken(emptyClaimsToken));
	}
	
}
