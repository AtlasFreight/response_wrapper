/**
 * Provides standardized response patterns for microservices architecture.
 *
 * <p>This package contains classes and interfaces that implement the Result Pattern
 * to normalize API responses across microservices. It enables consistent handling
 * of both successful responses and error conditions, improving API contract clarity
 * and client-side processing.</p>
 *
 * <p>Key features include:
 * <ul>
 *   <li>Type-safe response handling with generics</li>
 *   <li>Standardized error structure with status codes and timestamps</li>
 *   <li>Support for nested validation and business errors</li>
 *   <li>Compatibility with JSON serialization frameworks</li>
 *   <li>Functional-style methods for response transformation</li>
 * </ul>
 * </p>
 *
 * <p>Example usage:
 * <pre>{@code
 * Response<User> response = userService.getUser(id);
 * if (response.isSuccess()) {
 *     User user = response.getData().orElseThrow();
 *     // Process user data
 * } else {
 *     ApiError error = response.getError().orElseThrow();
 *     // Handle error appropriately
 * }
 * }</pre>
 * </p>
 *
 * @author Eduardo Díaz
 * @version 1.0
 */
package response;

/**
 * Defines a contract for standardized API responses in a microservices architecture.
 *
 * <p>The {@code ResponsePattern} interface provides a consistent structure for
 * handling both successful and failed operations, enabling better error handling
 * and response processing across services.</p>
 *
 * @param <T> the type of data contained in a successful response
 *
 * @author Eduardo Díaz
 * @version 1.0
 */
public interface ResponsePattern<T> {

    /**
     * Indicates whether the operation was successful.
     *
     * @return {@code true} if the operation completed successfully, {@code false} otherwise
     */
    boolean isSuccess();

    /**
     * Returns the error details if the operation failed.
     *
     * <p>This method returns an {@link Optional} that will be empty for successful responses
     * and contain an {@link ApiError} for failed operations.</p>
     *
     * @return an {@link Optional} containing error details if available, empty otherwise
     */
    Optional<ApiError> getError();

    /**
     * Returns the response data if the operation was successful.
     *
     * <p>This method returns an {@link Optional} that will contain the response data
     * for successful operations and be empty for failed ones.</p>
     *
     * @return an {@link Optional} containing the response data if available, empty otherwise
     */
    Optional<T> getData();

    /**
     * Returns the HTTP status code associated with the response.
     *
     * <p>For successful responses, this typically returns 2xx status codes.
     * For errors, this returns the appropriate 4xx or 5xx status code.</p>
     *
     * @return the HTTP status code representing the response outcome
     */
    int getStatusCode();
}