package io.express.converter;

/**
 * Convert String to Float.
 */
public class FloatConverter implements Converter<Float> {
    public Float convert(String s) {
        return Float.parseFloat(s);
    }
}
