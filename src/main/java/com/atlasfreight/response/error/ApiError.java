package com.atlasfreight.response.error;

import com.atlasfreight.response.error.suberror.ApiSubError;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Standardized error response container for API operations.
 *
 * <p>This record represents a structured error response that includes HTTP status code,
 * timestamp, user-friendly message, debug information, and optional sub-errors for
 * detailed validation or business rule violations.</p>
 *
 * <p>All error responses in the API should use this standardized format to ensure
 * consistency and better client-side error handling.</p>
 *
 * @param status the HTTP status code associated with the error
 * @param timestamp the date and time when the error occurred
 * @param message the user-friendly error message
 * @param debugMessage detailed technical information for debugging purposes
 * @param subErrors list of specific sub-errors providing detailed validation information
 *
 * @author Eduardo Díaz
 * @version 1.0
 * @see ApiSubError
 */
public record ApiError(
        int status,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
        LocalDateTime timestamp,
        String message,
        String debugMessage,
        List<ApiSubError> subErrors
) {

    /**
     * Constructs an ApiError with validation of parameters.
     *
     * @param status the HTTP status code (must be 4xx or 5xx)
     * @param message the user-friendly error message (cannot be null or blank)
     * @param debugMessage detailed technical information for debugging
     * @param subErrors list of specific sub-errors
     * @throws IllegalArgumentException if status is not an error code or message is invalid
     */
    public ApiError {
        if (status < 400 || status >= 600) {
            throw new IllegalArgumentException("Status code must be in the 4xx or 5xx range for errors");
        }

        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("Error message cannot be null or blank");
        }

        subErrors = subErrors != null ?
                Collections.unmodifiableList(subErrors) :
                Collections.emptyList();

        timestamp = Objects.requireNonNullElse(timestamp, LocalDateTime.now());
    }

    /**
     * Convenience constructor with automatic timestamp and empty subErrors.
     *
     * @param status the HTTP status code
     * @param message the user-friendly error message
     */
    public ApiError(int status, String message, String debugMessage, List<ApiSubError> subErrors) {
        this(status, LocalDateTime.now(), message, debugMessage, subErrors);
    }

    /**
     * Builder class for creating ApiError instances fluently.
     */
    public static class Builder {
        private int status;
        private String message;
        private String debugMessage;
        private List<ApiSubError> subErrors;

        public Builder status(int status) {
            this.status = status;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder debugMessage(String debugMessage) {
            this.debugMessage = debugMessage;
            return this;
        }

        public Builder subErrors(List<ApiSubError> subErrors) {
            this.subErrors = subErrors;
            return this;
        }

        public ApiError build() {
            return new ApiError(status, message, debugMessage, subErrors);
        }
    }

    /**
     * Creates a new builder for ApiError.
     *
     * @return a new ApiError builder instance
     */
    public static Builder builder() {
        return new Builder();
    }
}