package com.hellonature.hellonature_back.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SendMailDTO {
    private String address;
    private String title;
    private String message;
    private String Certification;
}
