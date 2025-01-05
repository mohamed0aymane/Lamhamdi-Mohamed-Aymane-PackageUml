package org.mql.java;




import org.junit.jupiter.api.*;
import org.mql.java.controller.FileExplorer;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class FileExplorerTest {

	private FileExplorer fileExplorer;
	
	@BeforeEach
	void setUp() {
		//Initialiser FileExplorer avec le chemin du package source
		String pathPackage="src/org/mql/java";
		fileExplorer=new FileExplorer(pathPackage);
	}
	
	@Test
	void testLoadPackages() {
		//on charge les packages
		fileExplorer.loadPackages();
		
		
		//verifier que les listes ne sont pas nulles
		assertNotNull(fileExplorer.getClasses(),"la liste de classe ne doit pas etre nulle");
		assertNotNull(fileExplorer.getInterfaces(),"la liste d'interface ne doit pas etre nulle");
		assertNotNull(fileExplorer.getEnumerations(),"la liste d'enumerations ne doit pas etre nulle");
		assertNotNull(fileExplorer.getAnnotations(),"la liste d'annotations ne doit pas etre nulle");
		
		//verifier qu'au moins une classe a ete trouve du package
		List<Class<?>>  classes= fileExplorer.getClasses();
		assertFalse(classes.isEmpty(),"au moins une classe doit etre trouvee");
		//verifier qu'une classe specifique existe
		assertTrue(classes
				.stream()
				.anyMatch(cls->cls.getName()
				.endsWith("FileExplorer")),
				"La classe FileExplorer devrait  etre detectee");

	}
	
	@Test
	void testCategorizeClass() {
		
		fileExplorer.loadPackages();
		
		//verifier qu'il n'y a pas de duplication entre les categories
			List<Class<?>> allClasses=fileExplorer.getClasses();
			allClasses.addAll(fileExplorer.getInterfaces());
			allClasses.addAll(fileExplorer.getAnnotations());
			allClasses.addAll(fileExplorer.getEnumerations());
			
			assertEquals(allClasses.size(),
					allClasses.stream().distinct().count(),
					"Aucune classe ne devrait etre categorisee deux fois");
		
	}

	@Test
	void testPrintResults() {
		fileExplorer.loadPackages();
		
		//appeler la methode printResults pour verifier qu'elle ne genere pas d'erreurs
		assertDoesNotThrow(()->fileExplorer.printResults()
				,"printResults ne devrait pas lever d'exception");
	}
}
