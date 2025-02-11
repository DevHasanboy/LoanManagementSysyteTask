package com.example.file.task.response;

import com.example.file.task.dto.ErrorDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> implements Serializable {
    private int code;
    private String message;
    private T data;
    private boolean success;
    private Map<String, Object> meta = new HashMap<>();
    private HttpStatus httpStatus;
    private List<ErrorDto> errorsList;

    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }


   /* public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
        if (success) {
            httpStatus = HttpStatus.OK;
        }
    }

    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
        if (success) {
            httpStatus = HttpStatus.OK;
        }
    }

    public ApiResponse(boolean success, String message, HttpStatus httpStatus) {
        this.success = success;
        this.message = message;
        this.httpStatus = httpStatus;
    }*/

}
