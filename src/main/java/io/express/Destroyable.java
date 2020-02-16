package io.express;

/**
 * Object which has resource to release should implement this interface.
 *
 * @author Nixin
 */
public interface Destroyable {
    /**
     * Called when container destroy the object.
     */
    void destroy();
}
