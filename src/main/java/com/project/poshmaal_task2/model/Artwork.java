package com.project.poshmaal_task2.model;

import jakarta.validation.constraints.NotNull;
import lombok.*;

//import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.*;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Artwork {

    @Id
    private long id;

    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title must be less than 100 characters")
    private String title;

    @Min(value = 0, message = "Year of completion cannot be negative")
    @Max(value = 2100, message = "Year of completion cannot be greater than 2100")
    private int yearOfCompletion;

    @DecimalMin(value = "0.00", inclusive = false, message = "Price must be greater than 0")
    private double price;

    private boolean sold;

    @NotNull(message = "Artist ID is required")
    private Long artist_id;
}
