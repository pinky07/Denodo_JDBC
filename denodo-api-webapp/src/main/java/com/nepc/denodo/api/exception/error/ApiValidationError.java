package com.nepc.denodo.api.exception.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import javax.validation.ConstraintViolation;

/**
 * Validation error
 *
 * @author Minor Madrigal
 */
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@Builder
public class ApiValidationError implements NepcApiSubError
{
	// Name of the class in which the field is contained
	private String className;

	// Field in which the validation error happened
	private String field;

	// Rejected value for the field
	private Object rejectedValue;

	// User friendly message
	private String message;

	/**
	 * Creates a new Object from a ConstraintViolation. The latter usually occurs when a @Validated annotation fails.
	 *
	 * @param constraintViolation ConstraintViolation
	 */
	public ApiValidationError(ConstraintViolation<?> constraintViolation)
	{
		this.className = constraintViolation.getRootBeanClass().getSimpleName();
		this.field = ((PathImpl) constraintViolation.getPropertyPath()).getLeafNode().asString();
		this.rejectedValue = constraintViolation.getInvalidValue();
		this.message = constraintViolation.getMessage();
	}

	/**
	 * Creates a new Object from a FieldError.
	 *
	 * @param fieldError FieldError
	 */
	public ApiValidationError(FieldError fieldError)
	{
		this.className = fieldError.getObjectName();
		this.field = fieldError.getField();
		this.rejectedValue = fieldError.getRejectedValue();
		this.message = fieldError.getDefaultMessage();
	}

	/**
	 * Creates a new Object from an Object Error
	 *
	 * @param objectError Object Error
	 */
	public ApiValidationError(ObjectError objectError)
	{
		this.className = objectError.getObjectName();
		this.field = null;
		this.rejectedValue = null;
		this.message = objectError.getDefaultMessage();
	}
}
