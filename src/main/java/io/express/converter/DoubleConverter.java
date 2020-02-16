package io.express.converter;

/**
 * Convert String to Double.
 */
public class DoubleConverter implements Converter<Double> {
    public Double convert(String s) {
        return Double.parseDouble(s);
    }
}
