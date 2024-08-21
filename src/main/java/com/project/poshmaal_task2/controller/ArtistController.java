package com.project.poshmaal_task2.controller;

import com.project.poshmaal_task2.model.Artist;
import com.project.poshmaal_task2.model.Artwork;
import com.project.poshmaal_task2.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/artist")
public class ArtistController {

    @Autowired
    ArtistRepository artistRepository;

    @GetMapping("/listAllArtist")
    public ResponseEntity<List<Artist>> findAllArtist(){
        List<Artist> artists = artistRepository.findAllArtist();
        if(artists.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            return new ResponseEntity<>(artists, HttpStatus.OK);
        }
    }

    @GetMapping("/findArtistById/{id}")
    public ResponseEntity<Artist> findById(@PathVariable("id") Long id){
        Artist artist = artistRepository.findById(id);
        if(artist!=null){
            return new ResponseEntity<>(artist, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/addArtist")
    public ResponseEntity<String> addArtist(@RequestBody Artist artist){
        int res = artistRepository.addArtist(artist);
        if (res==1){
            return new ResponseEntity<>("Artist added successfully", HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>("Failed to add artist", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateArtist/{id}")
    public ResponseEntity<Artist> updateArtist(@PathVariable("id") Long id, @RequestBody Artist artist){
        Artist artist1 = artistRepository.updateArtist(id, artist);
        if(artist1!=null){
            return new ResponseEntity<>(artist1, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @DeleteMapping("/deleteArtist/{id}")
    public ResponseEntity<String> deleteArtist(@PathVariable("id") Long id){
        int res = artistRepository.deleteArtistWithArts(id);
        if (res==1){
            return new ResponseEntity<>("Artist and their artworks deleted successfully", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Failed to delete artist", HttpStatus.NOT_FOUND);
        }
    }
}
