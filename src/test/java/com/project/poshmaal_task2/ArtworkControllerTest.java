package com.project.poshmaal_task2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.poshmaal_task2.config.SecurityConfig;
import com.project.poshmaal_task2.controller.ArtworkController;
import com.project.poshmaal_task2.model.Artwork;
import com.project.poshmaal_task2.repository.ArtworkRepository;
import com.project.poshmaal_task2.repository.ArtistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@Import(SecurityConfig.class) // for authentication testcases

@WebMvcTest(controllers = ArtworkController.class)
public class ArtworkControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArtworkRepository artworkRepository;

    @MockBean
    private ArtistRepository artistRepository;

    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @WithMockUser(username = "buyer@gmail.com", password="Buyer@10", roles = {"BUYER"})
    public void testAddArtwork_Conflict() throws Exception {

        Artwork artwork = new Artwork();
        artwork.setTitle("Artwork Title");
        artwork.setArtist_id(1L);
        artwork.setPrice(1.00);

        when(artworkRepository.addArt(any(Artwork.class))).thenReturn(0);

        mockMvc.perform(post("/artwork/addArtwork")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(artwork)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Failed to add artwork"));
    }

    @Test
    @WithMockUser(username = "buyer@gmail.com", password="Buyer@10", roles = {"BUYER"})
    public void testAddArtwork_InvalidData() throws Exception {

        Artwork artwork = new Artwork();

        mockMvc.perform(post("/artwork/addArtwork")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(artwork)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "buyer@gmail.com", password="Buyer@10", roles = {"BUYER"})
    public void testAddArtwork_NoData() throws Exception {
        mockMvc.perform(post("/artwork/addArtwork")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


     @Test
     @WithMockUser(username = "buyer@gmail.com", password="Buyer@10", roles = {"BUYER"})
     public void testAddArtwork_AuthenticatedAndAuthorized() throws Exception {

     Artwork artwork = new Artwork();
     artwork.setTitle("ArtworkTitle");
     artwork.setPrice(1.00);
     artwork.setArtist_id(1L);

     when(artworkRepository.addArt(any(Artwork.class))).thenReturn(1);

     mockMvc.perform(post("/artwork/addArtwork")
     .contentType(MediaType.APPLICATION_JSON)
     .content(objectMapper.writeValueAsString(artwork)))
     .andExpect(status().isCreated())
     .andExpect(content().string("Artwork added successfully"));
     }

     @Test
     public void testAddArtwork_NotAuthenticated() throws Exception {

     Artwork artwork = new Artwork();
     artwork.setTitle("Artwork Title");

     mockMvc.perform(post("/artwork/addArtwork")
     .contentType(MediaType.APPLICATION_JSON)
     .content(objectMapper.writeValueAsString(artwork)))
     .andExpect(status().isUnauthorized());
     }

     @Test
     @WithMockUser(username = "staff@gmail.com", password="Staff@10", roles = {"STAFF"})
     public void testAddArtwork_NotAuthorized() throws Exception {

     Artwork artwork = new Artwork();
     artwork.setTitle("Artwork Title");

     when(artworkRepository.addArt(any(Artwork.class))).thenReturn(0);

     mockMvc.perform(post("/artwork/addArtwork")
     .contentType(MediaType.APPLICATION_JSON)
     .content(objectMapper.writeValueAsString(artwork)))
     .andExpect(status().isForbidden());
     }


}

