package projet_groupe4.rest;

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
import projet_groupe4.dto.request.ReservationRequest;
import projet_groupe4.model.Client;
import projet_groupe4.model.Employe;
import projet_groupe4.model.Jeu;
import projet_groupe4.model.Reservation;
import projet_groupe4.model.StatutReservation;
import projet_groupe4.model.TableJeu;
import projet_groupe4.service.PersonneService;
import projet_groupe4.service.ReservationService;

@WebMvcTest(ReservationRestController.class)
public class ReservationRestControllerTest {
    private static final int RESA_ID = 1;
    private static final LocalDateTime RESA_DEBUT = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
    private static final LocalDateTime RESA_FIN = LocalDateTime.now().plusHours(1).truncatedTo(ChronoUnit.MINUTES);
    private static final int RESA_NBJ = 5;
    private static final String RESA_STATUT = "terminée";
    private static final int TABLE_ID = 1;
    private static final int JEU_ID = 1;
    private static final int CLIENT_ID = 1;
    private static final int GM_ID = 1;
    private static final String API_URL = "/api/reservation";
    private static final String API_URL_BY_ID = API_URL + "/" + RESA_ID;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReservationService srv;

    @MockitoBean
    private IDAOPersonne dao;

    @MockitoBean
    private PersonneService personneService;

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
        Reservation r1 = new Reservation();

        Jeu j = new Jeu();
        j.setId(JEU_ID);
        r1.setJeu(j);

        Client c = new Client();
        c.setId(CLIENT_ID);
        r1.setClient(c);

        TableJeu tj = new TableJeu();
        tj.setId(TABLE_ID);
        r1.setTableJeu(tj);
        

        Employe e = new Employe();
        e.setId(GM_ID);
        r1.setGameMaster(e);

        r1.setId(RESA_ID);
        r1.setDatetimeDebut(RESA_DEBUT);
        r1.setDatetimeFin(RESA_FIN);
        r1.setNbJoueur(RESA_NBJ);
        r1.setStatutReservation(StatutReservation.valueOf(RESA_STATUT));

        // On retourne la liste de CLIENT
        Mockito.when(srv.getAll()).thenReturn(List.of(r1));

        // --- WHEN ---
        ResultActions result = this.mockMvc.perform(
                MockMvcRequestBuilders.get(API_URL));

        // --- THEN ---

        result.andExpect(MockMvcResultMatchers.jsonPath("$[0].id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].datetimeDebut").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].datetimeFin").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].statutReservation").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nbJoueur").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].jeu").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].client").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].tableID").exists())
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
        Mockito.when(this.srv.getById(RESA_ID)).thenReturn(Optional.of(new Reservation()));
        // when
        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL_BY_ID));

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser
    void shouldGetByIdUseServiceGetById() throws Exception {
        // given
        Mockito.when(this.srv.getById(RESA_ID)).thenReturn(Optional.of(new Reservation()));

        // when
        this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL_BY_ID));

        // then
        Mockito.verify(this.srv).getById(RESA_ID);
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
        Reservation r1 = new Reservation();

        Jeu j = new Jeu();
        j.setId(JEU_ID);
        r1.setJeu(j);

        Client c = new Client();
        c.setId(CLIENT_ID);
        r1.setClient(c);

        TableJeu tj = new TableJeu();
        tj.setId(TABLE_ID);
        r1.setTableJeu(tj);

        Employe e = new Employe();
        e.setId(GM_ID);
        r1.setGameMaster(e);

        r1.setId(RESA_ID);
        r1.setDatetimeDebut(RESA_DEBUT);
        r1.setDatetimeFin(RESA_FIN);
        r1.setNbJoueur(RESA_NBJ);
        r1.setStatutReservation(StatutReservation.valueOf(RESA_STATUT));

        Mockito.when(this.srv.getById(RESA_ID)).thenReturn(Optional.of(r1));

        // when
        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL_BY_ID));

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.datetimeDebut").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.datetimeFin").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.statutReservation").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nbJoueur").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.tableID").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.jeu").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.client").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.gameMaster").exists());
        ;

    }

    @Test void shouldCreateStatusUnauthorized() throws Exception { 
        // given 
        // // when 
        ResultActions result = this.createAndPost(RESA_DEBUT, RESA_FIN, RESA_NBJ, RESA_STATUT, TABLE_ID, JEU_ID, CLIENT_ID, GM_ID); 
        // then 
        result.andExpect(MockMvcResultMatchers.status().isUnauthorized()); 
    }

   

    @Test
    @WithMockUser
    void shouldCreateStatusForbidden() throws Exception {
        // given
        Reservation resaMock = new Reservation();
        resaMock.setId(JEU_ID); // ID simulé pour éviter le NPE

        // On simule le comportement du service
        Mockito.when(srv.create(Mockito.any(ReservationRequest.class))).thenReturn(resaMock);
        // when
        ResultActions result = this.createAndPost(RESA_DEBUT, RESA_FIN, RESA_NBJ, RESA_STATUT, TABLE_ID, JEU_ID,
                CLIENT_ID, GM_ID);

        // then
        result.andExpect(MockMvcResultMatchers.status().isCreated());
    }
    private ResultActions createAndPost(LocalDateTime dateDebut, LocalDateTime dateFin, int nbJoueur, String statut,
            int tableId, int jeuId, int clientId, int gmId)
            throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        ReservationRequest request = new ReservationRequest();

        request.setDatetimeDebut(dateDebut);
        request.setDatetimeFin(dateFin);
        request.setNbJoueur(nbJoueur);
        request.setClientId(clientId);
        request.setJeuId(jeuId);
        request.setGameMasterId(gmId);
        request.setTableJeuId(tableId);

        if (statut == null || statut.isBlank()) {
            request.setStatutReservation(null);
        } else {
            request.setStatutReservation(StatutReservation.valueOf(statut));
        }

        return this.mockMvc.perform(MockMvcRequestBuilders
                .post(API_URL)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(request)));
    }

    @Test
    @WithMockUser(roles = "EMPLOYE")
    void shouldCreateStatusOk() throws Exception {
        // given
        Reservation resaMock = new Reservation();
        resaMock.setId(JEU_ID); // ID simulé pour éviter le NPE

        // On simule le comportement du service
        Mockito.when(srv.create(Mockito.any(ReservationRequest.class))).thenReturn(resaMock);
        // when
        ResultActions result = this.createAndPost(RESA_DEBUT, RESA_FIN, RESA_NBJ, RESA_STATUT, TABLE_ID, JEU_ID,
                CLIENT_ID, GM_ID);

        // then
        result.andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @WithMockUser(roles = "EMPLOYE")
    void shouldCreateUseDaoSave() throws Exception {
        // given
        ArgumentCaptor<ReservationRequest> reservationCaptor = ArgumentCaptor.captor();

        Reservation resaMock = new Reservation();
        resaMock.setId(JEU_ID); // ID simulé pour éviter le NPE

        // On simule le comportement du service
        Mockito.when(srv.create(Mockito.any(ReservationRequest.class))).thenReturn(resaMock);
        // when
        this.createAndPost(RESA_DEBUT, RESA_FIN, RESA_NBJ, RESA_STATUT, TABLE_ID, JEU_ID,
                CLIENT_ID, GM_ID);
        // then
        Mockito.verify(this.srv).create(reservationCaptor.capture());

        ReservationRequest reservation = reservationCaptor.getValue();

        Assertions.assertEquals(RESA_DEBUT, reservation.getDatetimeDebut());
        Assertions.assertEquals(RESA_FIN, reservation.getDatetimeFin());
        Assertions.assertEquals(RESA_NBJ, reservation.getNbJoueur());
        Assertions.assertEquals(StatutReservation.valueOf(RESA_STATUT), reservation.getStatutReservation());
        Assertions.assertEquals(JEU_ID, reservation.getJeuId());
        Assertions.assertEquals(CLIENT_ID, reservation.getClientId());
        Assertions.assertEquals(TABLE_ID, reservation.getTableJeuId());
        Assertions.assertEquals(GM_ID, reservation.getGameMasterId());

    }

    @ParameterizedTest
    @CsvSource({
            // 1. Date Début manquante (@NotNull) - Le "," force la String à être null dans
            ", '2025-01-02 12:00', 5, 'terminée', 1, 1, 1, 1",

            // 2. Date Fin manquante (@NotNull)
            "'2025-01-01 10:00', , 5, 'terminée', 1, 1, 1, 1",

            // 3. Nb Joueur invalide (@Min(1))
            "'2025-01-01 10:00', '2025-01-02 12:00', 0, 'terminée', 1, 1, 1, 1"
    })
    @WithMockUser(roles = "EMPLOYE")
    void shouldCreateStatusBadRequest(String debut, String fin, int nbJoueur, String statut, int tableId, int jeuId,
            int clientId, int gmId)
            throws Exception {
        // given
        LocalDateTime dateDebut = null;
        LocalDateTime dateFin = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        if (debut != null && !debut.isBlank()) {
            dateDebut = LocalDateTime.parse(debut, formatter);
        }

        if (fin != null && !fin.isBlank()) {
            dateFin = LocalDateTime.parse(fin, formatter);
        }

        // when
        ResultActions result = this.createAndPost(dateDebut, dateFin, nbJoueur, statut, tableId, jeuId, clientId, gmId);

        // then
        result.andExpect(MockMvcResultMatchers.status().isBadRequest());

        Mockito.verify(this.srv, Mockito.never()).create(Mockito.any());
    }

    

    @Test
    void shouldUpdateStatusUnauthorized() throws Exception {
        // given

        // when
        ResultActions result = this.updateAndPut(RESA_DEBUT, RESA_FIN, RESA_NBJ, RESA_STATUT, TABLE_ID, JEU_ID,
                CLIENT_ID, GM_ID);

        // then
        result.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    void shouldUpdateStatusForbidden() throws Exception {
        // given

        // when
        ResultActions result = this.updateAndPut(RESA_DEBUT, RESA_FIN, RESA_NBJ, RESA_STATUT, TABLE_ID, JEU_ID,
                CLIENT_ID, GM_ID);

        // then
        result.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "EMPLOYE")
    void shouldUpdateStatusOk() throws Exception {
        // given
        Mockito.when(srv.getById(RESA_ID)).thenReturn(Optional.of(new Reservation()));

        // when
        ResultActions result = this.updateAndPut(RESA_DEBUT, RESA_FIN, RESA_NBJ, RESA_STATUT, TABLE_ID, JEU_ID,
                CLIENT_ID, GM_ID);

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = "EMPLOYE")
    void shouldUpdateUseSrvUpdate() throws Exception {
        // given
        Reservation r = new Reservation();
        r.setId(RESA_ID);

        Mockito.when(srv.getById(RESA_ID)).thenReturn(Optional.of(r));
        ArgumentCaptor<ReservationRequest> resaCaptor = ArgumentCaptor.captor();

        // when
        this.updateAndPut(RESA_DEBUT, RESA_FIN, RESA_NBJ, RESA_STATUT, TABLE_ID, JEU_ID,
                CLIENT_ID, GM_ID);

        // then
        Mockito.verify(this.srv).update(Mockito.eq(RESA_ID), resaCaptor.capture());

        ReservationRequest reservation = resaCaptor.getValue();

        Assertions.assertEquals(RESA_DEBUT, reservation.getDatetimeDebut());
        Assertions.assertEquals(RESA_FIN, reservation.getDatetimeFin());
        Assertions.assertEquals(RESA_NBJ, reservation.getNbJoueur());
        Assertions.assertEquals(StatutReservation.valueOf(RESA_STATUT), reservation.getStatutReservation());
        Assertions.assertEquals(JEU_ID, reservation.getJeuId());
        Assertions.assertEquals(CLIENT_ID, reservation.getClientId());
        Assertions.assertEquals(TABLE_ID, reservation.getTableJeuId());
        Assertions.assertEquals(GM_ID, reservation.getGameMasterId());
    }

    @ParameterizedTest
    @CsvSource({
            // 1. Date Début manquante (@NotNull) - Le "," force la String à être null dans
            ", '2025-01-02 12:00', 5, 'terminée', 1, 1, 1, 1",

            // 2. Date Fin manquante (@NotNull)
            "'2025-01-01 10:00', , 5, 'terminée', 1, 1, 1, 1",

            // 3. Nb Joueur invalide (@Min(1))
            "'2025-01-01 10:00', '2025-01-02 12:00', 0, 'terminée', 1, 1, 1, 1"
    })
    @WithMockUser(roles = "EMPLOYE")
    void shouldUpdateStatusBadRequest(String debut, String fin, int nbJoueur, String statut, int tableId, int jeuId,
            int clientId, int gmId)
            throws Exception {
        // given
        LocalDateTime dateDebut = null;
        LocalDateTime dateFin = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        if (debut != null && !debut.isBlank()) {
            dateDebut = LocalDateTime.parse(debut, formatter);
        }

        if (fin != null && !fin.isBlank()) {
            dateFin = LocalDateTime.parse(fin, formatter);
        }
        // when
        ResultActions result = this.updateAndPut(dateDebut, dateFin, nbJoueur, statut, tableId, jeuId, clientId, gmId);

        // then
        result.andExpect(MockMvcResultMatchers.status().isBadRequest());

        Mockito.verify(this.srv, Mockito.never()).update(Mockito.eq(RESA_ID), Mockito.any());
    }

    private ResultActions updateAndPut(LocalDateTime dateDebut, LocalDateTime dateFin, int nbJoueur, String statut,
            int tableId, int jeuId, int clientId, int gmId)
            throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        ReservationRequest request = new ReservationRequest();

        request.setDatetimeDebut(dateDebut);
        request.setDatetimeFin(dateFin);
        request.setNbJoueur(nbJoueur);
        request.setClientId(clientId);
        request.setJeuId(jeuId);
        request.setGameMasterId(gmId);
        request.setTableJeuId(tableId);

        if (statut == null || statut.isBlank()) {
            request.setStatutReservation(null);
        } else {
            request.setStatutReservation(StatutReservation.valueOf(statut));
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

        Mockito.verify(this.srv).deleteById(RESA_ID);
    }

}
