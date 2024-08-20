package com.project.poshmaal_task2.model;

import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Art {
    @Id
    private long id;
    private String title;
    private String yearOfCompletion;
    private double price;
    private boolean sold;

    private long artist_id;
}
