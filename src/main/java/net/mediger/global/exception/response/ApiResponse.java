package net.mediger.global.exception.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.mediger.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private HttpStatus status;
    private int code;
    private String codeName;
    private String message;
    private T data;

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(HttpStatus.OK, HttpStatus.OK.value(), null, null, data);
    }

    public static ApiResponse<Void> success(HttpStatus status) {
        return new ApiResponse<>(status, status.value(), null, null, null);
    }

    public static <T> ApiResponse<T> error(ErrorCode errorCode) {
        return new ApiResponse<>(errorCode.getStatus(), errorCode.getStatus().value(), errorCode.getCodeName(),
                errorCode.getMessage(), null);
    }
}
