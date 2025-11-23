package projet_groupe4.rest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import projet_groupe4.dao.IDAOPersonne;
import projet_groupe4.dto.request.EmpruntRequest;
import projet_groupe4.model.Emprunt;
import projet_groupe4.model.StatutLocation;
import projet_groupe4.service.EmpruntService;

@WebMvcTest(EmpruntRestController.class)
public class EmpruntRestControllerTest {
    private static final int EMPRUNT_ID = 1;
    private static final LocalDate EMPRUNT_EMPRUNT = LocalDate.now();
    private static final LocalDate EMPRUNT_RETOUR = LocalDate.now();
    private static final String EMPRUNT_STATUT = "enCours";
    private static final LocalDate EMPRUNT_RETOUR_REEL = LocalDate.now();
    private static final String API_URL = "/api/emprunt";
    private static final String API_URL_BY_ID = API_URL + "/" + EMPRUNT_ID;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmpruntService srv;

    @MockitoBean
    private IDAOPersonne dao;

    @Test
    void shouldGetAllStatusUnauthorized() throws Exception {
        // given

        // when
        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL));

        // then
        result.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void shouldGetAllStatusOk() throws Exception {
        // given

        // when
        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL));

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser
    void shouldGetAllUseServiceGetAll() throws Exception {
        // given

        // when
        this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL));

        // then
        Mockito.verify(this.srv).getAll();
    }

    @Test
    @WithMockUser
    void shouldGetAllReturnAttributes() throws Exception {

        // --- GIVEN ---
        Emprunt e1 = new Emprunt();

        e1.setId(EMPRUNT_ID);
        e1.setDateEmprunt(EMPRUNT_EMPRUNT);
        e1.setDateRetour(EMPRUNT_RETOUR);
        e1.setStatutLocation(StatutLocation.valueOf(EMPRUNT_STATUT));
        e1.setDateRetourReel(EMPRUNT_RETOUR_REEL);

        // On retourne la liste de CLIENT
        Mockito.when(srv.getAll()).thenReturn(List.of(e1));

        // --- WHEN ---
        ResultActions result = this.mockMvc.perform(
                MockMvcRequestBuilders.get(API_URL));

        // --- THEN ---

        result.andExpect(MockMvcResultMatchers.jsonPath("$[0].id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].dateEmprunt").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].dateRetour").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].statutLocation").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].dateRetourReel").exists());

    }

    @Test
    void shouldGetByIdStatusUnauthorized() throws Exception {
        // given

        // when
        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL_BY_ID));

        // then
        result.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void shouldGetByIdStatusOk() throws Exception {
        // given
        Mockito.when(this.srv.getById(EMPRUNT_ID)).thenReturn(Optional.of(new Emprunt()));
        // when
        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL_BY_ID));

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser
    void shouldGetByIdUseServiceGetById() throws Exception {
        // given
        Mockito.when(this.srv.getById(EMPRUNT_ID)).thenReturn(Optional.of(new Emprunt()));

        // when
        this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL_BY_ID));

        // then
        Mockito.verify(this.srv).getById(EMPRUNT_ID);
    }

    @Test
    @WithMockUser
    void shouldGetByIdStatusNotFoundWhenIdNotFound() throws Exception {
        // given
        // when
        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL_BY_ID));

        // then
        result.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @WithMockUser
    void shouldGetByIdReturnAttributes() throws Exception {
        // given
        Emprunt e1 = new Emprunt();

        e1.setId(EMPRUNT_ID);
        e1.setDateEmprunt(EMPRUNT_EMPRUNT);
        e1.setDateRetour(EMPRUNT_RETOUR);
        e1.setStatutLocation(StatutLocation.valueOf(EMPRUNT_STATUT));
        e1.setDateRetourReel(EMPRUNT_RETOUR_REEL);

        Mockito.when(this.srv.getById(EMPRUNT_ID)).thenReturn(Optional.of(e1));

        // when
        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL_BY_ID));

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateEmprunt").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateRetour").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.statutLocation").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateRetourReel").exists());

    }

    @Test
    void shouldCreateStatusUnauthorized() throws Exception {
        // given

        // when
        ResultActions result = this.createAndPost(EMPRUNT_EMPRUNT, EMPRUNT_RETOUR, EMPRUNT_STATUT, EMPRUNT_RETOUR_REEL);

        // then
        result.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void shouldCreateStatusForbidden() throws Exception {
        // given
        Emprunt empruntMock = new Emprunt();
        empruntMock.setId(EMPRUNT_ID); // ID simulé pour éviter le NPE

        // On simule le comportement du service
        Mockito.when(srv.create(Mockito.any(EmpruntRequest.class))).thenReturn(empruntMock);
        // when
        ResultActions result = this.createAndPost(EMPRUNT_EMPRUNT, EMPRUNT_RETOUR, EMPRUNT_STATUT, EMPRUNT_RETOUR_REEL);

        // then
        result.andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @WithMockUser(roles = "EMPLOYE")
    void shouldCreateStatusOk() throws Exception {
        // given
        Emprunt empruntMock = new Emprunt();
        empruntMock.setId(EMPRUNT_ID); // ID simulé pour éviter le NPE

        // On simule le comportement du service
        Mockito.when(srv.create(Mockito.any(EmpruntRequest.class))).thenReturn(empruntMock);
        // when
        ResultActions result = this.createAndPost(EMPRUNT_EMPRUNT, EMPRUNT_RETOUR, EMPRUNT_STATUT, EMPRUNT_RETOUR_REEL);

        // then
        result.andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @WithMockUser(roles = "EMPLOYE")
    void shouldCreateUseDaoSave() throws Exception {
        // given
        ArgumentCaptor<EmpruntRequest> empruntCaptor = ArgumentCaptor.captor();
        Emprunt empruntMock = new Emprunt();
        empruntMock.setId(EMPRUNT_ID); // ID simulé pour éviter le NPE

        // On simule le comportement du service
        Mockito.when(srv.create(Mockito.any(EmpruntRequest.class))).thenReturn(empruntMock);

        // when
        this.createAndPost(EMPRUNT_EMPRUNT, EMPRUNT_RETOUR, EMPRUNT_STATUT, EMPRUNT_RETOUR_REEL);
        // then
        Mockito.verify(this.srv).create(empruntCaptor.capture());

        EmpruntRequest emprunt = empruntCaptor.getValue();

        Assertions.assertEquals(EMPRUNT_EMPRUNT, emprunt.getDateEmprunt());
        Assertions.assertEquals(EMPRUNT_RETOUR, emprunt.getDateRetour());
        Assertions.assertEquals(StatutLocation.valueOf(EMPRUNT_STATUT), emprunt.getStatutLocation());
        Assertions.assertEquals(EMPRUNT_RETOUR_REEL, emprunt.getDateRetourReel());
    }

    @ParameterizedTest
    @CsvSource({
            ",,,",
            "'', '', '', ''",
            "' ', ' ', ' ', ' '",
            "'2025-01-01', '2025-01-01', '', '2025-01-01'",
            "'2025-01-01', '', '', '2025-11-18'",
            "'2025-01-01', '2025-01-01', '', ''",
            "'', '2025-01-01', '', '2025-11-18'",
            "'2025-01-01', '', '', ''",
            "'', '', '', '2025-11-18'",
            "'', '', '', ''"
    })
    @WithMockUser(roles = "EMPLOYE")
    void shouldCreateStatusBadRequest(String emprunt, String retour, String statut, String retourReel)
            throws Exception {
        // given
        LocalDate dateEmprunt = null;
        LocalDate dateRetour = null;
        LocalDate dateRetourReel = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        if (emprunt != null && !emprunt.isBlank()) {
            dateEmprunt = LocalDate.parse(emprunt, formatter);
        }

        if (retour != null && !retour.isBlank()) {
            dateRetour = LocalDate.parse(retour, formatter);
        }

        if (retourReel != null && !retourReel.isBlank()) {
            dateRetourReel = LocalDate.parse(retourReel, formatter);
        }
        // when
        ResultActions result = this.createAndPost(dateEmprunt, dateRetour, statut, dateRetourReel);

        // then
        result.andExpect(MockMvcResultMatchers.status().isBadRequest());

        Mockito.verify(this.srv, Mockito.never()).create(Mockito.any());
    }

    private ResultActions createAndPost(LocalDate dateEmprunt, LocalDate dateRetour, String statut,
            LocalDate dateRetourReel) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        EmpruntRequest request = new EmpruntRequest();

        request.setDateEmprunt(dateEmprunt);
        request.setDateRetour(dateRetour);
        request.setDateRetourReel(dateRetourReel);

        if (statut == null || statut.isBlank()) {
            request.setStatutLocation(null);
        } else {
            request.setStatutLocation(StatutLocation.valueOf(statut));
        }

        return this.mockMvc.perform(MockMvcRequestBuilders
                .post(API_URL)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(request)));
    }

    @Test
    void shouldUpdateStatusUnauthorized() throws Exception {
        // given

        // when
        ResultActions result = this.updateAndPut(EMPRUNT_EMPRUNT, EMPRUNT_RETOUR, EMPRUNT_STATUT, EMPRUNT_RETOUR_REEL);

        // then
        result.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    void shouldUpdateStatusForbidden() throws Exception {
        // given

        // when
        ResultActions result = this.updateAndPut(EMPRUNT_EMPRUNT, EMPRUNT_RETOUR, EMPRUNT_STATUT, EMPRUNT_RETOUR_REEL);

        // then
        result.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "EMPLOYE")
    void shouldUpdateStatusOk() throws Exception {
        // given
        Mockito.when(srv.getById(EMPRUNT_ID)).thenReturn(Optional.of(new Emprunt()));

        // when
        ResultActions result = this.updateAndPut(EMPRUNT_EMPRUNT, EMPRUNT_RETOUR, EMPRUNT_STATUT, EMPRUNT_RETOUR_REEL);

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = "EMPLOYE")
    void shouldUpdateUseSrvUpdate() throws Exception {
        // given
        Emprunt e = new Emprunt();
        e.setId(EMPRUNT_ID);

        Mockito.when(srv.getById(EMPRUNT_ID)).thenReturn(Optional.of(e));
        ArgumentCaptor<EmpruntRequest> empruntCaptor = ArgumentCaptor.captor();

        // when
        this.updateAndPut(EMPRUNT_EMPRUNT, EMPRUNT_RETOUR, EMPRUNT_STATUT, EMPRUNT_RETOUR_REEL);

        // then
        Mockito.verify(this.srv).update(Mockito.eq(EMPRUNT_ID), empruntCaptor.capture());

        EmpruntRequest emprunt = empruntCaptor.getValue();

        Assertions.assertEquals(EMPRUNT_EMPRUNT, emprunt.getDateEmprunt());
        Assertions.assertEquals(EMPRUNT_RETOUR, emprunt.getDateRetour());
        Assertions.assertEquals(StatutLocation.valueOf(EMPRUNT_STATUT), emprunt.getStatutLocation());
        Assertions.assertEquals(EMPRUNT_RETOUR_REEL, emprunt.getDateRetourReel());

    }

    @ParameterizedTest
    @CsvSource({
            ",,,",
            "'', '', '', ''",
            "' ', ' ', ' ', ' '",
            "'2025-01-01', '2025-01-01', '', '2025-01-01'",
            "'2025-01-01', '', '', '2025-11-18'",
            "'2025-01-01', '2025-01-01', '', ''",
            "'', '2025-01-01', '', '2025-11-18'",
            "'2025-01-01', '', '', ''",
            "'', '', '', '2025-11-18'",
            "'', '', '', ''"
    })
    @WithMockUser(roles = "EMPLOYE")
    void shouldUpdateStatusBadRequest(String emprunt, String retour, String statut, String retourReel)
            throws Exception {
        // given
        LocalDate dateEmprunt = null;
        LocalDate dateRetour = null;
        LocalDate dateRetourReel = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        if (emprunt != null && !emprunt.isBlank()) {
            dateEmprunt = LocalDate.parse(emprunt, formatter);
        }

        if (retour != null && !retour.isBlank()) {
            dateRetour = LocalDate.parse(retour, formatter);
        }

        if (retourReel != null && !retourReel.isBlank()) {
            dateRetourReel = LocalDate.parse(retourReel, formatter);
        }
        // when
        ResultActions result = this.updateAndPut(dateEmprunt, dateRetour, statut, dateRetourReel);

        // then
        result.andExpect(MockMvcResultMatchers.status().isBadRequest());

        Mockito.verify(this.srv, Mockito.never()).update(Mockito.eq(EMPRUNT_ID), Mockito.any());
    }

    private ResultActions updateAndPut(LocalDate dateEmprunt, LocalDate dateRetour, String statut,
            LocalDate dateRetourReel) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        EmpruntRequest request = new EmpruntRequest();

        request.setDateEmprunt(dateEmprunt);
        request.setDateRetour(dateRetour);
        request.setDateRetourReel(dateRetourReel);

        if (statut == null || statut.isBlank()) {
            request.setStatutLocation(null);
        } else {
            request.setStatutLocation(StatutLocation.valueOf(statut));
        }

        return this.mockMvc.perform(MockMvcRequestBuilders
                .put(API_URL_BY_ID)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(request)));
    }

    @Test
    void shouldDeleteStatusUnauthorized() throws Exception {
        ResultActions result = this.mockMvc.perform(
                MockMvcRequestBuilders.delete(API_URL_BY_ID)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()));

        result.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void shouldDeleteStatusForbidden() throws Exception {
        ResultActions result = this.mockMvc.perform(
                MockMvcRequestBuilders.delete(API_URL_BY_ID)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()));

        result.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "EMPLOYE")
    void shouldDeleteStatusOk() throws Exception {

        ResultActions result = this.mockMvc.perform(
                MockMvcRequestBuilders.delete(API_URL_BY_ID)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()));

        result.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "EMPLOYE")
    void shouldDeleteUseServiceDeleteById() throws Exception {

        this.mockMvc.perform(
                MockMvcRequestBuilders.delete(API_URL_BY_ID)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()));

        Mockito.verify(this.srv).deleteById(EMPRUNT_ID);
    }
}
