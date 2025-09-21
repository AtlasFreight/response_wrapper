package com.atlasfreight.response;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Base class for API sub-errors that provides detailed information about specific validation
 * or business rule violations within an {@link ApiError}.
 *
 * <p>This abstract class serves as a marker for all types of sub-errors that can be included
 * in an API error response. Concrete implementations should provide specific error details
 * for different types of validation or business rule violations.</p>
 *
 * <p>Using JSON polymorphism annotations to handle different sub-error types during serialization.</p>
 *
 * @author Eduardo Díaz
 * @version 1.0
 * @see ApiError
 * @see ApiValidationError
 * @see ApiBusinessError
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ApiValidationError.class, name = "validation"),
        @JsonSubTypes.Type(value = ApiBusinessError.class, name = "business"),
        @JsonSubTypes.Type(value = ApiAuthenticationError.class, name = "authentication")
})
public abstract class ApiSubError {

    /**
     * Returns the error type identifier for polymorphism.
     *
     * @return a string representing the specific error type
     */
    public abstract String getType();

    /**
     * Returns a user-friendly description of the error.
     *
     * @return a descriptive error message
     */
    public abstract String getDescription();
}