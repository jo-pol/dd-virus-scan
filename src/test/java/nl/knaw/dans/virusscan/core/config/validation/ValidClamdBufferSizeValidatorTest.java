package nl.knaw.dans.virusscan.core.config.validation;

import nl.knaw.dans.virusscan.core.config.ClamdConfig;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidClamdBufferSizeValidatorTest {

    @Test
    void isValid() {
        // chunk size should be at least twice as big as overlap size
        // overlap size should be at least twice as big as buffer size
        var config = new ClamdConfig();
        config.setChunksize(1024*1024);
        config.setOverlapsize(2048);
        config.setBuffersize(512);

        var result = new ValidClamdBufferSizeValidator().isValid(config, null);

        assertTrue(result);
    }

    @Test
    void bufferSizeIsTooLarge() {
        // chunk size should be at least twice as big as overlap size
        // overlap size should be at least twice as big as buffer size
        var config = new ClamdConfig();
        config.setChunksize(1024*1024);
        config.setOverlapsize(2048);
        config.setBuffersize(1500);

        var result = new ValidClamdBufferSizeValidator().isValid(config, null);

        assertFalse(result);
    }

    @Test
    void overlapSizeIsTooLarge() {
        // chunk size should be at least twice as big as overlap size
        // overlap size should be at least twice as big as buffer size
        var config = new ClamdConfig();
        config.setChunksize(1024*1024);
        config.setOverlapsize(1024*1023);
        config.setBuffersize(1500);

        var result = new ValidClamdBufferSizeValidator().isValid(config, null);

        assertFalse(result);
    }

    @Test
    void overlapSizeIsBiggerThanChunksize() {
        // chunk size should be at least twice as big as overlap size
        // overlap size should be at least twice as big as buffer size
        var config = new ClamdConfig();
        config.setChunksize(1024*1024);
        config.setOverlapsize(1024*2048);
        config.setBuffersize(1500);

        var result = new ValidClamdBufferSizeValidator().isValid(config, null);

        assertFalse(result);
    }
}