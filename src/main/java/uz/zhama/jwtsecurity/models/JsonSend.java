package uz.zhama.jwtsecurity.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public class JsonSend<T> {
    public enum Status {
        SUCCESS("success"), FAIL("fail"), ERROR("error");

        private final String value;

        Status(String value) {
            this.value = value;
        }

        @JsonValue
        public String getValue() {
            return value;
        }

        public boolean isSuccess() {
            return equals(SUCCESS);
        }

        public boolean isFail() {
            return equals(FAIL);
        }

        public boolean isError() {
            return equals(ERROR);
        }
    }

    @JsonProperty
    private final Status status;

    @JsonProperty
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private String code;

    @JsonProperty
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private String message;

    @JsonProperty
    private T data;

    private JsonSend(Status status, String code, String message, T data) {

        this.status = status;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Status getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    @JsonCreator // for work un-serialization
    public static <T> JsonSend<T> success() {
        return JsonSend.success(null, null);
    }

    public static <T> JsonSend<T> success(T data) {
        return JsonSend.success(null, data);
    }

    public static <T> JsonSend<T> success(String code, T data) {
        return new JsonSend<>(Status.SUCCESS, code, null, data);
    }

    public static <T> JsonSend<T> fail() {
        return JsonSend.fail(null, null);
    }

    public static <T> JsonSend<T> fail(String code) {
        return JsonSend.fail(code, null);
    }

    public static <T> JsonSend<T> fail(String code, T data) {
        return new JsonSend<>(Status.FAIL, code, null, data);
    }

    public static <T> JsonSend<T> error() {
        return JsonSend.error(null, null, null);
    }

    public static <T> JsonSend<T> error(String code) {
        return JsonSend.error(null, code, null);
    }

    public static <T> JsonSend<T> error(String message, String code) {
        return JsonSend.error(message, code, null);
    }

    public static <T> JsonSend<T> error(String message, String code, T data) {
        return new JsonSend<>(Status.ERROR, code, message, data);
    }

    public static class Builder<T> {
        private Status status;
        private String code;
        private String message;
        private T data;

        public Builder<T> success() {
            status = Status.SUCCESS;
            return this;
        }

        public Builder<T> fail() {
            status = Status.FAIL;
            return this;
        }

        public Builder<T> error() {
            status = Status.ERROR;
            return this;
        }

        public Builder<T> code(String code) {
            this.code = code;
            return this;
        }

        public Builder<T> message(String message) {
            this.message = message;
            return this;
        }

        public Builder<T> data(T data) {
            this.data = data;
            return this;
        }

        public JsonSend<T> build() {
            return new JsonSend<>(status, code, message, data);
        }
    }

    @Override
    public String toString() {
        return "{" +
                "status=" + status +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
