package me.tomassetti;

import java.util.Map;

/**
 * <p>RequestHandler interface.</p>
 *
 * @author ftomassetti
 * @version 1:0
 */
public interface RequestHandler<V extends Validable> {

    /**
     * <p>process.</p>
     *
     * @param value a V object.
     * @param urlParams a {@link java.util.Map} object.
     * @param shouldReturnHtml a boolean.
     * @return a {@link me.tomassetti.Answer} object.
     */
    Answer process(V value, Map<String, String> urlParams, boolean shouldReturnHtml);

}
