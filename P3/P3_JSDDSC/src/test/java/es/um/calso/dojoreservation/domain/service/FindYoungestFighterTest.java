package es.um.calso.dojoreservation.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import es.um.calso.dojoreservation.domain.model.Fighter;

class FindYoungestFighterTest {
    private final ReservationDiscountService service = new ReservationDiscountService();
    private static final LocalDate REFERENCE_DATE = LocalDate.of(2026, 10, 20);
    @Test
    void testGivenNullFightersWhenFindYoungestFighterThenThrowsFightersAreRequired() {
        NullPointerException exception = assertThrows(NullPointerException.class,() -> service.findYoungestFighter(null, REFERENCE_DATE));
        assertEquals("Fighters are required", exception.getMessage());
    }

    @Test
    void testGivenNullReferenceDateWhenFindYoungestFighterThenThrowsReferenceDateIsRequired() {
        NullPointerException exception = assertThrows(NullPointerException.class,() -> service.findYoungestFighter(List.of(), null));
        assertEquals("Reference date is required", exception.getMessage());
    }

    @Test
    void testGivenEmptyFightersWhenFindYoungestFighterThenReturnsEmpty() {
        Optional<Fighter> result = service.findYoungestFighter(List.of(),REFERENCE_DATE);
        assertEquals(Optional.empty(), result);
    }

    @Test
    void testGivenNullFighterWhenFindYoungestFighterThenThrowsNullFighter() {
        List<Fighter> fighters = Collections.singletonList(null);
        NullPointerException exception = assertThrows(NullPointerException.class,() -> service.findYoungestFighter(fighters, REFERENCE_DATE));

        assertEquals("Null fighter", exception.getMessage());
    }

    @Test
    void testGivenOneFighterWhenFindYoungestFighterThenReturnsThatFighter() {
        Fighter fighter = new Fighter("Fighter 1",LocalDate.of(2000, 5, 10),null);
        Optional<Fighter> result = service.findYoungestFighter(List.of(fighter),REFERENCE_DATE);
        assertSame(fighter, result.orElseThrow());
    }

    @Test
    void testGivenSecondFighterBornEarlierWhenFindYoungestFighterThenReturnsFirstFighter() {
        Fighter first = new Fighter("Fighter 1",LocalDate.of(2000, 5, 10),null);
        Fighter second = new Fighter("Fighter 2",LocalDate.of(1999, 2, 20),null);
        Optional<Fighter> result = service.findYoungestFighter(List.of(first, second),REFERENCE_DATE);
        assertSame(first, result.orElseThrow());
    }

    @Test
    void testGivenSecondFighterBornLaterWhenFindYoungestFighterThenReturnsSecondFighter() {
        Fighter first = new Fighter("Fighter 1",LocalDate.of(2000, 5, 10),null);
        Fighter second = new Fighter("Fighter 2",LocalDate.of(2005, 2, 20),null);
        Optional<Fighter> result = service.findYoungestFighter(List.of(first, second),REFERENCE_DATE);
        assertSame(second, result.orElseThrow());
    }

    @Test
    void testGivenTwoFightersWithSameBirthDateWhenFindYoungestFighterThenReturnsFirstFighter() {
        Fighter first = new Fighter("Fighter 1",
                LocalDate.of(2005, 2, 20), null);

        Fighter second = new Fighter("Fighter 2", LocalDate.of(2005, 2, 20),null);
        Optional<Fighter> result = service.findYoungestFighter(List.of(first, second),REFERENCE_DATE);
        assertSame(first, result.orElseThrow());
    }
}