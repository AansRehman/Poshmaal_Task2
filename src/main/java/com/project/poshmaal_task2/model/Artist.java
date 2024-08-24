package com.project.poshmaal_task2.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Artist {

    @Id
    private Long Id;
    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name must be less than 50 characters")
    private String firstName;
    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name must be less than 50 characters")
    private String lastName;
    @NotBlank(message = "Country of birth is required")
    @Size(max = 50, message = "Country of birth must be less than 50 characters")
    private String countryOfBirth;

    private boolean active;
}
