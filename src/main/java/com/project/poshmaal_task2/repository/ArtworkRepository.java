package com.project.poshmaal_task2.repository;

import com.project.poshmaal_task2.model.Artwork;

import java.util.List;

public interface ArtworkRepository {


    List<Artwork> findAllArtWorks();

    Artwork findArtWorkById(Long id);

    int addArt(Artwork art);

    int deleteArt(Long id);

    Artwork updateArtwork(Long id, Artwork artwork);

//    List<Artwork> listArtWorksofArtist(Long artist_id);
//
//    List<Artwork> findUnsoldArtWorks();
//
//    List<Artwork> listUnsoldArtWorksofArtist(Long artist_id);
//
//    List<Artwork> listMostExpensiveSoldArtWorksofArtist(Long artist_id);
//
//    double avgPriceOfUnsoldArtWorks();
//
//    List<Artwork> listArtWorksfromCountry(String country);
//
//    Artwork changePrice(Long id, double price);
//
//    Artwork changeStatus(Long id, boolean status);


}
