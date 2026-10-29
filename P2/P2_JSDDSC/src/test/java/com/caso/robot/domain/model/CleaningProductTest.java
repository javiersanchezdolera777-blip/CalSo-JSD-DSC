package com.caso.robot.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.calso.robot.domain.model.CleaningProduct;

class CleaningProductTest {

	@Test
	void testGivenWaterWhenToStringThenReturnsAgua() {
		assertEquals("agua", CleaningProduct.WATER.toString());
	}
	
	@Test
	void testGivenSoapWhenToStringThenReturnsJabon() {
		assertEquals("jabón", CleaningProduct.SOAP.toString());
	}

	@Test
	void testGivenWaxWhenToStringThenReturnsCera() {
		assertEquals("cera", CleaningProduct.WAX.toString());
	}
	
	@Test
	void testGivenGlassCleanerWhenToStringThenReturnsLimpiacristales() {
		assertEquals("Limpiacristales", CleaningProduct.GLASS_CLEANER.toString());
	}
	
}
