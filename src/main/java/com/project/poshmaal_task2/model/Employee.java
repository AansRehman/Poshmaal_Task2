package com.project.poshmaal_task2.model;

import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Employee {

    @Id
    private long id;
    private String email;
    private String fName;
    private String lName;
    private String password;
    private boolean locked;
    private String role;
}
