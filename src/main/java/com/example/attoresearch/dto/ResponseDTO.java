package com.example.attoresearch.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseDTO<T> {

    //api 결과
    private boolean result;

    //api 통신 설명
    private String description;

    private T data;

    // OK
    public static <T> ResponseDTO<T> OK() {
        return (ResponseDTO<T>) ResponseDTO.builder()
                .result(true)
                .build();
    }

    // DATA OK
    public static <T> ResponseDTO<T> OK(T data) {
        return (ResponseDTO<T>)ResponseDTO.builder()
                .result(true)
                .data(data)
                .build();
    }

    // ERROR
    public static <T> ResponseDTO<T> ERROR(String description) {
        return (ResponseDTO<T>)ResponseDTO.builder()
                .result(false)
                .description(description)
                .build();
    }

}
