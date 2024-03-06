package com.ibrahim.implementation.kafka.model;

import com.ibrahim.implementation.kafka.entites.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO extends BaseEntity implements Serializable {
    private String username;
    private String firstname;
    private String lastname;
    private String email;


}
