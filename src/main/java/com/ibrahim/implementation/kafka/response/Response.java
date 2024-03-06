package com.ibrahim.implementation.kafka.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Response implements Serializable {

    private boolean status;
    private String message;
    private Object data;
}
