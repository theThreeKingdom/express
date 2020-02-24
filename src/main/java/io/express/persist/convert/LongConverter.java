package io.express.persist.convert;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LongConverter implements ResultConverter<Long> {

    @Override
    public Long convert(ResultSet rs) throws SQLException {
        return rs.getLong(1);
    }

}
