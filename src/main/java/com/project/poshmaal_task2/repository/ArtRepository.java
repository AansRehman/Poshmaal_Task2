package com.project.poshmaal_task2.repository;

import com.project.poshmaal_task2.model.Art;

import java.util.List;

public interface ArtRepository {
    List<Art> listArtWorksofArtist(long artist_id);
    List<Art> listUnsoldArtWorksofArtist(long artist_id);
    List<Art> listMostExpensiveSoldArtWorksofArtist(long artist_id);
    double avgPriceOfUnsoldArtWorks();
    List<Art> listArtWorksfromCountry(String country);


    Art addArt(Art art);

    String deleteArt(long id);

    Art changePrice(long id, double price);
    Art changeStatus(long id, boolean status);
}
