package io.express.converter;

/**
 * Convert String to Short.
 */
public class ShortConverter implements Converter<Short> {
    public Short convert(String s) {
        return Short.parseShort(s);
    }
}
