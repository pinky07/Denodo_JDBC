package com.nepc.denodo.api.exception;

/**
 * Wraps all possible exceptions in this application.
 *
 * @author Rubén Jiménez
 */
public class NepcDenodoException extends Exception
{
	/**
	 * Creates a new Exception.
	 *
	 * @param message User friendly message
	 */
	public NepcDenodoException(String message)
	{
		super(message);
	}
}
