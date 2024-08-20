package com.project.poshmaal_task2.repository;

import com.project.poshmaal_task2.model.Art;

import java.util.List;

public class ArtRepositoryImpl implements ArtRepository {

    @Override
    public List<Art> listArtWorksofArtist(long artist_id) {
//        TODO

        return List.of();
    }

    @Override
    public List<Art> listUnsoldArtWorksofArtist(long artist_id) {
//        TODO
        return List.of();
    }

    @Override
    public List<Art> listMostExpensiveSoldArtWorksofArtist(long artist_id) {
//        TODO
        return List.of();
    }

    @Override
    public double avgPriceOfUnsoldArtWorks() {
//        TODO
        return 0;
    }

    @Override
    public List<Art> listArtWorksfromCountry(String country) {
//        TODO
        return List.of();
    }

    @Override
    public Art addArt(Art art) {
//        TODO
        return null;
    }

    @Override
    public String deleteArt(long id) {
//        TODO
        return "";
    }

    @Override
    public Art changePrice(long id, double price) {
//        TODO
        return null;
    }

    @Override
    public Art changeStatus(long id, boolean status) {
//        TODO
        return null;
    }


}
