package com.project.poshmaal_task2.controller;

import com.project.poshmaal_task2.repository.ArtistRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/artist")
public class ArtistController {

    ArtistRepository artistRepository;
}
