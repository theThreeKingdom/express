package io.express.converter;

/**
 * Convert String to Integer.
 */
public class IntegerConverter implements Converter<Integer> {
    public Integer convert(String s) {
        return Integer.parseInt(s);
    }
}
