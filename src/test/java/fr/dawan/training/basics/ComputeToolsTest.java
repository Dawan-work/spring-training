package fr.dawan.training.basics;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.dawan.training.tools.ComputeTools;
import org.springframework.test.context.ActiveProfiles;

//Une classe de Test => TestCase
@ActiveProfiles("test")
class ComputeToolsTest {

	private int x,y;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		x = 2;
		y=4;
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	
	//plusieurs méthodes annotées @Test ou nommées testXXX()
	@Test
	void testSum() {
		//A l'intérieur d'une méthode de test, on peut avoir plusieurs "assertions"
		assertEquals(6, ComputeTools.sum(x, y));
	}
	
	@Test
	void testMultiply() {
		assertEquals(8, ComputeTools.multiply(x, y));
	}
	
	// beforeAll >>>>> BeforeEach >> testSum >> AfterEach
	                 // >> BeforeEach >> testMultiply >> AfterEach
	//afterAll

}
