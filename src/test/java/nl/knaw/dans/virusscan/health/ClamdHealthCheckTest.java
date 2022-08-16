/*
 * Copyright (C) 2022 DANS - Data Archiving and Networked Services (info@dans.knaw.nl)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nl.knaw.dans.virusscan.health;

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