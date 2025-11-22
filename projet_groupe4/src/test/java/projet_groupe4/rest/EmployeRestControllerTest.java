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
import projet_groupe4.dto.request.SubscribeEmployeRequest;
import projet_groupe4.model.Employe;
import projet_groupe4.service.PersonneService;

@WebMvcTest(EmployeRestController.class)
public class EmployeRestControllerTest {
    private static final int EMPLOYE_ID = 1;
    private static final String EMPLOYE_NOM = "Nom";
    private static final String EMPLOYE_PRENOM = "Prenom";
    private static final String EMPLOYE_MAIL = "mail@mail.com";
    private static final String EMPLOYE_MDP = "mdp";
    private static final String EMPLOYE_TEL = "00000000";
    private static final String EMPLOYE_JOB = "Serveur";
    private static final boolean EMPLOYE_GAME_MASTER = true;
    private static final String API_URL = "/api/employe";
    private static final String API_URL_BY_ID = API_URL + "/" + EMPLOYE_ID;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PersonneService srv;

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
        Mockito.verify(this.srv).getAllEmployes();
    }

    @Test
    @WithMockUser
    void shouldGetAllReturnAttributes() throws Exception {

        // --- GIVEN ---
        Employe e1 = new Employe();

        e1.setId(EMPLOYE_ID);
        e1.setNom(EMPLOYE_NOM);
        e1.setPrenom(EMPLOYE_PRENOM);
        e1.setMail(EMPLOYE_MAIL);
        e1.setMdp(EMPLOYE_MDP);
        e1.setTelephone(EMPLOYE_TEL);
        e1.setJob(EMPLOYE_JOB);
        e1.setGameMaster(EMPLOYE_GAME_MASTER);

        // On retourne la liste de EMPLOYE
        Mockito.when(srv.getAllEmployes()).thenReturn(List.of(e1));

        // --- WHEN ---
        ResultActions result = this.mockMvc.perform(
                MockMvcRequestBuilders.get(API_URL));

        // --- THEN ---

        result.andExpect(MockMvcResultMatchers.jsonPath("$[0].id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nom").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].prenom").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].mail").exists())
                // .andExpect(MockMvcResultMatchers.jsonPath("$[0].mdp").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].telephone").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].job").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].gameMaster").exists());

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
        Mockito.when(this.srv.getEmployeById(EMPLOYE_ID)).thenReturn(Optional.of(new Employe()));
        // when
        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL_BY_ID));

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser
    void shouldGetByIdUseServiceGetById() throws Exception {
        // given
        Mockito.when(this.srv.getEmployeById(EMPLOYE_ID)).thenReturn(Optional.of(new Employe()));

        // when
        this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL_BY_ID));

        // then
        Mockito.verify(this.srv).getEmployeById(EMPLOYE_ID);
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
        Employe e1 = new Employe();

        e1.setId(EMPLOYE_ID);
        e1.setNom(EMPLOYE_NOM);
        e1.setPrenom(EMPLOYE_PRENOM);
        e1.setMail(EMPLOYE_MAIL);
        e1.setMdp(EMPLOYE_MDP);
        e1.setTelephone(EMPLOYE_TEL);
        e1.setJob(EMPLOYE_JOB);
        e1.setGameMaster(EMPLOYE_GAME_MASTER);

        Mockito.when(this.srv.getEmployeById(EMPLOYE_ID)).thenReturn(Optional.of(e1));

        // when
        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL_BY_ID));

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nom").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.prenom").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.mail").exists())
                // .andExpect(MockMvcResultMatchers.jsonPath("$.mdp").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.telephone").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.job").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.gameMaster").exists());

    }

    @Test
    void shouldCreateStatusUnauthorized() throws Exception {
        // given

        // when
        ResultActions result = this.createAndPost(EMPLOYE_NOM, EMPLOYE_PRENOM, EMPLOYE_MAIL, EMPLOYE_MDP, EMPLOYE_TEL,
                EMPLOYE_JOB, EMPLOYE_GAME_MASTER);

        // then
        result.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void shouldCreateStatusForbidden() throws Exception {
        // given

        // when
        ResultActions result = this.createAndPost(EMPLOYE_NOM, EMPLOYE_PRENOM, EMPLOYE_MAIL, EMPLOYE_MDP, EMPLOYE_TEL,
                EMPLOYE_JOB, EMPLOYE_GAME_MASTER);

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = "EMPLOYE")
    void shouldCreateStatusOk() throws Exception {
        // given

        // when
        ResultActions result = this.createAndPost(EMPLOYE_NOM, EMPLOYE_PRENOM, EMPLOYE_MAIL, EMPLOYE_MDP, EMPLOYE_TEL,
                EMPLOYE_JOB, EMPLOYE_GAME_MASTER);

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = "EMPLOYE")
    void shouldCreateUseDaoSave() throws Exception {
        // given
        ArgumentCaptor<Employe> employeCaptor = ArgumentCaptor.captor();

        // when
        this.createAndPost(EMPLOYE_NOM, EMPLOYE_PRENOM, EMPLOYE_MAIL, EMPLOYE_MDP, EMPLOYE_TEL, EMPLOYE_JOB,
                EMPLOYE_GAME_MASTER);
        // then
        Mockito.verify(this.srv).create(employeCaptor.capture());

        Employe employe = employeCaptor.getValue();

        Assertions.assertEquals(EMPLOYE_NOM, employe.getNom());
        Assertions.assertEquals(EMPLOYE_PRENOM, employe.getPrenom());
        Assertions.assertEquals(EMPLOYE_MAIL, employe.getMail());
        Assertions.assertEquals(EMPLOYE_MDP, employe.getMdp());
        Assertions.assertEquals(EMPLOYE_TEL, employe.getTelephone());
        Assertions.assertEquals(EMPLOYE_JOB, employe.getJob());
        Assertions.assertEquals(EMPLOYE_GAME_MASTER, employe.isGameMaster());
    }

    @ParameterizedTest
    @CsvSource({
            // Tout null ou vide
            ",,,,,,false",
            "'', '', '', '', '', '', false",
            "'  ', '  ', '  ', '  ', '  ', '  ', false",

            // Nom manquant
            ", prenom, mail, mdp, tel, job, false",
            "'', prenom, mail, mdp, tel, job, false",

            // Prénom manquant
            "nom, '', mail, mdp, tel, job, false",
            "nom, '  ', mail, mdp, tel, job, false",

            // Mail manquant
            "nom, prenom, '', mdp, tel, job, false",
            "nom, prenom, '  ', mdp, tel, job, false",

            // Mot de passe manquant
            "nom, prenom, mail, '', tel, job, false",
            "nom, prenom, mail, '  ', tel, job, false",

            // Téléphone manquant
            "nom, prenom, mail, mdp, '', job, false",
            "nom, prenom, mail, mdp, '  ', job, false",

            // Job manquant
            "nom, prenom, mail, mdp, tel, '', false",
            "nom, prenom, mail, mdp, tel, '  ', false"
    })
    @WithMockUser(roles = "EMPLOYE")
    void shouldCreateStatusBadRequest(String nom, String prenom, String mail, String mdp, String tel, String job,
            boolean gameMaster)
            throws Exception {
        // given

        // when
        ResultActions result = this.createAndPost(nom, prenom, mail, mdp, tel, job, gameMaster);

        // then
        result.andExpect(MockMvcResultMatchers.status().isBadRequest());

        Mockito.verify(this.srv, Mockito.never()).create(Mockito.any());
    }

    private ResultActions createAndPost(String nom, String prenom, String mail, String mdp, String tel, String job,
            boolean gameMaster) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        SubscribeEmployeRequest request = new SubscribeEmployeRequest();

        request.setNom(nom);
        request.setPrenom(prenom);
        request.setMail(mail);
        request.setMdp(mdp);
        request.setTelephone(tel);
        request.setJob(job);
        request.setGameMaster(gameMaster);

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
        ResultActions result = this.updateAndPut(EMPLOYE_NOM, EMPLOYE_PRENOM, EMPLOYE_MAIL, EMPLOYE_MDP, EMPLOYE_TEL,
                EMPLOYE_JOB,
                EMPLOYE_GAME_MASTER);

        // then
        result.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    void shouldUpdateStatusForbidden() throws Exception {
        // given

        // when
        ResultActions result = this.updateAndPut(EMPLOYE_NOM, EMPLOYE_PRENOM, EMPLOYE_MAIL, EMPLOYE_MDP, EMPLOYE_TEL,
                EMPLOYE_JOB,
                EMPLOYE_GAME_MASTER);

        // then
        result.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "EMPLOYE")
    void shouldUpdateStatusOk() throws Exception {
        // given
        Mockito.when(srv.getEmployeById(EMPLOYE_ID)).thenReturn(Optional.of(new Employe()));

        // when
        ResultActions result = this.updateAndPut(EMPLOYE_NOM, EMPLOYE_PRENOM, EMPLOYE_MAIL, EMPLOYE_MDP, EMPLOYE_TEL,
                EMPLOYE_JOB,
                EMPLOYE_GAME_MASTER);

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = "EMPLOYE")
    void shouldUpdateUseSrvUpdate() throws Exception {
        // given
        Employe e = new Employe();
        e.setId(EMPLOYE_ID);

        Mockito.when(srv.getEmployeById(EMPLOYE_ID)).thenReturn(Optional.of(e));
        ArgumentCaptor<Employe> employeCaptor = ArgumentCaptor.captor();

        // when
        this.updateAndPut(EMPLOYE_NOM, EMPLOYE_PRENOM, EMPLOYE_MAIL, EMPLOYE_MDP, EMPLOYE_TEL, EMPLOYE_JOB,
                EMPLOYE_GAME_MASTER);

        // then
        Mockito.verify(this.srv).update(employeCaptor.capture());

        Employe employe = employeCaptor.getValue();

        Assertions.assertEquals(EMPLOYE_NOM, employe.getNom());
        Assertions.assertEquals(EMPLOYE_PRENOM, employe.getPrenom());
        Assertions.assertEquals(EMPLOYE_MAIL, employe.getMail());
        Assertions.assertEquals(EMPLOYE_MDP, employe.getMdp());
        Assertions.assertEquals(EMPLOYE_TEL, employe.getTelephone());
        Assertions.assertEquals(EMPLOYE_JOB, employe.getJob());
        Assertions.assertEquals(EMPLOYE_GAME_MASTER, employe.isGameMaster());

    }

    @ParameterizedTest
    @CsvSource({
            // Tout null ou vide
            ",,,,,,false",
            "'', '', '', '', '', '', false",
            "'  ', '  ', '  ', '  ', '  ', '  ', false",

            // Nom manquant
            ", prenom, mail, mdp, tel, job, false",
            "'', prenom, mail, mdp, tel, job, false",

            // Prénom manquant
            "nom, '', mail, mdp, tel, job, false",
            "nom, '  ', mail, mdp, tel, job, false",

            // Mail manquant
            "nom, prenom, '', mdp, tel, job, false",
            "nom, prenom, '  ', mdp, tel, job, false",

            // Mot de passe manquant
            "nom, prenom, mail, '', tel, job, false",
            "nom, prenom, mail, '  ', tel, job, false",

            // Téléphone manquant
            "nom, prenom, mail, mdp, '', job, false",
            "nom, prenom, mail, mdp, '  ', job, false",

            // Job manquant
            "nom, prenom, mail, mdp, tel, '', false",
            "nom, prenom, mail, mdp, tel, '  ', false"
    })
    @WithMockUser(roles = "EMPLOYE")
    void shouldUpdateStatusBadRequest(String nom, String prenom, String mail, String mdp, String tel, String job,
            boolean gameMaster)
            throws Exception {
        // given
        // when
        ResultActions result = this.updateAndPut(nom, prenom, mail, mdp, tel, job, gameMaster);

        // then
        result.andExpect(MockMvcResultMatchers.status().isBadRequest());

        Mockito.verify(this.srv, Mockito.never()).update(Mockito.any());
    }

    private ResultActions updateAndPut(String nom, String prenom, String mail, String mdp, String tel, String job,
            boolean gameMaster) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        SubscribeEmployeRequest request = new SubscribeEmployeRequest();

        request.setNom(nom);
        request.setPrenom(prenom);
        request.setMail(mail);
        request.setMdp(mdp);
        request.setTelephone(tel);
        request.setJob(job);
        request.setGameMaster(gameMaster);

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

        Mockito.verify(this.srv).deleteById(EMPLOYE_ID);
    }
}
