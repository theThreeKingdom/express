package io.express.converter;

/**
 * Convert String to any given type.
 *
 * @param <T> Generic type of converted result.
 */
public interface Converter<T> {
    /**
     * Convert a not-null String to specified object.
     */
    T convert(String s);
}
