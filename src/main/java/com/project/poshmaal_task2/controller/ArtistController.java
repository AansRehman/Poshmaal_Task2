package com.project.poshmaal_task2.controller;

import com.project.poshmaal_task2.model.Artist;
import com.project.poshmaal_task2.model.Artwork;
import com.project.poshmaal_task2.model.Employee;
import com.project.poshmaal_task2.repository.ArtistRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/artist")
public class ArtistController {

    @Autowired
    ArtistRepository artistRepository;

    @GetMapping("/listAllArtist")
    public ResponseEntity<List<Artist>> findAllArtist() {
        try {
            List<Artist> artists = artistRepository.findAllArtist();
            if (artists.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(artists, HttpStatus.FOUND);
            }
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/findArtistById/{id}")
    public ResponseEntity<Artist> findById(@PathVariable("id") Long id){
        try {
            Artist artist = artistRepository.findById(id);
            if (artist != null) {
                return new ResponseEntity<>(artist, HttpStatus.FOUND);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/addArtist")
    public ResponseEntity<String> addArtist(@Valid @RequestBody Artist artist){
        try {
            int res = artistRepository.addArtist(artist);
            if (res == 1) {
                return new ResponseEntity<>("Artist added successfully", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Failed to add artist", HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateArtist/{id}")
    public ResponseEntity<Artist> updateArtist(@PathVariable("id") Long id, @Valid @RequestBody Artist artist){
        try {
            Artist artist1 = artistRepository.updateArtist(id, artist);
            if (artist1 != null) {
                return new ResponseEntity<>(artist1, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
            }
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteArtist/{id}")
    public ResponseEntity<String> deleteArtist(@PathVariable("id") Long id){
        try {
            int res = artistRepository.deleteArtistWithArts(id);
            if (res == 1) {
                return new ResponseEntity<>("Artist and their artworks deleted successfully", HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>("Failed to delete artist", HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            return new ResponseEntity<>(e.toString(), HttpStatus.NOT_FOUND);
        }
    }


    // Handle validation errors globally for this controller
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        StringBuilder errors = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String errorMessage = error.getDefaultMessage();
            errors.append(errorMessage).append("; ");
        });
        return new ResponseEntity<>(errors.toString(), HttpStatus.BAD_REQUEST);
    }



}
