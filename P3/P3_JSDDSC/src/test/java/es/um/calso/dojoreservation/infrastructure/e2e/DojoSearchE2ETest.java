package es.um.calso.dojoreservation.infrastructure.e2e;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class DojoSearchE2ETest extends BrowserE2ESupport {

    @Test
    void testGivenValidTeamAndFutureDateWhenSearchThenShowsAvailableDojo() {
        // CP1 y límite superior: equipo válido y fecha de "mañana" respecto al reloj E2E
        searchDojos("EQ-001", "2026-10-21");
        
        // ahora verificamos que la tabla de dojos se ha renderizado
        String tableContent = textOf("dojo-table");
        assertNotNull(tableContent, "La tabla de dojos debería estar presente en el DOM");
        assertFalse(tableContent.isBlank(), "La tabla de dojos no debería estar vacía");
    }

    @Test
    void testGivenEmptyTeamWhenSearchThenShowsTeamRequiredError() {
        // CP2: equipo vacío. Como el select no tiene opción con value="", me ha dado error al ejecutarlo por primera vez
        // por eso, siguiendo la recomendación de 'guia pruebas', inyectamos el teamId vacío directamente por URL con open()
        open("/dojos?teamId=&trainingDate=2026-10-25");
        
        assertEquals("Team id is required", textOf("error"));
    }

    @Test
    void testGivenNonExistentTeamWhenSearchThenShowsTeamDoesNotExistError() {
        // CP3: el identificador EQ-999 no existe en el desplegable
        // simulamos que un atacante manipula los parámetros de la url  directamente
        open("/dojos?teamId=EQ-999&trainingDate=2026-10-25");
        
        assertEquals("Team does not exist", textOf("error"));
    }

    @Test
    void testGivenTodayDateWhenSearchThenShowsDateMustBeFutureError() {
        // CP4 y límite inferior: intentamos reservar hoy (20 de octubre de 2026 en E2E)
        searchDojos("EQ-001", "2026-10-20");
        
        assertEquals("Training date must be in the future", textOf("error"));
    }
}