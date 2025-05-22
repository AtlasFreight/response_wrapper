package response;

import java.util.Objects;

public class ApiValidationError extends ApiSubError {
    private final String object;
    private final String field;
    private final Object rejectedValue;
    private final String message;

    private ApiValidationError(Builder builder) {
        this.object = Objects.requireNonNull(builder.object, "object must not be null");
        this.field = builder.field;
        this.rejectedValue = builder.rejectedValue;
        this.message = Objects.requireNonNull(builder.message, "message must not be null");
    }

    public static class Builder {
        private String object;
        private String field;
        private Object rejectedValue;
        private String message;

        public Builder setObject(String object) {
            this.object = object;
            return this;
        }

        public Builder setField(String field) {
            this.field = field;
            return this;
        }

        public Builder setRejectedValue(Object rejectedValue) {
            this.rejectedValue = rejectedValue;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public ApiValidationError build() {
            return new ApiValidationError(this);
        }

    }

    public String getObject() {
        return object;
    }

    public String getField() {
        return field;
    }

    public Object getRejectedValue() {
        return rejectedValue;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiValidationError that = (ApiValidationError) o;
        return Objects.equals(object, that.object) &&
                Objects.equals(field, that.field) &&
                Objects.equals(rejectedValue, that.rejectedValue) &&
                Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(object, field, rejectedValue, message);
    }
}