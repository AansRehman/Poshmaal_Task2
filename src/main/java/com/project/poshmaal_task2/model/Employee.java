package com.project.poshmaal_task2.model;

import lombok.*;
import org.springframework.data.annotation.Id;


import java.util.Collection;
import java.util.Collections;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    @Id
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private boolean locked;
    private String role;

}
