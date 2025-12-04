package projet_groupe4.rest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import projet_groupe4.dto.response.TopJeuResponse;
import projet_groupe4.model.Jeu;
import projet_groupe4.service.EmpruntService;
import projet_groupe4.service.JeuService;
import projet_groupe4.service.PersonneService;
import projet_groupe4.service.ReservationService;


@WebMvcTest(TopController.class)
public class TopRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private PersonneService personneService;

    @MockBean
    private EmpruntService empruntService;

    @MockBean
    private ReservationService reservationService;

    @MockBean
    private JeuService jeuService;
    

    @Test
    @WithMockUser
    void shouldReturnTopEmprunts() throws Exception {
    	 Jeu jeu1 = new Jeu(); jeu1.setNom("Catan");
         Jeu jeu2 = new Jeu(); jeu2.setNom("Catan 2");
         Jeu jeu3 = new Jeu(); jeu3.setNom("Catan 3");
        List<TopJeuResponse> topList = List.of(
                new TopJeuResponse(jeu1, 10),
                new TopJeuResponse(jeu2, 8),
                new TopJeuResponse(jeu3, 5)
        );

        Mockito.when(empruntService.getTop3Emprunts()).thenReturn(topList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/top/emprunts")
                        .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].jeu.nom").value("Catan"))
               .andExpect(jsonPath("$[0].count").value(10))
               .andExpect(jsonPath("$[1].jeu.nom").value("Catan 2"))
               .andExpect(jsonPath("$[2].jeu.nom").value("Catan 3"));
    }

    @Test
    @WithMockUser
    void shouldReturnTopReservations() throws Exception {
        Jeu jeu1 = new Jeu(); jeu1.setNom("Carcassonne");
        Jeu jeu2 = new Jeu(); jeu2.setNom("Ticket to Ride");

        List<TopJeuResponse> topList = List.of(
                new TopJeuResponse(jeu1, 12),
                new TopJeuResponse(jeu2, 9)
        );

        Mockito.when(reservationService.getTop3Reservations()).thenReturn(topList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/top/reservations")
                        .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].jeu.nom").value("Carcassonne"))
               .andExpect(jsonPath("$[0].count").value(12))
               .andExpect(jsonPath("$[1].jeu.nom").value("Ticket to Ride"))
               .andExpect(jsonPath("$[1].count").value(9));
    }

    @Test
    @WithMockUser
    void shouldReturnTopNotes() throws Exception {
        Jeu jeu1 = new Jeu(); jeu1.setNom("Terraforming Mars");
        Jeu jeu2 = new Jeu(); jeu2.setNom("Azul");

        List<TopJeuResponse> topList = List.of(
                new TopJeuResponse(jeu1, 5),
                new TopJeuResponse(jeu2, 4)
        );

        Mockito.when(jeuService.getTop3Notes()).thenReturn(topList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/top/notes")
                        .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].jeu.nom").value("Terraforming Mars"))
               .andExpect(jsonPath("$[0].count").value(5))
               .andExpect(jsonPath("$[1].jeu.nom").value("Azul"))
               .andExpect(jsonPath("$[1].count").value(4));
    }
}
