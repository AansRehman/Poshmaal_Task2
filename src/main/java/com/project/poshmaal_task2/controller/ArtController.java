package com.project.poshmaal_task2.controller;

import com.project.poshmaal_task2.repository.ArtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/art")
public class ArtController {

    ArtRepository artRepository;
}
