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
import projet_groupe4.dto.request.BadgeRequest;
import projet_groupe4.model.Badge;
import projet_groupe4.service.BadgeService;

@WebMvcTest(BadgeRestController.class)
public class BadgeRestControllerTest {
    private static final int BADGE_ID = 1;
    private static final String BADGE_NOM = "Badge";
    private static final int BADGE_POINT_MIN = 0;
    private static final String BADGE_IMG = "IMG";
    private static final String API_URL = "/api/badge";
    private static final String API_URL_BY_ID = API_URL + "/" + BADGE_ID;

    @MockitoBean
    private BadgeService srv;

    @Autowired
    private MockMvc mockMvc;

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
        Badge b1 = new Badge();
        b1.setId(BADGE_ID);
        b1.setNomBadge(BADGE_NOM);
        b1.setPointMin(BADGE_POINT_MIN);
        b1.setImgURL(BADGE_IMG);

        // On retourne la liste de BADGE
        Mockito.when(srv.getAll()).thenReturn(List.of(b1));

        // --- WHEN ---
        ResultActions result = this.mockMvc.perform(
                MockMvcRequestBuilders.get(API_URL));

        // --- THEN ---

        result.andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(BADGE_ID))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nomBadge").value(BADGE_NOM))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].pointMin").value(BADGE_POINT_MIN))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].imgURL").value(BADGE_IMG));
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
        Mockito.when(this.srv.getById(BADGE_ID)).thenReturn(Optional.of(new Badge()));
        // when
        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL_BY_ID));

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser
    void shouldGetByIdUseServiceGetById() throws Exception {
        // given
        Mockito.when(this.srv.getById(BADGE_ID)).thenReturn(Optional.of(new Badge()));

        // when
        this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL_BY_ID));

        // then
        Mockito.verify(this.srv).getById(BADGE_ID);
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
        Badge b1 = new Badge();
        b1.setId(BADGE_ID);
        b1.setNomBadge(BADGE_NOM);
        b1.setPointMin(BADGE_POINT_MIN);
        b1.setImgURL(BADGE_IMG);
        Mockito.when(this.srv.getById(BADGE_ID)).thenReturn(Optional.of(b1));

        // when
        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL_BY_ID));

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.nomBadge").exists());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.pointMin").exists());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.imgURL").exists());
    }

    @Test
    void shouldCreateStatusUnauthorized() throws Exception {
        // given

        // when
        ResultActions result = this.createAndPost(BADGE_NOM, BADGE_POINT_MIN, BADGE_IMG);

        // then
        result.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void shouldCreateStatusForbidden() throws Exception {
        // given
        Badge badgeMock = new Badge();
        badgeMock.setId(BADGE_ID); // ID simulé pour éviter le NPE

        // On simule le comportement du service
        Mockito.when(srv.create(Mockito.any(BadgeRequest.class))).thenReturn(badgeMock);
        // when
        ResultActions result = this.createAndPost(BADGE_NOM, BADGE_POINT_MIN, BADGE_IMG);

        // then
        result.andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @WithMockUser(roles = "EMPLOYE")
    void shouldCreateStatusOk() throws Exception {
        // given
        Badge badgeMock = new Badge();
        badgeMock.setId(BADGE_ID); // ID simulé pour éviter le NPE

        // On simule le comportement du service
        Mockito.when(srv.create(Mockito.any(BadgeRequest.class))).thenReturn(badgeMock);
        // when
        ResultActions result = this.createAndPost(BADGE_NOM, BADGE_POINT_MIN, BADGE_IMG);

        // then
        result.andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @WithMockUser(roles = "EMPLOYE")
    void shouldCreateUseDaoSave() throws Exception {
        // given
        ArgumentCaptor<BadgeRequest

        > badgeCaptor = ArgumentCaptor.captor();

        Badge badgeMock = new Badge();
        badgeMock.setId(BADGE_ID); // ID simulé pour éviter le NPE

        // On simule le comportement du service
        Mockito.when(srv.create(Mockito.any(BadgeRequest.class))).thenReturn(badgeMock);
        // when
        this.createAndPost(BADGE_NOM, BADGE_POINT_MIN, BADGE_IMG);

        // then
        Mockito.verify(this.srv).create(badgeCaptor.capture());

        BadgeRequest request = badgeCaptor.getValue();

        Assertions.assertEquals(BADGE_NOM, request.getNomBadge());
        Assertions.assertEquals(BADGE_POINT_MIN, request.getPointMin());
        Assertions.assertEquals(BADGE_IMG, request.getImgURL());
    }

    @ParameterizedTest
    @CsvSource({
            "nom,0,''",
            "nom,0, '   '",
            "nom,0,",
            "'',0,commentaire",
            "'',0,commentaire",
            ",0,commentaire"
    })
    @WithMockUser(roles = "EMPLOYE")
    void shouldCreateStatusBadRequest(String nom, int pointMin, String imgUrl) throws Exception {
        // given

        // when
        ResultActions result = this.createAndPost(nom, pointMin, imgUrl);

        // then
        result.andExpect(MockMvcResultMatchers.status().isBadRequest());

        Mockito.verify(this.srv, Mockito.never()).create(Mockito.any());
    }

    private ResultActions createAndPost(String nom, int pointMin, String imgUrl) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        BadgeRequest request = new BadgeRequest();

        request.setNomBadge(nom);
        request.setPointMin(pointMin);
        request.setImgURL(imgUrl);

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
        ResultActions result = this.updateAndPut(BADGE_NOM, BADGE_POINT_MIN, BADGE_IMG);

        // then
        result.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    void shouldUpdateStatusForbidden() throws Exception {
        // given

        // when
        ResultActions result = this.updateAndPut(BADGE_NOM, BADGE_POINT_MIN, BADGE_IMG);

        // then
        result.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "EMPLOYE")
    void shouldUpdateStatusOk() throws Exception {
        // given
        Mockito.when(srv.getById(BADGE_ID)).thenReturn(Optional.of(new Badge()));

        // when
        ResultActions result = this.updateAndPut(BADGE_NOM, BADGE_POINT_MIN, BADGE_IMG);

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = "EMPLOYE")
    void shouldUpdateUseSrvUpdate() throws Exception {
        // given
        Badge b = new Badge();
        b.setId(BADGE_ID);

        Mockito.when(srv.getById(BADGE_ID)).thenReturn(Optional.of(b));
        ArgumentCaptor<BadgeRequest> badgeCaptor = ArgumentCaptor.captor();

        // when
        this.updateAndPut(BADGE_NOM, BADGE_POINT_MIN, BADGE_IMG);

        // then
        Mockito.verify(this.srv).update(Mockito.eq(BADGE_ID), badgeCaptor.capture());

        BadgeRequest request = badgeCaptor.getValue();

        Assertions.assertEquals(BADGE_NOM, request.getNomBadge());
        Assertions.assertEquals(BADGE_POINT_MIN, request.getPointMin());
        Assertions.assertEquals(BADGE_IMG, request.getImgURL());
    }

    @ParameterizedTest
    @CsvSource({
            "nom,0,''",
            "nom,0, '   '",
            "nom,0,",
            "'',0,commentaire",
            "'',0,commentaire",
            ",0,commentaire"
    })
    @WithMockUser(roles = "EMPLOYE")
    void shouldUpdateStatusBadRequest(String nom, int pointMin, String imgUrl) throws Exception {
        // given

        // when
        ResultActions result = this.updateAndPut(nom, pointMin, imgUrl);

        // then
        result.andExpect(MockMvcResultMatchers.status().isBadRequest());

        Mockito.verify(this.srv, Mockito.never()).update(Mockito.eq(BADGE_ID), Mockito.any());
    }

    private ResultActions updateAndPut(String nom, int pointMin, String imgUrl) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        BadgeRequest request = new BadgeRequest();

        request.setNomBadge(nom);
        request.setPointMin(pointMin);
        request.setImgURL(imgUrl);

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

        Mockito.verify(this.srv).deleteById(BADGE_ID);
    }

}
