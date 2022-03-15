package com.hellonature.hellonature_back.model.network;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Header<T> {

    // api 통신시간
    @Nullable
    private LocalDateTime transactionTime;
    // api 응답코드
    @Nullable
    private String resultCode;
//    // api 설명
//    @Nullable
//    private String description;

    private T data;

    @Nullable
    private Pagination pagination;

    // Ok
    public static <T> Header<T> OK(){
        return (Header<T>)Header.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("OK")
//                .description("OK")
                .build();
    }

    // Data
    public static <T> Header<T> OK(T data){
        return (Header<T>)Header.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("OK")
//                .description("OK")
                .data(data)
                .build();
    }

    public static <T> Header<T> OK(T data, Pagination pagination){
        pagination.totlaPagePlus();
        return (Header<T>)Header.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("OK")
//                .description("OK")
                .data(data)
                .pagination(pagination)
                .build();
    }

    public static <T> Header<T> ERROR(T data){
        return (Header<T>)Header.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("NO")
//                .description("OK")
                .data(data)
                .build();
    }

    // error
    public static <T> Header<T> ERROR(String description){
        return (Header<T>)Header.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("NO")
//                .description(description)
                .data(description)
                .build();
    }
}
