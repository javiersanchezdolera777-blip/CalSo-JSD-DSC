package es.um.calso.dojoreservation.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import es.um.calso.dojoreservation.domain.model.Discount;
import es.um.calso.dojoreservation.domain.model.Fighter;
import es.um.calso.dojoreservation.domain.model.UnemploymentPeriod;

class CalculateBestDiscountTest {
	private final ReservationDiscountService service = new ReservationDiscountService();
	private static final LocalDate TRAINING_DATE = LocalDate.of(2026, 10, 20);

	@Test
	void testGivenNullFightersWhenCalculateBestDiscountThenThrowsFightersAreRequired() {
		NullPointerException exception = assertThrows(NullPointerException.class,
				() -> service.calculateBestDiscount(null, TRAINING_DATE));
		assertEquals("Fighters are required", exception.getMessage());
	}

	@Test
	void testGivenNullTrainingDateWhenCalculateBestDiscountThenThrowsTrainingDateIsRequired() {
		NullPointerException exception = assertThrows(NullPointerException.class,
				() -> service.calculateBestDiscount(List.of(), null));
		assertEquals("Training date is required", exception.getMessage());
	}

	@Test
	void testGivenEmptyFightersWhenCalculateBestDiscountThenReturnsZeroDiscount() {
		Discount result = service.calculateBestDiscount(List.of(), TRAINING_DATE);
		assertEquals(new Discount(0), result);
	}

	@Test
	void testGivenAdultFighterWithoutUnemploymentWhenCalculateBestDiscountThenReturnsZeroDiscount() {
		Fighter fighter = new Fighter("Fighter 1", LocalDate.of(2000, 5, 10), null);
		Discount result = service.calculateBestDiscount(List.of(fighter), TRAINING_DATE);
		assertEquals(new Discount(0), result);
	}

	@Test
	void testGivenYoungFighterWithoutUnemploymentWhenCalculateBestDiscountThenReturnsFifteenPercent() {
		Fighter fighter = new Fighter("Fighter 1", LocalDate.of(2009, 5, 10), null);
		Discount result = service.calculateBestDiscount(List.of(fighter), TRAINING_DATE);
		assertEquals(new Discount(15), result);
	}

	@Test
	void testGivenThreeCompleteYearsOfUnemploymentWhenCalculateBestDiscountThenReturnsThirtyPercent() {
		Fighter fighter = new Fighter("Fighter 1", LocalDate.of(1990, 5, 10),
				new UnemploymentPeriod(LocalDate.of(2023, 10, 20)));
		Discount result = service.calculateBestDiscount(List.of(fighter), TRAINING_DATE);
		assertEquals(new Discount(30), result);
	}

	@Test
	void testGivenTwoCompleteYearsOfUnemploymentWhenCalculateBestDiscountThenReturnsTwentyPercent() {
		Fighter fighter = new Fighter("Fighter 1", LocalDate.of(1990, 5, 10),
				new UnemploymentPeriod(LocalDate.of(2024, 10, 20)));
		Discount result = service.calculateBestDiscount(List.of(fighter), TRAINING_DATE);
		assertEquals(new Discount(20), result);
	}

	@Test
	void testGivenLessThanOneCompleteYearOfUnemploymentWhenCalculateBestDiscountThenReturnsZeroDiscount() {
		Fighter fighter = new Fighter("Fighter 1", LocalDate.of(1990, 5, 10),
				new UnemploymentPeriod(LocalDate.of(2025, 10, 21)));
		Discount result = service.calculateBestDiscount(List.of(fighter), TRAINING_DATE);
		assertEquals(new Discount(0), result);
	}

	@Test
	void testGivenYoungFighterWithOneCompleteYearOfUnemploymentWhenCalculateBestDiscountThenKeepsYouthDiscount() {
		Fighter fighter = new Fighter("Fighter 1", LocalDate.of(2009, 5, 10),
				new UnemploymentPeriod(LocalDate.of(2025, 5, 10)));
		Discount result = service.calculateBestDiscount(List.of(fighter), TRAINING_DATE);
		assertEquals(new Discount(15), result);
	}

	@Test
	void testGivenFightersWithThirtyAndTenPercentUnemploymentDiscountsWhenCalculateBestDiscountThenReturnsThirtyPercent() {
		Fighter first = new Fighter("Fighter 1", LocalDate.of(1990, 5, 10),
				new UnemploymentPeriod(LocalDate.of(2023, 10, 20)));
		Fighter second = new Fighter("Fighter 2", LocalDate.of(1995, 5, 10),
				new UnemploymentPeriod(LocalDate.of(2025, 10, 20)));
		Discount result = service.calculateBestDiscount(List.of(first, second), TRAINING_DATE);
		assertEquals(new Discount(30), result);
	}

	@Test
	void testGivenListContainingNullFighterWhenCalculateBestDiscountThenThrowsNullFighter() {
		List<Fighter> fighters = Collections.singletonList(null);
		NullPointerException exception = assertThrows(NullPointerException.class,
				() -> service.calculateBestDiscount(fighters, TRAINING_DATE));
		assertEquals("Null fighter", exception.getMessage());
	}

	@Test
	void testGivenOneCompleteYearOfUnemploymentWhenCalculateBestDiscountThenReturnsTenPercent() {
		Fighter fighter = new Fighter("Fighter 1", LocalDate.of(1990, 5, 10),
				new UnemploymentPeriod(LocalDate.of(2025, 10, 20)));
		Discount result = service.calculateBestDiscount(List.of(fighter), TRAINING_DATE);

		assertEquals(new Discount(10), result);
	}
}
