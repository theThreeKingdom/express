package io.express.persist.convert;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IntegerConverter implements ResultConverter<Integer> {
    @Override
    public Integer convert(ResultSet rs) throws SQLException {
        return rs.getInt(1);
    }
}
