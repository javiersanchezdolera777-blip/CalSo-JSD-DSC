package es.um.calso.dojoreservation.infrastructure.e2e;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

class ReservationCostE2ETest extends BrowserE2ESupport {

	@Test
	void testGivenMissingTeamIdWhenCalculateCostThenShowsTeamIdRequired() {
		open("/coste?teamId=&dojoId=D-001&trainingDate=2026-10-21");
		assertPageContains("Team id is required");
	}

	@Test
	void testGivenMissingDojoIdWhenCalculateCostThenShowsDojoIdRequired() {
		open("/coste?teamId=EQ-002&dojoId=&trainingDate=2026-10-21");
		assertPageContains("Dojo id is required");
	}

	@Test
	void testGivenUnknownTeamIdWhenCalculateCostThenShowsTeamDoesNotExist() {
		open("/coste?teamId=EQ-999&dojoId=D-001&trainingDate=2026-10-21");
		assertEquals("Team does not exist", textOf("error").trim());
	}

	@Test
	void testGivenUnknownDojoIdWhenCalculateCostThenShowsDojoDoesNotExist() {
		open("/coste?teamId=EQ-002&dojoId=D-999&trainingDate=2026-10-21");
		assertEquals("Dojo does not exist", textOf("error").trim());
	}

	@Test
	void testGivenPastTrainingDateWhenCalculateCostThenShowsFutureDateRequired() {
		calculateCost("EQ-002", "D-003", "2026-10-19");
		assertEquals("Training date must be in the future", textOf("error").trim());
	}

	@Test
	void testGivenTodayAsTrainingDateWhenCalculateCostThenShowsFutureDateRequired() {
		calculateCost("EQ-002", "D-003", "2026-10-20");
		assertEquals("Training date must be in the future", textOf("error").trim());
	}

	@Test
	void testGivenTomorrowTrainingDateWithRegularTeamAndDojoSurWhenCalculateCostThenCalculatesPrices() {
		calculateCost("EQ-002", "D-003", "2026-10-21");
		assertCost("35.00", "5.00", "40.00", "0", "40.00");
	}

	@Test
	void testGivenReservedDojoDateWhenCalculateCostThenStillCalculatesCost() {
		calculateCost("EQ-001", "D-001", "2026-11-20");
		assertCost("40.00", "10.00", "50.00", "20", "40.00");
	}

	@Test
	void testGivenTwoRequiredWeaponsAndCompetingDiscountsWhenCalculateCostThenAppliesOnlyHighestDiscount() {
		calculateCost("EQ-001", "D-002", "2026-10-21");
		assertCost("50.00", "10.00", "60.00", "15", "51.00");
	}

	@Test
	void testGivenYoungestFighterIsSeventeenWhenCalculateCostThenAppliesYouthDiscount() {
		calculateCost("EQ-003", "D-003", "2027-11-19");
		assertCost("35.00", "5.00", "40.00", "15", "34.00");
	}

	@Test
	void testGivenYoungestFighterTurnsEighteenWhenCalculateCostThenDoesNotApplyYouthDiscount() {
		calculateCost("EQ-003", "D-003", "2027-11-20");
		assertCost("35.00", "5.00", "40.00", "0", "40.00");
	}

	@Test
	void testGivenYoungestFighterIsOlderThanEighteenWhenCalculateCostThenDoesNotApplyYouthDiscount() {
		calculateCost("EQ-003", "D-003", "2027-11-21");
		assertCost("35.00", "5.00", "40.00", "0", "40.00");
	}

	@Test
	void testGivenUnemploymentOneDayShortOfOneYearWhenCalculateCostThenAppliesNoUnemploymentDiscount() {
		calculateCost("EQ-004", "D-003", "2026-11-19");
		assertCost("35.00", "5.00", "40.00", "0", "40.00");
	}

	@Test
	void testGivenExactlyOneCompleteYearOfUnemploymentWhenCalculateCostThenAppliesTenPercentDiscount() {
		calculateCost("EQ-004", "D-003", "2026-11-20");
		assertCost("35.00", "5.00", "40.00", "10", "36.00");
	}

	@Test
	void testGivenOneDayMoreThanOneYearOfUnemploymentWhenCalculateCostThenAppliesTenPercentDiscount() {
		calculateCost("EQ-004", "D-003", "2026-11-21");
		assertCost("35.00", "5.00", "40.00", "10", "36.00");
	}

	@Test
	void testGivenUnemploymentOneDayShortOfTwoYearsWhenCalculateCostThenAppliesTenPercentDiscount() {
		calculateCost("EQ-004", "D-003", "2027-11-19");
		assertCost("35.00", "5.00", "40.00", "10", "36.00");
	}

	@Test
	void testGivenExactlyTwoCompleteYearsOfUnemploymentWhenCalculateCostThenAppliesTwentyPercentDiscount() {
		calculateCost("EQ-004", "D-003", "2027-11-20");
		assertCost("35.00", "5.00", "40.00", "20", "32.00");
	}

	@Test
	void testGivenOneDayMoreThanTwoYearsOfUnemploymentWhenCalculateCostThenAppliesTwentyPercentDiscount() {
		calculateCost("EQ-004", "D-003", "2027-11-21");
		assertCost("35.00", "5.00", "40.00", "20", "32.00");
	}

	@Test
	void testGivenUnemploymentOneDayShortOfThreeYearsWhenCalculateCostThenAppliesTwentyPercentDiscount() {
		calculateCost("EQ-005", "D-003", "2027-11-19");
		assertCost("35.00", "5.00", "40.00", "20", "32.00");
	}

	@Test
	void testGivenExactlyThreeCompleteYearsOfUnemploymentWhenCalculateCostThenAppliesThirtyPercentDiscount() {
		calculateCost("EQ-005", "D-003", "2027-11-20");
		assertCost("35.00", "5.00", "40.00", "30", "28.00");
	}

	@Test
	void testGivenOneDayMoreThanThreeYearsOfUnemploymentWhenCalculateCostThenAppliesThirtyPercentDiscount() {
		calculateCost("EQ-005", "D-003", "2027-11-21");
		assertCost("35.00", "5.00", "40.00", "30", "28.00");
	}

	private void assertPageContains(String expectedText) {
		String pageText = textAt(By.tagName("body"));
		assertTrue(pageText.contains(expectedText),
				"Expected page to contain: '" + expectedText + "'. Actual page text: " + pageText);
	}

	private void assertCost(String expectedBasePrice, String expectedWeaponsPrice, String expectedSubtotal,
			String expectedDiscount, String expectedFinalPrice) {
		assertDisplayedPrice("base-price", expectedBasePrice);
		assertDisplayedPrice("weapons-price", expectedWeaponsPrice);
		assertDisplayedPrice("subtotal", expectedSubtotal);
		assertDisplayedDiscount(expectedDiscount);
		assertDisplayedPrice("final-price", expectedFinalPrice);
	}

	private void assertDisplayedPrice(String elementId, String expectedValue) {
		String actualValue = textOf(elementId).replace(',', '.').replaceAll("[^0-9.]", "");
		assertEquals(expectedValue, actualValue, "Unexpected value for #" + elementId);
	}

	private void assertDisplayedDiscount(String expectedValue) {
		String actualValue = textOf("discount").replaceAll("[^0-9]", "");
		assertEquals(expectedValue, actualValue, "Unexpected discount percentage");
	}
}
