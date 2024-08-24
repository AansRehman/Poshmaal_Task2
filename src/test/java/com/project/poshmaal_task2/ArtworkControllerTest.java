package com.project.poshmaal_task2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.poshmaal_task2.controller.ArtworkController;
import com.project.poshmaal_task2.model.Artwork;
import com.project.poshmaal_task2.repository.ArtworkRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(controllers = ArtworkController.class)
public class ArtworkControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArtworkRepository artworkRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testAddArtwork_Conflict() throws Exception {

        Artwork artwork = new Artwork();
        artwork.setTitle("Artwork Title");

        when(artworkRepository.addArt(any(Artwork.class))).thenReturn(0);

        mockMvc.perform(post("/artwork/addArtwork")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(artwork)))
                .andExpect(status().isNotFound()) 
                .andExpect(content().string("Failed to add artwork"));
    }

    @Test
    public void testAddArtwork_InvalidData() throws Exception {

        Artwork artwork = new Artwork();

        mockMvc.perform(post("/artwork/addArtwork")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(artwork)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAddArtwork_NoData() throws Exception {
        mockMvc.perform(post("/artwork/addArtwork")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}

