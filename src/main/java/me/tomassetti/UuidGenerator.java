package me.tomassetti;

import java.util.UUID;

/**
 * A UUID generator.
 *
 * @author ftomassetti
 * @since Mar 2015
 * @version 1:0
 */
public interface UuidGenerator {

    /**
     * Each call should return a different UUID.
     *
     * @return a {@link java.util.UUID} object.
     */
    UUID generate();
}
