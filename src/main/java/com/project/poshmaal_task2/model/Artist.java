package com.project.poshmaal_task2.model;

import lombok.*;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Artist {

    @Id
    private Long Id;
    private String firstName;
    private String lastName;
    private String countryOfBirth;
    private boolean active;
}
