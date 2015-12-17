package me.tomassetti.handlers;

import me.tomassetti.Validable;

/**
 * Created by federico on 24/07/15.
 *
 * @author ftomassetti
 * @version 1:0
 */
public class EmptyPayload implements Validable {
    /** {@inheritDoc} */
    @Override
    public boolean isValid() {
        return true;
    }
}
