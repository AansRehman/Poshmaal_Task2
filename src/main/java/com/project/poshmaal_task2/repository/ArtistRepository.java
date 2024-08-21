package com.project.poshmaal_task2.repository;

import com.project.poshmaal_task2.model.Artist;

import java.util.List;

public interface ArtistRepository {
    List<Artist> findAllArtist();

    Artist findById(Long id);

    int addArtist(Artist artist);

    Artist updateArtist(Long id, Artist artist);

    int deleteArtistWithArts(Long artist_id);
}
