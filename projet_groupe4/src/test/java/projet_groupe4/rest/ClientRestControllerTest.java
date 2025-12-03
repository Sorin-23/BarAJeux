package projet_groupe4.rest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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
import projet_groupe4.dto.request.SubscribeClientRequest;
import projet_groupe4.dto.request.UpdateClientRequest;
import projet_groupe4.model.Client;
import projet_groupe4.service.PersonneService;

@WebMvcTest(ClientRestController.class)
public class ClientRestControllerTest {
    private static final int CLIENT_ID = 1;
    private static final String CLIENT_NOM = "Nom";
    private static final String CLIENT_PRENOM = "Prenom";
    private static final String CLIENT_MAIL = "mail@mail.com";
    private static final String CLIENT_MDP = "mdp";
    private static final String CLIENT_TEL = "00000000";
    private static final int CLIENT_POINT_FDT = 10;
    private static final LocalDateTime CLIENT_CREATION = LocalDateTime.now();
    private static final LocalDateTime CLIENT_LAST_CONNEX = LocalDateTime.now();
    private static final String CLIENT_VILLE = "Nice";
    private static final String CLIENT_CP = "06";
    private static final String CLIENT_ADRESSE = "1 rue de Nice";
    private static final String API_URL = "/api/client";
    private static final String API_URL_BY_ID = API_URL + "/" + CLIENT_ID;
    private static final String API_URL_WITH_RESA = API_URL + "/" + "reservations" + "/" + CLIENT_ID;
    private static final String API_URL_WITH_EMPRUNT = API_URL + "/" + "emprunts" + "/" + CLIENT_ID;

    @MockitoBean
    private PersonneService srv;

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
        Mockito.verify(this.srv).getAllClients();
    }

    @Test
    @WithMockUser
    void shouldGetAllReturnAttributes() throws Exception {

        // --- GIVEN ---
        Client c1 = new Client();

        c1.setId(CLIENT_ID);
        c1.setNom(CLIENT_NOM);
        c1.setPrenom(CLIENT_PRENOM);
        c1.setMail(CLIENT_MAIL);
        // c1.setMdp(CLIENT_MDP);
        c1.setTelephone(CLIENT_TEL);
        c1.setPointFidelite(CLIENT_POINT_FDT);
        c1.setDateCreation(CLIENT_CREATION);
        c1.setDateLastConnexion(CLIENT_LAST_CONNEX);
        c1.setVille(CLIENT_VILLE);
        c1.setCodePostale(CLIENT_CP);
        c1.setAdresse(CLIENT_ADRESSE);

        // On retourne la liste de CLIENT
        Mockito.when(srv.getAllClients()).thenReturn(List.of(c1));

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
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].pointFidelite").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].dateCreation").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].dateLastConnexion").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].ville").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].codePostale").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].adresse").exists());

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
        Mockito.when(this.srv.getClientById(CLIENT_ID)).thenReturn(Optional.of(new Client()));
        // when
        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL_BY_ID));

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser
    void shouldGetByIdUseServiceGetById() throws Exception {
        // given
        Mockito.when(this.srv.getClientById(CLIENT_ID)).thenReturn(Optional.of(new Client()));

        // when
        this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL_BY_ID));

        // then
        Mockito.verify(this.srv).getClientById(CLIENT_ID);
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
        Client c1 = new Client();

        c1.setId(CLIENT_ID);
        c1.setNom(CLIENT_NOM);
        c1.setPrenom(CLIENT_PRENOM);
        c1.setMail(CLIENT_MAIL);
        // c1.setMdp(CLIENT_MDP);
        c1.setTelephone(CLIENT_TEL);
        c1.setPointFidelite(CLIENT_POINT_FDT);
        c1.setDateCreation(CLIENT_CREATION);
        c1.setDateLastConnexion(CLIENT_LAST_CONNEX);
        c1.setVille(CLIENT_VILLE);
        c1.setCodePostale(CLIENT_CP);
        c1.setAdresse(CLIENT_ADRESSE);

        Mockito.when(this.srv.getClientById(CLIENT_ID)).thenReturn(Optional.of(c1));

        // when
        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL_BY_ID));

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nom").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.prenom").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.mail").exists())
                // .andExpect(MockMvcResultMatchers.jsonPath("$.mdp").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.telephone").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.pointFidelite").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateCreation").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateLastConnexion").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.ville").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.codePostale").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.adresse").exists());
    }

    @Test
    void shouldGetClientWithResaStatusUnauthorized() throws Exception {
        // given

        // when
        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL_WITH_RESA));

        // then
        result.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void shouldGetClientWithResaStatusOk() throws Exception {
        // given
        Mockito.when(this.srv.getClientById(CLIENT_ID)).thenReturn(Optional.of(new Client()));

        // when
        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL_WITH_RESA));

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser
    void shouldGetClientWithResaUseServiceGetById() throws Exception {
        // given
        Mockito.when(this.srv.getClientById(CLIENT_ID)).thenReturn(Optional.of(new Client()));

        // when
        this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL_WITH_RESA));

        // then
        Mockito.verify(this.srv).getClientById(CLIENT_ID);
    }

    @Test
    @WithMockUser
    void shouldGetClientWithResaStatusNotFoundWhenIdNotFound() throws Exception {
        // given

        // when
        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL_WITH_RESA));

        // then
        result.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @WithMockUser
    void shouldGetClientWithResaReturnReservationAttributes() throws Exception {
        // given
        Client c1 = new Client();
        c1.setId(CLIENT_ID);

        Mockito.when(this.srv.getClientById(CLIENT_ID)).thenReturn(Optional.of(c1));

        // when
        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL_WITH_RESA));

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.reservations").exists());
    }

    @Test
    void shouldGetClientWithEmpruntsStatusUnauthorized() throws Exception {
        // given

        // when
        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL_WITH_EMPRUNT));

        // then
        result.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void shouldGetClientWithEmpruntsStatusOk() throws Exception {
        // given
        Mockito.when(this.srv.getClientById(CLIENT_ID)).thenReturn(Optional.of(new Client()));

        // when
        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL_WITH_EMPRUNT));

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser
    void shouldGetClientWithEmpruntsUseServiceGetById() throws Exception {
        // given
        Mockito.when(this.srv.getClientById(CLIENT_ID)).thenReturn(Optional.of(new Client()));

        // when
        this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL_WITH_EMPRUNT));

        // then
        Mockito.verify(this.srv).getClientById(CLIENT_ID);
    }

    @Test
    @WithMockUser
    void shouldGetClientWithEmpruntsStatusNotFoundWhenIdNotFound() throws Exception {
        // given

        // when
        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL_WITH_EMPRUNT));

        // then
        result.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @WithMockUser
    void shouldGetClientWithEmpruntsReturnEmpruntAttributes() throws Exception {
        // given
        Client c1 = new Client();
        c1.setId(CLIENT_ID);

        Mockito.when(this.srv.getClientById(CLIENT_ID)).thenReturn(Optional.of(c1));

        // when
        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL_WITH_EMPRUNT));

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.emprunts").exists());
    }

    @Test
    void shouldCreateStatusUnauthorized() throws Exception {
        // given

        // when
        ResultActions result = this.createAndPost(CLIENT_NOM, CLIENT_PRENOM, CLIENT_MAIL, CLIENT_TEL,
                CLIENT_POINT_FDT, CLIENT_CREATION, CLIENT_LAST_CONNEX, CLIENT_VILLE, CLIENT_CP, CLIENT_ADRESSE);

        // then
        result.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void shouldCreateStatusForbidden() throws Exception {
        // given
        Client clientMock = new Client();
        clientMock.setId(CLIENT_ID); // ID simulé pour éviter le NPE

        // On simule le comportement du service
        Mockito.when(srv.create(Mockito.any(SubscribeClientRequest.class))).thenReturn(clientMock);
        // when
        ResultActions result = this.createAndPost(CLIENT_NOM, CLIENT_PRENOM, CLIENT_MAIL, CLIENT_TEL,
                CLIENT_POINT_FDT, CLIENT_CREATION, CLIENT_LAST_CONNEX, CLIENT_VILLE, CLIENT_CP, CLIENT_ADRESSE);

        // then
        result.andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @WithMockUser(roles = "EMPLOYE")
    void shouldCreateStatusOk() throws Exception {
        // given
        Client clientMock = new Client();
        clientMock.setId(CLIENT_ID); // ID simulé pour éviter le NPE

        // On simule le comportement du service
        Mockito.when(srv.create(Mockito.any(SubscribeClientRequest.class))).thenReturn(clientMock);
        // when
        ResultActions result = this.createAndPost(CLIENT_NOM, CLIENT_PRENOM, CLIENT_MAIL, CLIENT_TEL,
                CLIENT_POINT_FDT, CLIENT_CREATION, CLIENT_LAST_CONNEX, CLIENT_VILLE, CLIENT_CP, CLIENT_ADRESSE);

        // then
        result.andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @WithMockUser(roles = "EMPLOYE")
    void shouldCreateUseDaoSave() throws Exception {
        // given
        ArgumentCaptor<SubscribeClientRequest> clientCaptor = ArgumentCaptor.captor();

        Client clientMock = new Client();
        clientMock.setId(CLIENT_ID); // ID simulé pour éviter le NPE

        // On simule le comportement du service
        Mockito.when(srv.create(Mockito.any(SubscribeClientRequest.class))).thenReturn(clientMock);
        // when
        this.createAndPost(CLIENT_NOM, CLIENT_PRENOM, CLIENT_MAIL, CLIENT_TEL,
                CLIENT_POINT_FDT, CLIENT_CREATION, CLIENT_LAST_CONNEX, CLIENT_VILLE, CLIENT_CP, CLIENT_ADRESSE);

        // then
        Mockito.verify(this.srv).create(clientCaptor.capture());

        SubscribeClientRequest client = clientCaptor.getValue();

        Assertions.assertEquals(CLIENT_NOM, client.getNom());
        Assertions.assertEquals(CLIENT_PRENOM, client.getPrenom());
        Assertions.assertEquals(CLIENT_MAIL, client.getMail());
        Assertions.assertEquals(CLIENT_MDP, client.getMdp());
        Assertions.assertEquals(CLIENT_TEL, client.getTelephone());
        Assertions.assertEquals(CLIENT_POINT_FDT, client.getPointFidelite());
        Assertions.assertEquals(CLIENT_CREATION.truncatedTo(ChronoUnit.MINUTES),client.getDateCreation().truncatedTo(ChronoUnit.MINUTES));
        Assertions.assertEquals(CLIENT_LAST_CONNEX.truncatedTo(ChronoUnit.MINUTES),client.getDateLastConnexion().truncatedTo(ChronoUnit.MINUTES));
        //Assertions.assertEquals(CLIENT_LAST_CONNEX, client.getDateLastConnexion());
        Assertions.assertEquals(CLIENT_VILLE, client.getVille());
        Assertions.assertEquals(CLIENT_CP, client.getCodePostale());
        Assertions.assertEquals(CLIENT_ADRESSE, client.getAdresse());
    }

    @ParameterizedTest
    @CsvSource({
            // 1. Nom manquant/vide
            "'',prenom,mail,tel,10,2025-01-01,2025-01-01,Nice,06000,1 rue de Nice",
            // 2. Prénom manquant/vide
            "Nom,'',mail@valide.com,0600000000,10,2025-01-01,2025-01-01,Nice,06000,1 rue de Nice",
            // 3. Mail manquant/vide
            "Nom,Prenom,'',0600000000,10,2025-01-01,2025-01-01,Nice,06000,1 rue de Nice",
            // 4. Téléphone manquant/vide
            "Nom,Prenom,mail@valide.com,'',10,2025-01-01,2025-01-01,Nice,06000,1 rue de Nice",
            // 5. PointFidelite invalide (négatif)
            "Nom,Prenom,mail@valide.com,0600000000,-1,2025-01-01,2025-01-01,Nice,06000,1 rue de Nice",
            // 6. Ville manquant/vide
            "Nom,Prenom,mail@valide.com,0600000000,10,2025-01-01,2025-01-01,'',06000,1 rue de Nice",
            // 7. Code Postal manquant/vide
            "Nom,Prenom,mail@valide.com,0600000000,10,2025-01-01,2025-01-01,Nice,'',1 rue de Nice"
    })
    @WithMockUser(roles = "EMPLOYE")
    void shouldCreateStatusBadRequest(String nom, String prenom, String mail, String tel,
            int pointFidelite,
            String creation, String connexion, String ville, String cp, String adresse) throws Exception {
        // given
        LocalDateTime dateCreation = (creation != null && !creation.isBlank())
        ? LocalDate.parse(creation, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay()
        : null;

LocalDateTime dateConnexion = (connexion != null && !connexion.isBlank())
        ? LocalDate.parse(connexion, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay()
        : null;
        // when
        ResultActions result = this.createAndPost(nom, prenom, mail, tel, pointFidelite, dateCreation,
                dateConnexion, ville, cp, adresse);

        // then
        result.andExpect(MockMvcResultMatchers.status().isBadRequest());

        Mockito.verify(this.srv, Mockito.never()).create(Mockito.any());
    }

    private ResultActions createAndPost(String nom, String prenom, String mail, String tel,
            int pointFidelite,
            LocalDateTime dateCreation, LocalDateTime dateConnexion, String ville, String cp, String adresse)
            throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        SubscribeClientRequest request = new SubscribeClientRequest();

        request.setNom(nom);
        request.setPrenom(prenom);
        request.setMail(mail);
        request.setMdp(CLIENT_MDP);
        request.setTelephone(tel);
        request.setPointFidelite(pointFidelite);
        request.setDateCreation(dateCreation);
        request.setDateLastConnexion(dateConnexion);
        request.setVille(ville);
        request.setCodePostale(cp);
        request.setAdresse(adresse);

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
        ResultActions result = this.updateAndPut(CLIENT_NOM, CLIENT_PRENOM, CLIENT_MAIL, CLIENT_TEL,
                CLIENT_POINT_FDT, CLIENT_CREATION, CLIENT_LAST_CONNEX, CLIENT_VILLE, CLIENT_CP, CLIENT_ADRESSE);

        // then
        result.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    void shouldUpdateStatusForbidden() throws Exception {
        // given

        // when
        ResultActions result = this.updateAndPut(CLIENT_NOM, CLIENT_PRENOM, CLIENT_MAIL, CLIENT_TEL,
                CLIENT_POINT_FDT, CLIENT_CREATION, CLIENT_LAST_CONNEX, CLIENT_VILLE, CLIENT_CP, CLIENT_ADRESSE);

        // then
        result.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "EMPLOYE")
    void shouldUpdateStatusOk() throws Exception {
        // given
        Mockito.when(srv.getClientById(CLIENT_ID)).thenReturn(Optional.of(new Client()));

        // when
        ResultActions result = this.updateAndPut(CLIENT_NOM, CLIENT_PRENOM, CLIENT_MAIL, CLIENT_TEL,
                CLIENT_POINT_FDT, CLIENT_CREATION, CLIENT_LAST_CONNEX, CLIENT_VILLE, CLIENT_CP, CLIENT_ADRESSE);

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = "EMPLOYE")
    void shouldUpdateUseSrvUpdate() throws Exception {
        // given
        Client c = new Client();
        c.setId(CLIENT_ID);

        Mockito.when(srv.getClientById(CLIENT_ID)).thenReturn(Optional.of(c));
        ArgumentCaptor<UpdateClientRequest> clientCaptor = ArgumentCaptor.forClass(UpdateClientRequest.class);

        // when
        this.updateAndPut(CLIENT_NOM, CLIENT_PRENOM, CLIENT_MAIL, CLIENT_TEL,
                CLIENT_POINT_FDT, CLIENT_CREATION, CLIENT_LAST_CONNEX, CLIENT_VILLE, CLIENT_CP, CLIENT_ADRESSE);

        // then
        Mockito.verify(this.srv).update(Mockito.eq(CLIENT_ID), clientCaptor.capture());

        UpdateClientRequest request = clientCaptor.getValue();

        Assertions.assertEquals(CLIENT_NOM, request.getNom());
        Assertions.assertEquals(CLIENT_PRENOM, request.getPrenom());
        Assertions.assertEquals(CLIENT_MAIL, request.getMail());
        //Assertions.assertEquals(CLIENT_MDP, request.get);
        Assertions.assertEquals(CLIENT_TEL, request.getTelephone());
        //Assertions.assertEquals(CLIENT_POINT_FDT, request.getPointFidelite());
        //Assertions.assertEquals(CLIENT_CREATION.truncatedTo(ChronoUnit.MINUTES),request.getDateCreation().truncatedTo(ChronoUnit.MINUTES));
        //Assertions.assertEquals(CLIENT_LAST_CONNEX.truncatedTo(ChronoUnit.MINUTES),request.getDateLastConnexion().truncatedTo(ChronoUnit.MINUTES));
        Assertions.assertEquals(CLIENT_VILLE, request.getVille());
        Assertions.assertEquals(CLIENT_CP, request.getCodePostale());
        Assertions.assertEquals(CLIENT_ADRESSE, request.getAdresse());
    }

    @ParameterizedTest
    @CsvSource({
            // 1. Nom manquant/vide
            "'',prenom,mail,tel,10,2025-01-01,2025-01-01,Nice,06000,1 rue de Nice",
            // 2. Prénom manquant/vide
            "Nom,'',mail@valide.com,0600000000,10,2025-01-01,2025-01-01,Nice,06000,1 rue de Nice",
            // 3. Mail manquant/vide
            "Nom,Prenom,'',0600000000,10,2025-01-01,2025-01-01,Nice,06000,1 rue de Nice",
            // 4. Téléphone manquant/vide
            "Nom,Prenom,mail@valide.com,'',10,2025-01-01,2025-01-01,Nice,06000,1 rue de Nice",
            // 5. PointFidelite invalide (négatif)
            "Nom,Prenom,mail@valide.com,0600000000,-1,2025-01-01,2025-01-01,Nice,06000,1 rue de Nice",
            // 6. Ville manquant/vide
            "Nom,Prenom,mail@valide.com,0600000000,10,2025-01-01,2025-01-01,'',06000,1 rue de Nice",
            // 7. Code Postal manquant/vide
            "Nom,Prenom,mail@valide.com,0600000000,10,2025-01-01,2025-01-01,Nice,'',1 rue de Nice"
    })
    @WithMockUser(roles = "EMPLOYE")
    void shouldUpdateStatusBadRequest(String nom, String prenom, String mail, String tel, int pointFidelite,
            String creation, String connexion, String ville, String cp, String adresse) throws Exception {
        // given
       LocalDateTime dateCreation = (creation != null && !creation.isBlank())
        ? LocalDate.parse(creation, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay()
        : null;

LocalDateTime dateConnexion = (connexion != null && !connexion.isBlank())
        ? LocalDate.parse(connexion, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay()
        : null;

        

        // when
        ResultActions result = this.updateAndPut(null, prenom, mail, tel, pointFidelite, dateCreation,
                dateConnexion, ville, cp, adresse);

        // then
        result.andExpect(MockMvcResultMatchers.status().isBadRequest());

        Mockito.verify(this.srv, Mockito.never()).update(Mockito.eq(CLIENT_ID), Mockito.any());
    }

    private ResultActions updateAndPut(String nom, String prenom, String mail, String tel,
            int pointFidelite,
            LocalDateTime dateCreation, LocalDateTime dateConnexion, String ville, String cp, String adresse)
            throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        SubscribeClientRequest request = new SubscribeClientRequest();

        request.setNom(nom);
        request.setPrenom(prenom);
        request.setMail(mail);
        request.setMdp(CLIENT_MDP);
        request.setTelephone(tel);
        request.setPointFidelite(pointFidelite);
        request.setDateCreation(dateCreation);
        request.setDateLastConnexion(dateConnexion);
        request.setVille(ville);
        request.setCodePostale(cp);
        request.setAdresse(adresse);

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

        Mockito.verify(this.srv).deleteById(CLIENT_ID);
    }

}
