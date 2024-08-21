package com.project.poshmaal_task2.repository;

import com.project.poshmaal_task2.model.Artwork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ArtworkRepositoryImpl implements ArtworkRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final String FIND_ALL_ARTWORKS_SQL = "SELECT * FROM Artwork";
    private final String FIND_ARTWORK_BY_ID_SQL = "SELECT * FROM Artwork WHERE id = ?";
    private final String ADD_ARTWORK_SQL = "INSERT INTO Artwork (title, year_of_completion, price, sold, artist_id) VALUES (?, ?, ?, ?, ?)";
    private final String UPDATE_ARTWORK_SQL = "UPDATE Artwork SET title = ?, year_of_completion = ?, price = ?, sold = ?, artist_id = ? WHERE id = ?";
    private final String DELETE_ARTWORK_SQL = "DELETE FROM Artwork WHERE id = ?";


    @Override
    public List<Artwork> findAllArtWorks() {
        return jdbcTemplate.query(FIND_ALL_ARTWORKS_SQL, new ArtworkRowMapper());
    }
    @Override
    public Artwork findArtWorkById(Long id) {
        return jdbcTemplate.queryForObject(FIND_ARTWORK_BY_ID_SQL, new ArtworkRowMapper(), id);
    }

    @Override
    public Artwork updateArtwork(Long id, Artwork artwork) {
        int result = jdbcTemplate.update(UPDATE_ARTWORK_SQL, artwork.getTitle(), artwork.getYearOfCompletion(), artwork.getPrice(), artwork.isSold(), artwork.getId(), id);
        return result == 1 ? artwork : null;
    }

    @Override
    public int addArt(Artwork art) {
        int result = jdbcTemplate.update(ADD_ARTWORK_SQL, art.getTitle(), art.getYearOfCompletion(), art.getPrice(), art.isSold(), art.getArtist_id());
        return result;
    }

    @Override
    public int deleteArt(Long id) {
        int result = jdbcTemplate.update(DELETE_ARTWORK_SQL, id);
        return result;
    }

    private static class ArtworkRowMapper implements RowMapper<Artwork> {
        @Override
        public Artwork mapRow(ResultSet rs, int rowNum) throws SQLException {
            Artwork artwork = new Artwork();
            artwork.setId(rs.getLong("id"));
            artwork.setTitle(rs.getString("title"));
            artwork.setYearOfCompletion(rs.getInt("year_of_completion"));
            artwork.setPrice(rs.getDouble("price"));
            artwork.setSold(rs.getBoolean("sold"));
            artwork.setArtist_id(rs.getLong("artist_id"));
            return artwork;
        }
    }

}
