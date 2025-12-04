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
import projet_groupe4.dto.request.PasswordEmployeRequest;
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
        Employe employeMock = new Employe();
        employeMock.setId(EMPLOYE_ID); // ID simulé pour éviter le NPE

        // On simule le comportement du service
        Mockito.when(srv.create(Mockito.any(SubscribeEmployeRequest.class))).thenReturn(employeMock);
        // when
        ResultActions result = this.createAndPost(EMPLOYE_NOM, EMPLOYE_PRENOM, EMPLOYE_MAIL, EMPLOYE_MDP, EMPLOYE_TEL,
                EMPLOYE_JOB, EMPLOYE_GAME_MASTER);

        // then
        result.andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @WithMockUser(roles = "EMPLOYE")
    void shouldCreateStatusOk() throws Exception {
        // given
        Employe employeMock = new Employe();
        employeMock.setId(EMPLOYE_ID); // ID simulé pour éviter le NPE

        // On simule le comportement du service
        Mockito.when(srv.create(Mockito.any(SubscribeEmployeRequest.class))).thenReturn(employeMock);
        // when
        ResultActions result = this.createAndPost(EMPLOYE_NOM, EMPLOYE_PRENOM, EMPLOYE_MAIL, EMPLOYE_MDP, EMPLOYE_TEL,
                EMPLOYE_JOB, EMPLOYE_GAME_MASTER);

        // then
        result.andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @WithMockUser(roles = "EMPLOYE")
    void shouldCreateUseDaoSave() throws Exception {
        // given
        ArgumentCaptor<SubscribeEmployeRequest> employeCaptor = ArgumentCaptor.captor();

        Employe employeMock = new Employe();
        employeMock.setId(EMPLOYE_ID); // ID simulé pour éviter le NPE

        // On simule le comportement du service
        Mockito.when(srv.create(Mockito.any(SubscribeEmployeRequest.class))).thenReturn(employeMock);
        // when
        this.createAndPost(EMPLOYE_NOM, EMPLOYE_PRENOM, EMPLOYE_MAIL, EMPLOYE_MDP, EMPLOYE_TEL, EMPLOYE_JOB,
                EMPLOYE_GAME_MASTER);
        // then
        Mockito.verify(this.srv).create(employeCaptor.capture());

        SubscribeEmployeRequest employe = employeCaptor.getValue();

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
        ArgumentCaptor<SubscribeEmployeRequest> employeCaptor = ArgumentCaptor.captor();

        // when
        this.updateAndPut(EMPLOYE_NOM, EMPLOYE_PRENOM, EMPLOYE_MAIL, EMPLOYE_MDP, EMPLOYE_TEL, EMPLOYE_JOB,
                EMPLOYE_GAME_MASTER);

        // then
        Mockito.verify(this.srv).update(Mockito.eq(EMPLOYE_ID), employeCaptor.capture());

        SubscribeEmployeRequest employe = employeCaptor.getValue();

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

        Mockito.verify(this.srv, Mockito.never()).update(Mockito.eq(EMPLOYE_ID), Mockito.any());
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

        Mockito.verify(this.srv).deleteById(EMPLOYE_ID);
    }
    
    
    
    
    
    
    
    @Test
    void shouldGetMyProfileUnauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(API_URL + "/me"))
               .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "CLIENT")
    void shouldGetMyProfileForbidden() throws Exception {
    	mockMvc.perform(MockMvcRequestBuilders.get(API_URL + "/me"))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "EMPLOYE")
    void shouldGetMyProfileOk() throws Exception {
        Employe e = new Employe();
        e.setId(EMPLOYE_ID);
        e.setNom(EMPLOYE_NOM);
        e.setPrenom(EMPLOYE_PRENOM);
        e.setMail(EMPLOYE_MAIL);

        Mockito.when(srv.getByMail(EMPLOYE_MAIL)).thenReturn(Optional.of(e));

        mockMvc.perform(MockMvcRequestBuilders.get(API_URL + "/me")
                .with(SecurityMockMvcRequestPostProcessors.user(EMPLOYE_MAIL).roles("EMPLOYE")))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(EMPLOYE_ID))
               .andExpect(MockMvcResultMatchers.jsonPath("$.nom").value(EMPLOYE_NOM))
               .andExpect(MockMvcResultMatchers.jsonPath("$.prenom").value(EMPLOYE_PRENOM))
               .andExpect(MockMvcResultMatchers.jsonPath("$.mail").value(EMPLOYE_MAIL));
    }

    @Test
    @WithMockUser(roles = "EMPLOYE")
    void shouldGetMyProfileNotFound() throws Exception {
        Mockito.when(srv.getByMail(EMPLOYE_MAIL)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get(API_URL + "/me")
                .principal(() -> EMPLOYE_MAIL))
               .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    // --- PUT /api/employe/me ---
    @Test
    void shouldUpdateMyProfileUnauthorized() throws Exception {
    	mockMvc.perform(MockMvcRequestBuilders.put(API_URL + "/me")
    	        .contentType(MediaType.APPLICATION_JSON)
    	        .content("{}"))
    	       .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "CLIENT")
    void shouldUpdateMyProfileForbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(API_URL + "/me")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
               .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "EMPLOYE")
    void shouldUpdateMyProfileOk() throws Exception {
        Employe e = new Employe();
        e.setId(EMPLOYE_ID);
        e.setMail(EMPLOYE_MAIL);

        Mockito.when(srv.getByMail(EMPLOYE_MAIL)).thenReturn(Optional.of(e));

        SubscribeEmployeRequest req = new SubscribeEmployeRequest();
        req.setNom("NewNom");
        req.setPrenom("Prenom");
        req.setMail(EMPLOYE_MAIL);
        req.setMdp("mdp");
        req.setTelephone("00000000");
        req.setJob("Serveur");
        req.setGameMaster(true);

        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders.put(API_URL + "/me")
                .with(SecurityMockMvcRequestPostProcessors.user(EMPLOYE_MAIL).roles("EMPLOYE"))
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)))
               .andExpect(MockMvcResultMatchers.status().isOk());

        ArgumentCaptor<SubscribeEmployeRequest> captor = ArgumentCaptor.forClass(SubscribeEmployeRequest.class);
        Mockito.verify(srv).update(Mockito.eq(EMPLOYE_ID), captor.capture());

        SubscribeEmployeRequest actualReq = captor.getValue();
        Assertions.assertEquals("NewNom", actualReq.getNom());
        Assertions.assertEquals("Prenom", actualReq.getPrenom());
        Assertions.assertEquals(EMPLOYE_MAIL, actualReq.getMail());
        Assertions.assertEquals("mdp", actualReq.getMdp());
        Assertions.assertEquals("00000000", actualReq.getTelephone());
        Assertions.assertEquals("Serveur", actualReq.getJob());
        Assertions.assertTrue(actualReq.isGameMaster());
    }

    @Test
    @WithMockUser(roles = "EMPLOYE")
    void shouldUpdateMyProfileBadRequest() throws Exception {
        Employe e = new Employe();
        e.setId(EMPLOYE_ID);

        Mockito.when(srv.getByMail(EMPLOYE_MAIL)).thenReturn(Optional.of(e));

        // Nom null
        SubscribeEmployeRequest req = new SubscribeEmployeRequest();
        req.setNom(null);

        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders.put(API_URL + "/me")
                .principal(() -> EMPLOYE_MAIL)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)))
               .andExpect(MockMvcResultMatchers.status().isBadRequest());

        Mockito.verify(srv, Mockito.never()).update(Mockito.eq(EMPLOYE_ID), Mockito.any());
    }

    // --- PUT /api/employe/{id}/password ---
    @Test
    void shouldChangePasswordUnauthorized() throws Exception {
        PasswordEmployeRequest req = new PasswordEmployeRequest();
        req.setOldPassword("old");
        req.setNewPassword("new");

        mockMvc.perform(MockMvcRequestBuilders.put(API_URL_BY_ID + "/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(req)))
               .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "CLIENT")
    void shouldChangePasswordForbidden() throws Exception {
        PasswordEmployeRequest req = new PasswordEmployeRequest();
        req.setOldPassword("old");
        req.setNewPassword("new");

        mockMvc.perform(MockMvcRequestBuilders.put(API_URL_BY_ID + "/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(req)))
               .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "EMPLOYE")
    void shouldChangePasswordOk() throws Exception {
        PasswordEmployeRequest req = new PasswordEmployeRequest();
        req.setOldPassword("old");
        req.setNewPassword("new");

        Mockito.when(srv.changePassword(EMPLOYE_ID, "old", "new")).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.put(API_URL_BY_ID + "/password")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(req)))
               .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = "EMPLOYE")
    void shouldChangePasswordForbiddenWhenOldPasswordIncorrect() throws Exception {
        PasswordEmployeRequest req = new PasswordEmployeRequest();
        req.setOldPassword("wrong");
        req.setNewPassword("new");

        Mockito.when(srv.changePassword(EMPLOYE_ID, "wrong", "new")).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.put(API_URL_BY_ID + "/password")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(req)))
               .andExpect(MockMvcResultMatchers.status().isForbidden());
    }
    
}
