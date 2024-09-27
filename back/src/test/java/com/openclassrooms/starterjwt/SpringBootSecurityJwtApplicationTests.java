package com.openclassrooms.starterjwt;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SpringBootSecurityJwtApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
    void mainMethodTest() {
        // Vérifie que la méthode main s'exécute sans lancer d'exception
        assertDoesNotThrow(() -> SpringBootSecurityJwtApplication.main(new String[]{}));
    }

}
