package com.project.poshmaal_task2.repository;

import com.project.poshmaal_task2.model.Art;
import com.project.poshmaal_task2.model.Artist;

import java.util.List;

public interface ArtistRepository {
    List<Artist> findAllArtist();

    Artist addArtist(Artist artist);

    String deleteArtistWithArts(long artist_id);
}
