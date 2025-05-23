# API Response Wrapper

A lightweight and efficient tool for managing API responses. This library makes it easy to standardize and handle response structures across your application.

## Key Features

- **Standardized Structures**: Ensures that all API endpoints return data with a consistent format.
- **Flexible Configuration**: Easily adjustable to fit the specific requirements of your API.
- **High Performance**: Minimal overhead for optimized performance.

## Installation

Clone or download the project to your local environment:

```bash
git clone https://github.com/eduard2diaz/api_response_wrapper.git
```
Alternatively, you can add this library as a dependency directly in your project. Follow the instructions below based on your build tool:

- **For Maven:** Add the following to your `pom.xml` file:
  ```xml
  <dependency>
      <groupId>io.github.eduard2diaz</groupId>
      <artifactId>api_response_wrapper</artifactId>
      <version>1.0.0-beta</version>
  </dependency>
  ```

- **For Gradle:** Add the following to your `build.gradle` file:
  ```groovy
  dependencies {
      implementation 'io.github.eduard2diaz:api_response_wrapper:1.0.0-beta'
  }
  ```

## Quick Usage

### Creating Errors

Suppose you’re handling an error for a missing endpoint. You can construct an `ApiError` object as follows:

```java
import response.ApiSubError;
import response.ApiValidationError;

import java.util.List;

// Create custom sub-errors
List<ApiSubError> additionalErrors = List.of(
    new ApiValidationError.Builder()
        .setMessage("Age must be a positive number")
        .setRejectedValue(-15)
        .setField("age")
        .setObject("Person")
        .build()
);

// Create the main error object
ApiError apiError = new ApiError.Builder()
    .setMessage("Endpoint not found: " + ex.getRequestURL())
    .setException(ex) // Optional
    .setStatus(404)
    .setSubErrors(additionalErrors)
    .build();
```

### Creating Successful and Failed Responses

From an `ApiError` instance, create a failed response:

```java
Response response = Response.fail(apiError);
```

To create a successful response with generic data:

```java
Response response = Response.success(data);
```

### Example of Resulting JSON

For a success scenario:

```json
{
  "status": "success",
  "data": {
    "message": "Operation completed successfully"
  }
}
```

For an error scenario:

```json
{
  "status": "failure",
  "error": {
    "message": "Endpoint not found: /users/20",
    "code": 404,
    "subErrors": [
      {
        "field": "age",
        "rejectedValue": -15,
        "message": "Age must be a positive number",
        "object": "Person"
      }
    ]
  }
}
```

## Compatibility

This library is compatible with:

- **Java SDK 21** and later versions.
- Frameworks such as **Spring Boot** (optional).

## Contributing

Contributions are always welcome! If you encounter an issue or have an idea for a new feature, feel free to open an issue or submit a pull request.

### Steps to Contribute:

1. Fork the repository.
2. Create a new branch for your changes (either a feature or bugfix).
3. Commit your changes and push them to your forked repository.
4. Open a Pull Request for review.

## License

This project is licensed under the MIT License.