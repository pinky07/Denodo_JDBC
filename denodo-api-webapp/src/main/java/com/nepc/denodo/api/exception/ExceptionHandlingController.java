package com.nepc.denodo.api.exception;

import com.nepc.denodo.api.exception.error.ApiValidationError;
import com.nepc.denodo.api.exception.error.NepcApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

/**
 * Handles all exceptions that can occur in the application
 *
 * @author Minor Madrigal
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
public class ExceptionHandlingController extends ResponseEntityExceptionHandler
{
	/**
	 * Handle MissingServletRequestParameterException. Triggered when a 'required' request parameter is
	 * missing.
	 *
	 * @param ex      MissingServletRequestParameterException
	 * @param headers HttpHeaders
	 * @param status  HttpStatus
	 * @param request WebRequest
	 * @return the ApiError object
	 */
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request)
	{
		String error = ex.getParameterName() + " parameter is missing";
		return buildResponseEntity(new NepcApiError(HttpStatus.BAD_REQUEST, error, ex));
	}

	/**
	 * Handle HttpMediaTypeNotSupportedException. This one triggers when JSON is invalid as well.
	 *
	 * @param ex      HttpMediaTypeNotSupportedException
	 * @param headers HttpHeaders
	 * @param status  HttpStatus
	 * @param request WebRequest
	 * @return the ApiError object
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request)
	{
		StringBuilder builder = new StringBuilder();
		builder.append(ex.getContentType());
		builder.append(" media type is not supported. Supported media types are ");
		ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
		return buildResponseEntity(new NepcApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, builder
				.substring(0, builder.length() - 2), ex));
	}

	/**
	 * Handle MethodArgumentNotValidException. Triggered when an object fails @Valid validation.
	 *
	 * @param ex      the MethodArgumentNotValidException that is thrown when @Valid validation fails
	 * @param headers HttpHeaders
	 * @param status  HttpStatus
	 * @param request WebRequest
	 * @return the ApiError object
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request)
	{
		NepcApiError apiError = new NepcApiError(HttpStatus.BAD_REQUEST);
		apiError.setMessage("Validation error");
		ex.getBindingResult().getFieldErrors().stream().map(ApiValidationError::new).forEach(apiError::addSubError);
		ex.getBindingResult().getGlobalErrors().stream().map(ApiValidationError::new).forEach(apiError::addSubError);
		return buildResponseEntity(apiError);
	}

	// /**
	//  * Handles org.springframework.security.authentication.BadCredentialsException. Thrown when @Validated
	//  * fails.
	//  *
	//  * @param ex the BadCredentialsException
	//  * @return the ApiError object
	//  */
	// @ExceptionHandler(BadCredentialsException.class)
	// protected ResponseEntity<Object> handleBadCredentials(BadCredentialsException ex)
	// {
	//	NepcApiError apiError = new NepcApiError(HttpStatus.BAD_REQUEST);
	//	apiError.setMessage("Bad credentials");
	//	return buildResponseEntity(apiError);
	// }

	/**
	 * Handles IllegalArgumentException.
	 *
	 * @param ex the IllegalArgumentException
	 * @return the ApiError object
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	protected ResponseEntity<Object> handleMalformedJwt(IllegalArgumentException ex)
	{
		NepcApiError apiError = new NepcApiError(HttpStatus.BAD_REQUEST);
		apiError.setMessage(ex.getMessage());
		return buildResponseEntity(apiError);
	}

	/**
	 * Handles javax.validation.ConstraintViolationException. Thrown when @Validated fails.
	 *
	 * @param ex the ConstraintViolationException
	 * @return the ApiError object
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	protected ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex)
	{
		NepcApiError apiError = new NepcApiError(HttpStatus.BAD_REQUEST);
		apiError.setMessage("Validation error");
		ex.getConstraintViolations().stream().map(ApiValidationError::new).forEach(apiError::addSubError);
		return buildResponseEntity(apiError);
	}

	/**
	 * Handles EdukoEntityNotFoundException. Created to encapsulate errors with more detail than
	 * javax.persistence.EdukoEntityNotFoundException.
	 *
	 * @param ex the EdukoEntityNotFoundException
	 * @return the ApiError object
	 */
	@ExceptionHandler(NepcEntityNotFoundException.class)
	protected ResponseEntity<Object> handleEntityNotFound(NepcEntityNotFoundException ex)
	{
		NepcApiError apiError = new NepcApiError(HttpStatus.NOT_FOUND);
		apiError.setMessage(ex.getMessage());
		return buildResponseEntity(apiError);
	}

	/**
	 * Handle HttpMessageNotReadableException. Happens when request JSON is malformed.
	 *
	 * @param ex      HttpMessageNotReadableException
	 * @param headers HttpHeaders
	 * @param status  HttpStatus
	 * @param request WebRequest
	 * @return the ApiError object
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request)
	{
		ServletWebRequest servletWebRequest = (ServletWebRequest) request;
		log.info("{} to {}", servletWebRequest.getHttpMethod(), servletWebRequest.getRequest().getServletPath());
		String error = "Malformed JSON request";
		return buildResponseEntity(new NepcApiError(HttpStatus.BAD_REQUEST, error, ex));
	}

	/**
	 * Handle HttpMessageNotWritableException.
	 *
	 * @param ex      HttpMessageNotWritableException
	 * @param headers HttpHeaders
	 * @param status  HttpStatus
	 * @param request WebRequest
	 * @return the ApiError object
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request)
	{
		String error = "Error writing JSON output";
		return buildResponseEntity(new NepcApiError(HttpStatus.INTERNAL_SERVER_ERROR, error, ex));
	}

	/**
	 * Handle DataIntegrityViolationException, inspects the cause for different DB causes.
	 *
	 * @param ex the DataIntegrityViolationException
	 * @return the ApiError object
	 */
	@ExceptionHandler(DataIntegrityViolationException.class)
	protected ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex,
			WebRequest request)
	{
		if (ex.getCause() instanceof ConstraintViolationException)
		{
			return buildResponseEntity(new NepcApiError(HttpStatus.CONFLICT, "Database error", ex.getCause()));
		}
		return buildResponseEntity(new NepcApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex));
	}

	/**
	 * Handle Exception, handle generic Exception.class
	 *
	 * @param ex the Exception
	 * @return the ApiError object
	 */
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
			WebRequest request)
	{
		NepcApiError apiError = new NepcApiError(HttpStatus.BAD_REQUEST);
		apiError.setMessage(String
				.format("The parameter '%s' of value '%s' could not be converted to type '%s'", ex.getName(), ex
						.getValue(), ex.getRequiredType().getSimpleName()));
		apiError.setDebugMessage(ex.getMessage());
		return buildResponseEntity(apiError);
	}

	private ResponseEntity<Object> buildResponseEntity(NepcApiError apiError)
	{
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}
}
