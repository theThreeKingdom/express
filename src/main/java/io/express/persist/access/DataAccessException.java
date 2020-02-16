package io.express.persist.access;

public class DataAccessException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public DataAccessException(Throwable cause)
	{
		super(cause);
	}
}
