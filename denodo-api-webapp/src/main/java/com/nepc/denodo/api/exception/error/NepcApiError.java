package com.nepc.denodo.api.exception.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import com.nepc.denodo.api.utility.LowerCaseClassNameResolver;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * DTO class for exceptions that can occur in this application.
 *
 * @author Minor Madrigal
 * @author Rubén Jiménez
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonTypeIdResolver(LowerCaseClassNameResolver.class)
public class NepcApiError
{
	// HTTP Status associated with the error
	private HttpStatus status;

	// Timestamp in which the error was generated
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;

	// User friendly message
	private String message;

	// Debug message
	private String debugMessage;

	// List of sub errors
	private List<NepcApiSubError> subErrors;

	/**
	 * Creates a new object with a default timestamp.
	 */
	private NepcApiError()
	{
		timestamp = LocalDateTime.now();
	}

	/**
	 * Crates a new object with a default timestamp and a given HTTP status.
	 *
	 * @param httpStatus Http Status associated with the exception
	 */
	public NepcApiError(HttpStatus httpStatus)
	{
		this();
		this.status = httpStatus;
	}

	/**
	 * Creates a new object with a default timestamp and a given status and throwable.
	 *
	 * @param status    Http Status associated with the exception
	 * @param throwable Throwable
	 */
	public NepcApiError(HttpStatus status, Throwable throwable)
	{
		this(status);
		this.debugMessage = throwable.getLocalizedMessage();
		this.message = "Unexpected error";
	}

	/**
	 * Creates a new object with a default timestamp and a given status, message and throwable.
	 *
	 * @param status    Http Status associated with the exception
	 * @param message   User friendly message
	 * @param throwable Throwable
	 */
	public NepcApiError(HttpStatus status, String message, Throwable throwable)
	{
		this();
		this.status = status;
		this.message = message;
		this.debugMessage = throwable.getLocalizedMessage();
	}

	/**
	 * Adds a Sub Error to this object.
	 *
	 * @param subError Sub Error to add
	 */
	public void addSubError(NepcApiSubError subError)
	{
		lazyInitSubErrors();
		subErrors.add(subError);
	}

	/**
	 * Adds all Sub Errors to this object.
	 *
	 * @param subErrorCollection Sub Errors to add
	 */
	public void addAllSubErrors(Collection<NepcApiSubError> subErrorCollection)
	{
		lazyInitSubErrors();
		subErrorCollection.addAll(subErrorCollection);
	}

	/**
	 * Performs a lazy initialization on the sub errors field.
	 */
	private void lazyInitSubErrors()
	{
		if (subErrors == null) subErrors = new ArrayList<>();
	}
}
