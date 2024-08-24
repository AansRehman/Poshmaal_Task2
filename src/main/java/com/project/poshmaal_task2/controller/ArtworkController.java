package com.project.poshmaal_task2.controller;

import com.project.poshmaal_task2.model.Artist;
import com.project.poshmaal_task2.model.Artwork;
import com.project.poshmaal_task2.model.Employee;
import com.project.poshmaal_task2.repository.ArtistRepository;
import com.project.poshmaal_task2.repository.ArtworkRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/artwork")
public class ArtworkController {
    @Autowired
    ArtworkRepository artworkRepository;

    @Autowired
    ArtistRepository artistRepository;

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
    public ResponseEntity<String> addArtwork(@Valid @RequestBody Artwork artwork){
        int res = artworkRepository.addArt(artwork);
        if(res==1){
            return new ResponseEntity<>("Artwork added successfully", HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>("Failed to add artwork", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateArtwork/{id}")
    public ResponseEntity<Artwork> updateArtwork(@PathVariable("id") Long id, @Valid @RequestBody Artwork artwork){
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

    @GetMapping("/listMostExpensiveSoldArtWorksofArtist/{id}")
    public ResponseEntity<List<Artwork>> listMostExpensiveSoldArtWorksofArtist(@PathVariable("id") Long id){
        List<Artwork> artworksByArtist = artworksByArtistFunc(id);
        List<Artwork> soldArtworks = new ArrayList<>();

        for (Artwork artwork : artworksByArtist) {
            if (artwork.isSold()) {
                soldArtworks.add(artwork);
            }
        }

        // Sorting sold artworks by price in descending order
        soldArtworks.sort((a1, a2) -> Double.compare(a2.getPrice(), a1.getPrice()));

        if (!soldArtworks.isEmpty()) {
            return new ResponseEntity<>(soldArtworks, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/avgPriceOfUnsoldArtWorks")
    public ResponseEntity<Double> avgPriceOfUnsoldArtWorks(){
        List<Artwork> artworks = artworkRepository.findAllArtWorks();
        double totalPrice = 0;
        int count = 0;

        for (Artwork artwork : artworks) {
            if (!artwork.isSold()) {
                totalPrice += artwork.getPrice();
                count++;
            }
        }

        if (count > 0) {
            double avgPrice = totalPrice / count;
            return new ResponseEntity<>(avgPrice, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/listArtWorksfromCountry/{country}")
    public ResponseEntity<List<Artwork>> listArtWorksfromCountry(@PathVariable("country") String country){
        List<Artwork> artworks = artworkRepository.findAllArtWorks();
        List<Artwork> artworksFromCountry = new ArrayList<>();

        for (Artwork artwork : artworks) {
            Artist artist = artistRepository.findById(artwork.getArtist_id());
            if (artist != null && country.equalsIgnoreCase(artist.getCountryOfBirth())) {
                artworksFromCountry.add(artwork);
            }
        }

        if (!artworksFromCountry.isEmpty()) {
            return new ResponseEntity<>(artworksFromCountry, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("/changePrice/{id}")
    public ResponseEntity<Artwork> changePrice(@PathVariable("id") Long id, @Valid @RequestBody double price){
        Artwork artwork = artworkRepository.findArtWorkById(id);

        if (artwork != null) {
            artwork.setPrice(price);
            artworkRepository.updateArtwork(id, artwork);
            return new ResponseEntity<>(artwork, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/changeStatus/{id}")
    public ResponseEntity<Artwork> changeStatus(@PathVariable("id") Long id, @Valid @RequestBody boolean status){
        Artwork artwork = artworkRepository.findArtWorkById(id);

        if (artwork != null) {
            artwork.setSold(status);
            artworkRepository.updateArtwork(id, artwork);
            return new ResponseEntity<>(artwork, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @MutationMapping(name = "addArtwork")
    public Artwork addArtworkMutation(@Argument Artwork artwork){
        int res = artworkRepository.addArt(artwork);
        if(res > 0){
            return artwork;
        }else{
            return null;
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
