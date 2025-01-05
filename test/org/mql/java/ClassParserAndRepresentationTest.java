package org.mql.java;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.*;
import org.mql.java.controller.ClassParserAndRepresentation;

class ClassParserAndRepresentationTest {
	   private ClassParserAndRepresentation parser;

	    @BeforeEach
	    void setUp() {
	        
	        parser = new ClassParserAndRepresentation("java.lang.String");
	    }
	    @Test
	    void testAttributesExtraction() {
	        // Recuperer les attributs analyses
	        List<String> attributes = parser.getAttributes();

	     
	        assertNotNull(attributes, "La liste des attributs ne doit pas être nulle");

	       
	        assertFalse(attributes.isEmpty(), "La classe String devrait avoir des attributs");

	        // Verifier qu'un attribut specifique est present
	        assertTrue(attributes
	        		.stream()
	        		.anyMatch(attr -> attr.contains("value")),
	                "L'attribut 'value' devrait être detecte dans la classe String");
	    }
	    @Test
	    void testMethodsExtraction() {
	        // Recuperer les methodes analysees
	        List<String> methods = parser.getMethodes();

	        
	        assertNotNull(methods, "La liste des methodes ne doit pas être nulle");

	        
	        assertFalse(methods.isEmpty(), "La classe String devrait avoir des methodes");

	        // Verifier qu'une methode specifique est presente
	        assertTrue(methods.stream().anyMatch(method -> method.contains("substring")),
	                "La methode 'substring' devrait être detectee dans la classe String");
	    }
//
//	 // Tester un nom de classe invalide
//	    @Test
//	    void testInvalidClassName() {
//	        
//	        Exception exception = assertThrows(ClassNotFoundException.class, () -> {
//	            new ClassParserAndRepresentation("org.mql.java.ui.shapes.Carre");
//	        });
//
//	        // Verifier que l'exception est bien levee
//	        assertNotNull(exception, "Une exception ClassNotFoundException devrait être levee pour une classe inexistante");
//	    }
}
