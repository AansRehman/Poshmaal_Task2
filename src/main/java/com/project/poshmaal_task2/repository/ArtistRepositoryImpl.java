package com.project.poshmaal_task2.repository;

import com.project.poshmaal_task2.model.Artist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ArtistRepositoryImpl implements ArtistRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final String FIND_ALL_ARTISTS_SQL = "SELECT * FROM Artist";
    private final String FIND_ARTIST_BY_ID_SQL = "SELECT * FROM Artist WHERE id = ?";
    private final String ADD_ARTIST_SQL = "INSERT INTO Artist (firstname, lastname, country_of_birth, active) VALUES (?, ?, ?, ?)";
    private final String UPDATE_ARTIST_SQL = "UPDATE Artist SET firstname = ?, lastname = ?, country_of_birth = ?, active = ? WHERE id = ?";
    private final String DELETE_ARTIST_WITH_ARTS_SQL = "DELETE FROM Artist WHERE id = ?";


    @Override
    public List<Artist> findAllArtist() {
        return jdbcTemplate.query(FIND_ALL_ARTISTS_SQL, new ArtistRowMapper());

    }

    @Override
    public Artist findById(Long id) {
        return jdbcTemplate.queryForObject(FIND_ARTIST_BY_ID_SQL, new ArtistRowMapper(), id);
    }

    @Override
    public int addArtist(Artist artist) {
        int result = jdbcTemplate.update(ADD_ARTIST_SQL, artist.getFirstName(), artist.getLastName(), artist.getCountryOfBirth(), artist.isActive());
        return result;
//                ? "Artist added successfully" : "Failed to add artist";
    }

    @Override
    public Artist updateArtist(Long id, Artist artist) {
        int result = jdbcTemplate.update(UPDATE_ARTIST_SQL, artist.getFirstName(), artist.getLastName(), artist.getCountryOfBirth(), artist.isActive(), id);
        return result == 1 ? artist : null;
    }

    @Override
    public int deleteArtistWithArts(Long artist_id) {
        int result = jdbcTemplate.update(DELETE_ARTIST_WITH_ARTS_SQL, artist_id);
        return result;
//                == 1 ? "Artist and their artworks deleted successfully" : "Failed to delete artist";
    }
    private static class ArtistRowMapper implements RowMapper<Artist> {
        @Override
        public Artist mapRow(ResultSet rs, int rowNum) throws SQLException {
            Artist artist = new Artist();
            artist.setId(rs.getLong("id"));
            artist.setFirstName(rs.getString("firstname"));
            artist.setLastName(rs.getString("lastname"));
            artist.setCountryOfBirth(rs.getString("country_of_birth"));
            artist.setActive(rs.getBoolean("active"));
            return artist;
        }
    }
}

