package projet_groupe4.rest;

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

import projet_groupe4.dao.IDAOPersonne;
import projet_groupe4.dto.request.TableRequest;
import projet_groupe4.model.TableJeu;
import projet_groupe4.service.TableJeuService;

@WebMvcTest(TableJeuRestController.class)
public class TableJeuRestControllerTest {
    private static final int TABLEJEU_ID = 1;
    private static final String TABLEJEU_NOM = "Nom";
    private static final int TABLEJEU_CAPA = 5;
    private static final String TABLEJEU_IMG = "image";
    private static final String API_URL = "/api/tableJeu";
    private static final String API_URL_BY_ID = API_URL + "/" + TABLEJEU_ID;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TableJeuService srv;

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
        TableJeu t1 = new TableJeu();

        t1.setId(TABLEJEU_ID);
        t1.setNomTable(TABLEJEU_NOM);
        t1.setCapacite(TABLEJEU_CAPA);
        t1.setImgUrl(TABLEJEU_IMG);

        // On retourne la liste de TABLEJEU
        Mockito.when(srv.getAll()).thenReturn(List.of(t1));

        // --- WHEN ---
        ResultActions result = this.mockMvc.perform(
                MockMvcRequestBuilders.get(API_URL));

        // --- THEN ---

        result.andExpect(MockMvcResultMatchers.jsonPath("$[0].id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nomTable").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].capacite").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].imgUrl").exists());

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
        Mockito.when(this.srv.getById(TABLEJEU_ID)).thenReturn(Optional.of(new TableJeu()));
        // when
        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL_BY_ID));

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser
    void shouldGetByIdUseServiceGetById() throws Exception {
        // given
        Mockito.when(this.srv.getById(TABLEJEU_ID)).thenReturn(Optional.of(new TableJeu()));

        // when
        this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL_BY_ID));

        // then
        Mockito.verify(this.srv).getById(TABLEJEU_ID);
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
        TableJeu t1 = new TableJeu();

        t1.setId(TABLEJEU_ID);
        t1.setNomTable(TABLEJEU_NOM);
        t1.setCapacite(TABLEJEU_CAPA);
        t1.setImgUrl(TABLEJEU_IMG);

        Mockito.when(this.srv.getById(TABLEJEU_ID)).thenReturn(Optional.of(t1));

        // when
        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL_BY_ID));

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nomTable").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.capacite").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.imgUrl").exists());

    }

    @Test
    void shouldCreateStatusUnauthorized() throws Exception {
        // given

        // when
        ResultActions result = this.createAndPost(TABLEJEU_NOM, TABLEJEU_CAPA, TABLEJEU_IMG);

        // then
        result.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void shouldCreateStatusForbidden() throws Exception {
        // given

        // when
        ResultActions result = this.createAndPost(TABLEJEU_NOM, TABLEJEU_CAPA, TABLEJEU_IMG);

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = "EMPLOYE")
    void shouldCreateStatusOk() throws Exception {
        // given

        // when
        ResultActions result = this.createAndPost(TABLEJEU_NOM, TABLEJEU_CAPA, TABLEJEU_IMG);

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = "EMPLOYE")
    void shouldCreateUseDaoSave() throws Exception {
        // given
        ArgumentCaptor<TableJeu> tableJeuCaptor = ArgumentCaptor.captor();

        // when
        this.createAndPost(TABLEJEU_NOM, TABLEJEU_CAPA, TABLEJEU_IMG);
        // then
        Mockito.verify(this.srv).create(tableJeuCaptor.capture());

        TableJeu tableJeu = tableJeuCaptor.getValue();

        Assertions.assertEquals(TABLEJEU_NOM, tableJeu.getNomTable());
        Assertions.assertEquals(TABLEJEU_CAPA, tableJeu.getCapacite());
        Assertions.assertEquals(TABLEJEU_IMG, tableJeu.getImgUrl());
    }

    @ParameterizedTest
    @CsvSource({
            "nom, 0,",
            "nom, 0, ''"
    })
    @WithMockUser(roles = "EMPLOYE")
    void shouldCreateStatusBadRequest(String nom, int capacite, String imgUrl)
            throws Exception {
        // given

        // when
        ResultActions result = this.createAndPost(nom, capacite, imgUrl);

        // then
        result.andExpect(MockMvcResultMatchers.status().isBadRequest());

        Mockito.verify(this.srv, Mockito.never()).create(Mockito.any());
    }

    private ResultActions createAndPost(String nom, int capacite, String imgUrl) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        TableRequest request = new TableRequest();

        request.setNomTable(nom);
        request.setCapacite(capacite);
        request.setImgUrl(imgUrl);

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
        ResultActions result = this.updateAndPut(TABLEJEU_NOM, TABLEJEU_CAPA, TABLEJEU_IMG);

        // then
        result.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    void shouldUpdateStatusForbidden() throws Exception {
        // given

        // when
        ResultActions result = this.updateAndPut(TABLEJEU_NOM, TABLEJEU_CAPA, TABLEJEU_IMG);

        // then
        result.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "EMPLOYE")
    void shouldUpdateStatusOk() throws Exception {
        // given
        Mockito.when(srv.getById(TABLEJEU_ID)).thenReturn(Optional.of(new TableJeu()));

        // when
        ResultActions result = this.updateAndPut(TABLEJEU_NOM, TABLEJEU_CAPA, TABLEJEU_IMG);

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = "EMPLOYE")
    void shouldUpdateUseSrvUpdate() throws Exception {
        // given
        TableJeu t = new TableJeu();
        t.setId(TABLEJEU_ID);

        Mockito.when(srv.getById(TABLEJEU_ID)).thenReturn(Optional.of(t));
        ArgumentCaptor<TableJeu> tableJeuCaptor = ArgumentCaptor.captor();

        // when
        this.updateAndPut(TABLEJEU_NOM, TABLEJEU_CAPA, TABLEJEU_IMG);

        // then
        Mockito.verify(this.srv).update(tableJeuCaptor.capture());

        TableJeu tableJeu = tableJeuCaptor.getValue();

        Assertions.assertEquals(TABLEJEU_NOM, tableJeu.getNomTable());
        Assertions.assertEquals(TABLEJEU_CAPA, tableJeu.getCapacite());
        Assertions.assertEquals(TABLEJEU_IMG, tableJeu.getImgUrl());
    }

    @ParameterizedTest
    @CsvSource({
            "nom, 0,",
            "nom, 0, ''",
    })
    @WithMockUser(roles = "EMPLOYE")
    void shouldUpdateStatusBadRequest(String nom, int capacite, String imgUrl)
            throws Exception {
        // given
        // when
        ResultActions result = this.updateAndPut(nom, capacite, imgUrl);

        // then
        result.andExpect(MockMvcResultMatchers.status().isBadRequest());

        Mockito.verify(this.srv, Mockito.never()).update(Mockito.any());
    }

    private ResultActions updateAndPut(String nom, int capacite, String imgUrl) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        TableRequest request = new TableRequest();

        request.setNomTable(nom);
        request.setCapacite(capacite);
        request.setImgUrl(imgUrl);

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

        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = "EMPLOYE")
    void shouldDeleteStatusOk() throws Exception {

        ResultActions result = this.mockMvc.perform(
                MockMvcRequestBuilders.delete(API_URL_BY_ID)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()));

        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = "EMPLOYE")
    void shouldDeleteUseServiceDeleteById() throws Exception {

        this.mockMvc.perform(
                MockMvcRequestBuilders.delete(API_URL_BY_ID)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()));

        Mockito.verify(this.srv).deleteById(TABLEJEU_ID);
    }
}
