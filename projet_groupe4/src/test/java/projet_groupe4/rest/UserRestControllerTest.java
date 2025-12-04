package projet_groupe4.rest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import projet_groupe4.dto.request.SubscribeClientRequest;
import projet_groupe4.dto.request.SubscribeEmployeRequest;
import projet_groupe4.model.Client;
import projet_groupe4.model.Employe;
import projet_groupe4.service.PersonneService;

@WebMvcTest(UserRestController.class)
@AutoConfigureMockMvc(addFilters = false) 
public class UserRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonneService srv;

    private final ObjectMapper mapper = new ObjectMapper();

    // --- POST /api/inscription/client ---

    @Test

    void shouldSubscribeClientSuccessfully() throws Exception {
        SubscribeClientRequest req = new SubscribeClientRequest();
        req.setNom("Dupont");
        req.setPrenom("Jean");
        req.setMail("jean.dupont@mail.com");
        req.setMdp("password");
        req.setTelephone("0123456789");
        req.setVille("Paris");
        req.setCodePostale("75001");
        req.setAdresse("10 rue de la Paix");

        Client saved = new Client();
        saved.setId(1);

        Mockito.when(srv.getByMail(req.getMail())).thenReturn(Optional.empty());
        Mockito.when(srv.create(Mockito.any(SubscribeClientRequest.class))).thenReturn(saved);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/inscription/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(1));
    }

    @Test

    void shouldFailSubscribeClientEmailAlreadyUsed() throws Exception {
        SubscribeClientRequest req = new SubscribeClientRequest();
        req.setMail("existing@mail.com");

        Mockito.when(srv.getByMail(req.getMail())).thenReturn(Optional.of(new Client()));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/inscription/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
               .andExpect(status().isBadRequest());
    }

    @Test

    void shouldFailSubscribeClientValidation() throws Exception {
        SubscribeClientRequest req = new SubscribeClientRequest(); // Nom et mail manquants

        mockMvc.perform(MockMvcRequestBuilders.post("/api/inscription/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
               .andExpect(status().isBadRequest());
    }

    // --- POST /api/inscription/employe ---

    @Test

    void shouldSubscribeEmployeSuccessfully() throws Exception {
        SubscribeEmployeRequest req = new SubscribeEmployeRequest();
        req.setNom("Martin");
        req.setPrenom("Alice");
        req.setMail("alice.martin@mail.com");
        req.setMdp("password");
        req.setJob("Serveur");
        req.setTelephone("0123456789");

        Employe saved = new Employe();
        saved.setId(2);

        Mockito.when(srv.create(Mockito.any(SubscribeEmployeRequest.class))).thenReturn(saved);


        mockMvc.perform(MockMvcRequestBuilders.post("/api/inscription/employe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(2));
    }

    @Test

    void shouldFailSubscribeEmployeEmailAlreadyUsed() throws Exception {
        SubscribeEmployeRequest req = new SubscribeEmployeRequest();
        req.setMail("existing@mail.com");

        Mockito.when(srv.getByMail(req.getMail())).thenReturn(Optional.of(new Employe()));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/inscription/employe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
               .andExpect(status().isBadRequest());
    }

    @Test

    void shouldFailSubscribeEmployeValidation() throws Exception {
        SubscribeEmployeRequest req = new SubscribeEmployeRequest(); // Nom et mail manquants

        mockMvc.perform(MockMvcRequestBuilders.post("/api/inscription/employe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
               .andExpect(status().isBadRequest());
    }


}
