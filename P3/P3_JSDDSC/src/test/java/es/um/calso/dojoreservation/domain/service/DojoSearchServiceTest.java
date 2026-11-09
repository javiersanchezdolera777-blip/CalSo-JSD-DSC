package es.um.calso.dojoreservation.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.um.calso.dojoreservation.domain.model.Dojo;
import es.um.calso.dojoreservation.domain.model.Money;
import es.um.calso.dojoreservation.domain.model.Reservation;
import es.um.calso.dojoreservation.domain.model.Weapon;

class DojoSearchServiceTest {

    private DojoSearchService searchService;
    private Weapon katana;
    private Weapon bo;
    private Dojo dojoEmpty;
    private Dojo dojoWithKatana;
    private Dojo dojoWithBoth;
    private LocalDate today;

    @BeforeEach
    void setUp() {
        searchService = new DojoSearchService();
        katana = new Weapon("Katana");
        bo = new Weapon("Bo");
        
        // instancisamos un objeto Money ficticio para que el Dojo no falle
        Money dummyPrice = new Money(java.math.BigDecimal.ZERO); 
        
        dojoEmpty = new Dojo("D1", "Dojo Vacio", dummyPrice, new HashSet<>());
        dojoWithKatana = new Dojo("D2", "Dojo Katana", dummyPrice, Set.of(katana));
        dojoWithBoth = new Dojo("D3", "Dojo Completo", dummyPrice, Set.of(katana, bo));
        
        today = LocalDate.of(2026, 10, 20);
    }

    // ==========================================
    // TESTS PARA containsAllRequiredWeapons
    // ==========================================

    @Test
    void testGivenAvailableWeaponsNullWhenCheckingContainsAllThenThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> 
            searchService.containsAllRequiredWeapons(null, Set.of(katana))
        );
    }

    @Test
    void testGivenRequiredWeaponsNullWhenCheckingContainsAllThenThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> 
            searchService.containsAllRequiredWeapons(Set.of(katana), null)
        );
    }

    @Test
    void testGivenEmptyRequiredWeaponsWhenCheckingContainsAllThenReturnsTrue() {
        assertTrue(searchService.containsAllRequiredWeapons(Set.of(katana), new HashSet<>()));
    }

    @Test
    void testGivenMissingRequiredWeaponWhenCheckingContainsAllThenReturnsFalse() {
    	// C4: no hay armas disponibles y se requiere una katana
        assertFalse(searchService.containsAllRequiredWeapons(new HashSet<>(), Set.of(katana)));
    }
    
    @Test
    void testGivenAvailableKatanaAndMissingBoWhenCheckingContainsAllThenReturnsFalse() {
        Set<Weapon> available = Set.of(katana);
        Set<Weapon> required = Set.of(katana, bo);

        assertFalse(searchService.containsAllRequiredWeapons(available, required));
    }

    // ==========================================
    // TESTS PARA findFirstAvailableDojo
    // ==========================================

    @Test
    void testGivenNullDojosWhenFindFirstAvailableThenThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> 
            searchService.findFirstAvailableDojo(null, Set.of(katana), new ArrayList<>(), today)
        );
    }

    @Test
    void testGivenNullRequiredWeaponsWhenFindFirstAvailableThenThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> 
            searchService.findFirstAvailableDojo(List.of(dojoWithKatana), null, new ArrayList<>(), today)
        );
    }

    @Test
    void testGivenNullReservationsWhenFindFirstAvailableThenThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> 
            searchService.findFirstAvailableDojo(List.of(dojoWithKatana), Set.of(katana), null, today)
        );
    }

    @Test
    void testGivenNullTrainingDateWhenFindFirstAvailableThenThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> 
            searchService.findFirstAvailableDojo(List.of(dojoWithKatana), Set.of(katana), new ArrayList<>(), null)
        );
    }

    @Test
    void testGivenEmptyDojoListWhenFindFirstAvailableThenReturnsEmpty() {
        Optional<Dojo> result = searchService.findFirstAvailableDojo(new ArrayList<>(), Set.of(katana), new ArrayList<>(), today);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGivenDojoWithoutRequiredWeaponsWhenFindFirstAvailableThenReturnsEmpty() {
        Optional<Dojo> result = searchService.findFirstAvailableDojo(List.of(dojoEmpty), Set.of(katana), new ArrayList<>(), today);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGivenAvailableDojoWithoutReservationsWhenFindFirstAvailableThenReturnsDojo() {
        Optional<Dojo> result = searchService.findFirstAvailableDojo(List.of(dojoWithKatana), Set.of(katana), new ArrayList<>(), today);
        assertTrue(result.isPresent());
        assertEquals("D2", result.get().id());
    }

    @Test
    void testGivenDojoWithReservationOnDifferentDateWhenFindFirstAvailableThenReturnsDojo() {
        Reservation diffDateRes = new Reservation("D2", today.plusDays(1));
        Optional<Dojo> result = searchService.findFirstAvailableDojo(List.of(dojoWithKatana), Set.of(katana), List.of(diffDateRes), today);
        assertTrue(result.isPresent());
    }

    @Test
    void testGivenDojoWithReservationOnSameDateWhenFindFirstAvailableThenReturnsEmpty() {
        Reservation sameDateRes = new Reservation("D2", today);
        Optional<Dojo> result = searchService.findFirstAvailableDojo(List.of(dojoWithKatana), Set.of(katana), List.of(sameDateRes), today);
        // Este test fallará inicialmente por el defecto lógico del "!" en el if de ocupación
        assertTrue(result.isEmpty());
    }
    
    @Test
    void testGivenFirstDojoOccupiedAndSecondFreeWhenFindFirstAvailableThenReturnsSecondDojo() {
        // Dojo 1 está ocupado hoy, pero Dojo 3 (que tiene las armas requeridas) está libre
        Reservation sameDateRes = new Reservation("D2", today);
        Optional<Dojo> result = searchService.findFirstAvailableDojo(List.of(dojoWithKatana, dojoWithBoth), Set.of(katana), List.of(sameDateRes), today);
        
        assertTrue(result.isPresent());
        assertEquals("D3", result.get().id());
    }
    
    @Test
    void testGivenReservationForDifferentDojoOnSameDateWhenFindFirstAvailableThenReturnsDojo() {
        Reservation otherDojoReservation = new Reservation("D99", today);

        Optional<Dojo> result = searchService.findFirstAvailableDojo(
                List.of(dojoWithKatana),
                Set.of(katana),
                List.of(otherDojoReservation),
                today
        );

        assertTrue(result.isPresent());
        assertEquals("D2", result.get().id());
    }
}