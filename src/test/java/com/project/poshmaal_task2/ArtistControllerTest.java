package com.project.poshmaal_task2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.poshmaal_task2.config.SecurityConfig;
import com.project.poshmaal_task2.controller.ArtistController;
import com.project.poshmaal_task2.model.Artist;
import com.project.poshmaal_task2.repository.ArtistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;
@Import(SecurityConfig.class) // for authentication testcases

@WebMvcTest(ArtistController.class)
public class ArtistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArtistRepository artistRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @WithMockUser(username = "staff@gmail.com", password="Staff@10", roles = {"STAFF"})
    public void testFindById_ArtistExists() throws Exception {
        // Arrange
        Long artistId = 1L;
        Artist mockArtist = new Artist();
        mockArtist.setId(artistId);
        mockArtist.setFirstName("John");
        mockArtist.setLastName("Doe");
        mockArtist.setCountryOfBirth("USA");
        mockArtist.setActive(true);

        when(artistRepository.findById(artistId)).thenReturn(mockArtist);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/artist/findArtistById/{id}", artistId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(artistId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.countryOfBirth").value("USA"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.active").value(true));
    }

    @Test
    @WithMockUser(username = "staff@gmail.com", password="Staff@10", roles = {"STAFF"})
    public void testFindById_ArtistDoesNotExist() throws Exception {
        // Arrange
        Long artistId = 2L;

        when(artistRepository.findById(artistId)).thenReturn(null);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/artist/findArtistById/{id}", artistId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @WithMockUser(username = "buyer@gmail.com", password="Buyer@10", roles = {"BUYER"})
    public void testDeleteArtist_ArtistExists() throws Exception {
        // Arrange
        Long artistId = 1L;
        when(artistRepository.deleteArtistWithArts(artistId)).thenReturn(1);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/artist/deleteArtist/{id}", artistId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andExpect(MockMvcResultMatchers.content().string("Artist and their artworks deleted successfully"));
    }

    @Test
    @WithMockUser(username = "buyer@gmail.com", password="Buyer@10", roles = {"BUYER"})
    public void testDeleteArtist_ArtistDoesNotExist() throws Exception {
        // Arrange
        Long artistId = 2L;
        when(artistRepository.deleteArtistWithArts(artistId)).thenReturn(0);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/artist/deleteArtist/{id}", artistId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Failed to delete artist"));
    }

    @Test
    public void testDeleteArtist_ArtistExistsButNotAuthenticated() throws Exception {
        // Arrange
        Long artistId = 1L;

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/artist/deleteArtist/{id}", artistId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized()); // Expect Unauthorized status (401)
    }

    @Test
    @WithMockUser(username = "staff@gmail.com", password="Staff@10", roles = {"STAFF"})
    public void testDeleteArtist_ArtistExistsButNotAuthorized() throws Exception {
        // Arrange
        Long artistId = 1L;

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/artist/deleteArtist/{id}", artistId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isForbidden()); // Expect Forbidden status (403)
    }
    }
