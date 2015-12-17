package me.tomassetti;

import java.util.UUID;

/**
 * Our generator of UUIDs, just random.
 *
 * @author ftomassetti
 * @since Mar 2015
 * @version 1:0
 */
public class RandomUuidGenerator implements UuidGenerator {
    
    /** {@inheritDoc} */
    @Override
    public UUID generate() {
        return UUID.randomUUID();
    }
}
