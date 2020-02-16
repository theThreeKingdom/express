package io.express.persist.convert;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultConverter<T>
{
	public T convert(ResultSet rs) throws SQLException ;
}
