package be.trollcorporation.wikidoci.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class StringUtilsTest {
	
	private int tolerance;

	@Before
	public void init() {
		tolerance = 0;
	}
		
	@Test
	public void testEqualsWithNoTolerance() {
		//No tolerance
		tolerance = 0;
		assertTrue(testEqualsWithTolerance("test", "test"));
		
		assertFalse(testEqualsWithTolerance("test", "tést"));
		assertFalse(testEqualsWithTolerance("test", "test2"));
	}
	
	@Test
	public void testEqualsWithFullTolerance() {
		tolerance = 100;
		assertTrue(testEqualsWithTolerance("test", "test"));
		assertTrue(testEqualsWithTolerance("test", "test2"));
		assertTrue(testEqualsWithTolerance("test", "ultrasupermagictest"));
	}
	
	@Test
	public void testEqualsWithHalfTolerance() {
		tolerance = 50;
		assertTrue(testEqualsWithTolerance("test", "test"));
		assertTrue(testEqualsWithTolerance("test", "test2"));
		assertTrue(testEqualsWithTolerance("test", "tee"));
		assertTrue(testEqualsWithTolerance("testtest", "testtoast2"));
		
		assertFalse(testEqualsWithTolerance("test", "toast"));
		assertFalse(testEqualsWithTolerance("test", ""));
		assertFalse(testEqualsWithTolerance("test", "t"));
		assertFalse(testEqualsWithTolerance("test", "té"));
		assertFalse(testEqualsWithTolerance("test", "ultrasupermagictest"));
	}
	
	@Test
	public void testEqualsWith70Tolerance() {
		tolerance = 70;
		assertTrue(testEqualsWithTolerance("test", "test"));
		assertTrue(testEqualsWithTolerance("test", "tast"));
		assertTrue(testEqualsWithTolerance("test", "tést"));
		assertTrue(testEqualsWithTolerance("test", "tèst"));
		assertTrue(testEqualsWithTolerance("test", "test2"));
		
		assertFalse(testEqualsWithTolerance("test", "toast2"));
		assertFalse(testEqualsWithTolerance("moyenmottest", ""));
		assertFalse(testEqualsWithTolerance("test", ""));
	}
	
	@Test
	public void testEqualsWith95Tolerance() {
		tolerance = 95;
		assertTrue(testEqualsWithTolerance("test", "test"));
		assertTrue(testEqualsWithTolerance("test", "test2"));
		assertTrue(testEqualsWithTolerance("test", "toast"));
		
		assertFalse(testEqualsWithTolerance("test", "ultrasupermagictest"));
	}
	
	@Test
	public void testEqualsWith10Tolerance() {
		tolerance = 10;
		assertTrue(testEqualsWithTolerance("test", "test"));
		assertTrue(testEqualsWithTolerance("supermaxitoys", "supérmaxitoys"));
		
		assertFalse(testEqualsWithTolerance("test", "tast"));
		assertFalse(testEqualsWithTolerance("test", "tèst"));
		assertFalse(testEqualsWithTolerance("test", "tést"));
		assertFalse(testEqualsWithTolerance("test", "test2"));
		assertFalse(testEqualsWithTolerance("test", "toast2"));
		assertFalse(testEqualsWithTolerance("test", "superlongmot"));
	}
	
	@Test
	public void testEqualsWith20Tolerance() {
		tolerance = 20;
		assertTrue(testEqualsWithTolerance("test", "test"));
		assertTrue(testEqualsWithTolerance("test", "tast"));
		assertTrue(testEqualsWithTolerance("test", "tést"));
		assertTrue(testEqualsWithTolerance("test", "tèst"));
		assertTrue(testEqualsWithTolerance("supermaxitoys", "supérmaxitoys"));
		assertTrue(testEqualsWithTolerance("supermaxitoys", "supermaxitoys2"));
		assertTrue(testEqualsWithTolerance("supermaxitoys", "supermasitoys2"));
		
		assertFalse(testEqualsWithTolerance("test", "tast2"));
		assertFalse(testEqualsWithTolerance("test", "toast2"));
		assertFalse(testEqualsWithTolerance("test", "superlongmot"));
	}
	
	@Test
	public void testEqualsWithCrossRegex() {
		assertTrue(StringUtils.equalsWithCrossRegex("b*nb*n", "bonbon"));
	}
	
	private boolean testEqualsWithTolerance(final String mot1, final String mot2) {
		return StringUtils.equalsWithTolerance(mot1, mot2, tolerance);
	}
	
}
