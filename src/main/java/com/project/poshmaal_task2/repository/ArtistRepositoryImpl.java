package com.project.poshmaal_task2.repository;

import com.project.poshmaal_task2.model.Art;
import com.project.poshmaal_task2.model.Artist;

import java.util.List;

public class ArtistRepositoryImpl implements ArtistRepository{
    @Override
    public List<Artist> findAllArtist() {
//        TODO

        return List.of();
    }

    @Override
    public Artist addArtist(Artist artist) {
//        TODO
        return null;
    }

    @Override
    public String deleteArtistWithArts(long artist_id) {
//        TODO
        return "";
    }
}
