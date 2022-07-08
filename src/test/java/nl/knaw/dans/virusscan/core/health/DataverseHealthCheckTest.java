package nl.knaw.dans.virusscan.core.health;

import nl.knaw.dans.lib.dataverse.DataverseException;
import nl.knaw.dans.virusscan.core.model.DataverseVersionResponse;
import nl.knaw.dans.virusscan.core.service.DataverseApiService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DataverseHealthCheckTest {

    @Test
    void checkSuccessful() throws IOException {

        var service = Mockito.mock(DataverseApiService.class);
        Mockito.when(service.getDataverseInfo()).thenReturn(new DataverseVersionResponse("OK"));

        var result = new DataverseHealthCheck(service).check();

        assertTrue(result.isHealthy());
    }

    @Test
    void checkUnsuccessful() throws IOException {

        var service = Mockito.mock(DataverseApiService.class);
        Mockito.when(service.getDataverseInfo()).thenReturn(new DataverseVersionResponse("NOT OK"));

        var result = new DataverseHealthCheck(service).check();

        assertFalse(result.isHealthy());
    }

    @Test
    void checkIOExceptionsBeingCaught() throws IOException {

        var service = Mockito.mock(DataverseApiService.class);
        Mockito.doThrow(IOException.class).when(service).getDataverseInfo();

        var result = new DataverseHealthCheck(service).check();

        assertFalse(result.isHealthy());
    }
}