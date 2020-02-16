package io.express.converter;

/**
 * Convert String to Byte.
 */
public class ByteConverter implements Converter<Byte> {
    public Byte convert(String s) {
        return Byte.parseByte(s);
    }
}
