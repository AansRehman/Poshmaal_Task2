package com.project.poshmaal_task2.model;

import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Artist {

    @Id
    private long Id;
    private String firstName;
    private String lastLastName;
    private String countryOfBirth;
    private boolean active;
}
