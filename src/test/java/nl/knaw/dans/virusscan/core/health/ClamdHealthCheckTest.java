package nl.knaw.dans.virusscan.core.health;

import nl.knaw.dans.virusscan.core.service.ClamdService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClamdHealthCheckTest {

    @Test
    void checkSuccessful() throws IOException {
        var clamdService = Mockito.mock(ClamdService.class);
        Mockito.when(clamdService.ping()).thenReturn("PONG\n");
        var check = new ClamdHealthCheck(clamdService);
        var result = check.check();

        assertTrue(result.isHealthy());
    }

    @Test
    void checkIncorrectOutputFromClamAV() throws IOException {
        var clamdService = Mockito.mock(ClamdService.class);
        Mockito.when(clamdService.ping()).thenReturn("BOGUS VALUE\n");
        var check = new ClamdHealthCheck(clamdService);
        var result = check.check();

        assertFalse(result.isHealthy());
    }

    @Test
    void checkIOExceptionOccursDuringCheck() throws IOException {
        var clamdService = Mockito.mock(ClamdService.class);
        Mockito.doThrow(IOException.class).when(clamdService).ping();
        var check = new ClamdHealthCheck(clamdService);
        var result = check.check();

        assertFalse(result.isHealthy());
    }
}