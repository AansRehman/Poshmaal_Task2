package com.project.poshmaal_task2.controller;

import com.project.poshmaal_task2.model.Artwork;
import com.project.poshmaal_task2.repository.ArtworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/artwork")
public class ArtworkController {
    @Autowired
    ArtworkRepository artworkRepository;

    @GetMapping("/listAllArtworks")
    public ResponseEntity<List<Artwork>> getAllArtworks(){
        List<Artwork> artworks = artworkRepository.findAllArtWorks();
        if(artworks.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<>(artworks, HttpStatus.OK);
        }
    }

    @GetMapping("/findArtworkById/{id}")
    public ResponseEntity<Artwork> findById(@PathVariable("id") Long id){
        Artwork artwork = artworkRepository.findArtWorkById(id);
        if(artwork!=null){
            return new ResponseEntity<>(artwork, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/addArtwork")
    public ResponseEntity<String> addArtwork(@RequestBody Artwork artwork){
        int res = artworkRepository.addArt(artwork);
        if(res==1){
            return new ResponseEntity<>("Artwork added successfully", HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>("Failed to add artwork", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateArtwork/{id}")
    public ResponseEntity<Artwork> updateArtwork(@PathVariable("id") Long id, @RequestBody Artwork artwork){
        Artwork artwork1 = artworkRepository.updateArtwork(id, artwork);
        if(artwork1==artwork){
            return new ResponseEntity<>(artwork1, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }
    @DeleteMapping("/deleteArtwork/{id}")
    public ResponseEntity<String> deleteArtwork(@PathVariable("id") Long id){
        int res = artworkRepository.deleteArt(id);
        if(res==1.){
            return new ResponseEntity<>("Artwork deleted successfully", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Failed to delete artwork", HttpStatus.NOT_FOUND);
        }
    }

    public List<Artwork> artworksByArtistFunc(Long id){
        List<Artwork> artworks = artworkRepository.findAllArtWorks();
        ArrayList<Artwork> artworksByArtist = new ArrayList<>();
        System.out.println(artworks);
        for(int i=0; i<artworks.size(); i++){
            Long prev_id = artworks.get(i).getArtist_id();
            if(prev_id == id){
                artworksByArtist.add(artworks.get(i));
            }
        }
        return artworksByArtist;
    }

    @GetMapping("/getArtworkOfArtistById/{id}")
    public ResponseEntity<List<Artwork>> artworksByArtist(@PathVariable("id") Long id){
        List artworksByArtist = artworksByArtistFunc(id);
        if(artworksByArtist!=null) {
            return new ResponseEntity<>(artworksByArtist, HttpStatus.OK);
        }else {
            return new ResponseEntity<>( HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/unsoldArtwork")
    public ResponseEntity<List<Artwork>> unsoldArtworks(){
        List<Artwork> artworks = artworkRepository.findAllArtWorks();
        ArrayList<Artwork> unsoldArtworks = new ArrayList<>();

        for(int i=0; i<artworks.size(); i++){
            boolean isSold = artworks.get(i).isSold();
            if(!isSold){
                unsoldArtworks.add(artworks.get(i));
            }
        }
        if(!unsoldArtworks.isEmpty()) {
            return new ResponseEntity<>(unsoldArtworks, HttpStatus.OK);
        } else{
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/unsoldArtworkByArtist/{id}")
    public ResponseEntity<List<Artwork>> unsoldArtworkByArtist(@PathVariable Long id){
    List<Artwork> artworksByArtist = artworksByArtistFunc(id);
    ArrayList<Artwork> unsoldArtworksByArtist = new ArrayList<>();
    for(int i=0; i<artworksByArtist.size(); i++){
        if(!artworksByArtist.get(i).isSold()){
            unsoldArtworksByArtist.add(artworksByArtist.get(i));
        }
    }
    if(!unsoldArtworksByArtist.isEmpty()){
        return new ResponseEntity<>(unsoldArtworksByArtist, HttpStatus.OK);
    }else {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    }



}
