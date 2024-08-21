package com.project.poshmaal_task2.model;

import lombok.*;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Artwork {
    @Id
    private long id;
    private String title;
    private int yearOfCompletion;
    private double price;
    private boolean sold;

    private Long artist_id;
}
