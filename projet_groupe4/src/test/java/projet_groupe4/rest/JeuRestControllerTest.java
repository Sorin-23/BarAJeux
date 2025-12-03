package projet_groupe4.rest;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
import projet_groupe4.dto.request.JeuRequest;
import projet_groupe4.model.CategorieJeu;
import projet_groupe4.model.Jeu;
import projet_groupe4.model.TypeJeu;
import projet_groupe4.service.JeuService;
import projet_groupe4.service.PersonneService;

@WebMvcTest(JeuRestController.class)
public class JeuRestControllerTest {
    private static final int JEU_ID = 1;
    private static final String JEU_NOM = "Catan";
    private static final int JEU_AGE = 7;
    private static final int JEU_NBJ_MIN = 2;
    private static final int JEU_NBJ_MAX = 5;
    private static final int JEU_DUREE = 30;
    private static final int JEU_EXEMP = 2;
    private static final double JEU_NOTE = 4.0;
    private static final String JEU_IMG = "image";
    private static final boolean JEU_GAME_MASTER = true;
    private static final String API_URL = "/api/jeu";
    private static final String API_URL_BY_ID = API_URL + "/" + JEU_ID;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JeuService srv;

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
        Jeu j1 = new Jeu();

        j1.setId(JEU_ID);
        j1.setNom(JEU_NOM);
        j1.setAgeMinimum(JEU_AGE);
        j1.setNbJoueurMinimum(JEU_NBJ_MIN);
        j1.setNbJoueurMaximum(JEU_NBJ_MAX);
        j1.setDuree(JEU_DUREE);
        j1.setNbExemplaire(JEU_EXEMP);
        j1.setNote(JEU_NOTE);
        j1.setImgURL(JEU_IMG);
        j1.setBesoinGameMaster(JEU_GAME_MASTER);

        // On retourne la liste de EMPLOYE
        Mockito.when(srv.getAll()).thenReturn(List.of(j1));

        // --- WHEN ---
        ResultActions result = this.mockMvc.perform(
                MockMvcRequestBuilders.get(API_URL));

        // --- THEN ---

        result.andExpect(MockMvcResultMatchers.jsonPath("$[0].id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nom").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].ageMinimum").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nbJoueurMinimum").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nbJoueurMaximum").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].duree").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nbExemplaire").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].note").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].imgURL").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].besoinGameMaster").exists());

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
        Mockito.when(this.srv.getById(JEU_ID)).thenReturn(Optional.of(new Jeu()));
        // when
        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL_BY_ID));

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser
    void shouldGetByIdUseServiceGetById() throws Exception {
        // given
        Mockito.when(this.srv.getById(JEU_ID)).thenReturn(Optional.of(new Jeu()));

        // when
        this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL_BY_ID));

        // then
        Mockito.verify(this.srv).getById(JEU_ID);
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
        Jeu j1 = new Jeu();

        j1.setId(JEU_ID);
        j1.setNom(JEU_NOM);
        j1.setAgeMinimum(JEU_AGE);
        j1.setNbJoueurMinimum(JEU_NBJ_MIN);
        j1.setNbJoueurMaximum(JEU_NBJ_MAX);
        j1.setDuree(JEU_DUREE);
        j1.setNbExemplaire(JEU_EXEMP);
        j1.setNote(JEU_NOTE);
        j1.setImgURL(JEU_IMG);
        j1.setBesoinGameMaster(JEU_GAME_MASTER);

        Mockito.when(this.srv.getById(JEU_ID)).thenReturn(Optional.of(j1));

        // when
        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL_BY_ID));

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nom").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.ageMinimum").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nbJoueurMinimum").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nbJoueurMaximum").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.duree").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nbExemplaire").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.note").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.imgURL").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.besoinGameMaster").exists());

    }

    @Test
    void shouldCreateStatusUnauthorized() throws Exception {
        // given

        // when
        ResultActions result = this.createAndPost(JEU_NOM, JEU_AGE, JEU_NBJ_MIN, JEU_NBJ_MAX,
                JEU_DUREE, JEU_EXEMP, JEU_NOTE, JEU_IMG, JEU_GAME_MASTER);

        // then
        result.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    void shouldCreateStatusForbidden() throws Exception {
        // given
        Jeu jeuMock = new Jeu();
        jeuMock.setId(JEU_ID); // ID simulé pour éviter le NPE

        // On simule le comportement du service
        Mockito.when(srv.create(Mockito.any(JeuRequest.class))).thenReturn(jeuMock);
        // when
        ResultActions result = this.createAndPost(JEU_NOM, JEU_AGE, JEU_NBJ_MIN, JEU_NBJ_MAX,
                JEU_DUREE, JEU_EXEMP, JEU_NOTE, JEU_IMG, JEU_GAME_MASTER);

        // then
        result.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "EMPLOYE")
    void shouldCreateStatusOk() throws Exception {
        // given
        Jeu jeuMock = new Jeu();
        jeuMock.setId(JEU_ID); // ID simulé pour éviter le NPE

        // On simule le comportement du service
        Mockito.when(srv.create(Mockito.any(JeuRequest.class))).thenReturn(jeuMock);
        // when
        ResultActions result = this.createAndPost(JEU_NOM, JEU_AGE, JEU_NBJ_MIN, JEU_NBJ_MAX,
                JEU_DUREE, JEU_EXEMP, JEU_NOTE, JEU_IMG, JEU_GAME_MASTER);

        // then
        result.andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @WithMockUser(roles = "EMPLOYE")
    void shouldCreateUseDaoSave() throws Exception {
        // given
        ArgumentCaptor<JeuRequest> jeuCaptor = ArgumentCaptor.captor();

        Jeu jeuMock = new Jeu();
        jeuMock.setId(JEU_ID); // ID simulé pour éviter le NPE

        // On simule le comportement du service
        Mockito.when(srv.create(Mockito.any(JeuRequest.class))).thenReturn(jeuMock);
        // when
        this.createAndPost(JEU_NOM, JEU_AGE, JEU_NBJ_MIN, JEU_NBJ_MAX,
                JEU_DUREE, JEU_EXEMP, JEU_NOTE, JEU_IMG, JEU_GAME_MASTER);
        // then
        Mockito.verify(this.srv).create(jeuCaptor.capture());

        JeuRequest jeu = jeuCaptor.getValue();

        Assertions.assertEquals(JEU_NOM, jeu.getNom());
        Assertions.assertEquals(JEU_AGE, jeu.getAgeMinimum());
        Assertions.assertEquals(JEU_NBJ_MIN, jeu.getNbJoueurMinimum());
        Assertions.assertEquals(JEU_NBJ_MAX, jeu.getNbJoueurMaximum());
        Assertions.assertEquals(JEU_DUREE, jeu.getDuree());
        Assertions.assertEquals(JEU_EXEMP, jeu.getNbExemplaire());
        Assertions.assertEquals(JEU_NOTE, jeu.getNote());
        Assertions.assertEquals(JEU_IMG, jeu.getImgURL());
        Assertions.assertEquals(JEU_GAME_MASTER, jeu.isBesoinGameMaster());
    }

    @ParameterizedTest
    @CsvSource({
            // 1. Nom manquant/vide (@NotBlank)
            "'', 7, 1, 5, 30, 2, 5.0, image, true",
            // 2. Nom avec seulement des espaces (@NotBlank)
            "'  ', 7, 1, 5, 30, 2, 5.0, image, true",
            // 3. Nom null (teste les limites de @NotBlank)
            ", 7, 1, 5, 30, 2, 5.0, image, true",

            // 4. Age Minimum invalide (@Min(0))
            "Catan, -1, 1, 5, 30, 2, 5.0, image, true",

            // 5. Nb Joueur Minimum invalide (@Min(1))
            "Catan, 7, 0, 5, 30, 2, 5.0, image, true",

            // 6. Durée invalide (@Min(1))
            "Catan, 7, 1, 5, 0, 2, 5.0, image, true",

            // 7. Nb Exemplaire invalide (@Min(0)) - (Exemple de test)
            "Catan, 7, 1, 5, 30, -1, 5.0, image, true",

            // 8. Note invalide (trop basse) (@DecimalMin("0.0"))
            "Catan, 7, 1, 5, 30, 2, -0.1, image, true",

            // 9. Note invalide (trop haute) (@DecimalMax("5.0"))
            "Catan, 7, 1, 5, 30, 2, 5.1, image, true",
    })
    @WithMockUser(roles = "EMPLOYE")
    void shouldCreateStatusBadRequest(String nom, int age, int nbjMin, int nbjMax, int duree,
            int exemp, double note, String img, boolean gameMaster)
            throws Exception {
        // given

        // when
        ResultActions result = this.createAndPost(nom, age, nbjMin, nbjMax, duree, exemp, note, img,
                gameMaster);

        // then
        result.andExpect(MockMvcResultMatchers.status().isBadRequest());

        Mockito.verify(this.srv, Mockito.never()).create(Mockito.any());
    }

    private ResultActions createAndPost(String nom, int age, int nbjMin, int nbjMax, int duree,
            int exemp, double note, String img, boolean gameMaster) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JeuRequest request = new JeuRequest();

        request.setNom(nom);
        request.setAgeMinimum(age);
        request.setNbJoueurMinimum(nbjMin);
        request.setNbJoueurMaximum(nbjMax);
        request.setDuree(duree);
        request.setNbExemplaire(exemp);
        request.setNote(note);
        request.setImgURL(img);
        request.setBesoinGameMaster(gameMaster);
        request.setTypesJeux(Set.of(TypeJeu.CARTES));
        request.setCategoriesJeux(Set.of(CategorieJeu.AMBIANCE));

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
        ResultActions result = this.updateAndPut(JEU_NOM, JEU_AGE, JEU_NBJ_MIN, JEU_NBJ_MAX,
                JEU_DUREE, JEU_EXEMP, JEU_NOTE, JEU_IMG, JEU_GAME_MASTER);

        // then
        result.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    void shouldUpdateStatusForbidden() throws Exception {
        // given

        // when
        ResultActions result = this.updateAndPut(JEU_NOM, JEU_AGE, JEU_NBJ_MIN, JEU_NBJ_MAX,
                JEU_DUREE, JEU_EXEMP, JEU_NOTE, JEU_IMG, JEU_GAME_MASTER);

        // then
        result.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "EMPLOYE")
    void shouldUpdateStatusOk() throws Exception {
        // given
        Mockito.when(srv.getById(JEU_ID)).thenReturn(Optional.of(new Jeu()));

        // when
        ResultActions result = this.updateAndPut(JEU_NOM, JEU_AGE, JEU_NBJ_MIN, JEU_NBJ_MAX,
                JEU_DUREE, JEU_EXEMP, JEU_NOTE, JEU_IMG, JEU_GAME_MASTER);

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = "EMPLOYE")
    void shouldUpdateUseSrvUpdate() throws Exception {
        // given
        Jeu j = new Jeu();
        j.setId(JEU_ID);

        Mockito.when(srv.getById(JEU_ID)).thenReturn(Optional.of(j));
        ArgumentCaptor<JeuRequest> jeuCaptor = ArgumentCaptor.captor();

        // when
        this.updateAndPut(JEU_NOM, JEU_AGE, JEU_NBJ_MIN, JEU_NBJ_MAX,
                JEU_DUREE, JEU_EXEMP, JEU_NOTE, JEU_IMG, JEU_GAME_MASTER);

        // then
        Mockito.verify(this.srv).update(Mockito.eq(JEU_ID), jeuCaptor.capture());

        JeuRequest jeu = jeuCaptor.getValue();

        Assertions.assertEquals(JEU_NOM, jeu.getNom());
        Assertions.assertEquals(JEU_AGE, jeu.getAgeMinimum());
        Assertions.assertEquals(JEU_NBJ_MIN, jeu.getNbJoueurMinimum());
        Assertions.assertEquals(JEU_NBJ_MAX, jeu.getNbJoueurMaximum());
        Assertions.assertEquals(JEU_DUREE, jeu.getDuree());
        Assertions.assertEquals(JEU_EXEMP, jeu.getNbExemplaire());
        Assertions.assertEquals(JEU_NOTE, jeu.getNote());
        Assertions.assertEquals(JEU_IMG, jeu.getImgURL());
        Assertions.assertEquals(JEU_GAME_MASTER, jeu.isBesoinGameMaster());

    }

    @ParameterizedTest
    @CsvSource({
            // 1. Nom manquant/vide (@NotBlank)
            "'', 7, 1, 5, 30, 2, 5.0, image, true",
            // 2. Nom avec seulement des espaces (@NotBlank)
            "'  ', 7, 1, 5, 30, 2, 5.0, image, true",
            // 3. Nom null (teste les limites de @NotBlank)
            ", 7, 1, 5, 30, 2, 5.0, image, true",

            // 4. Age Minimum invalide (@Min(0))
            "Catan, -1, 1, 5, 30, 2, 5.0, image, true",

            // 5. Nb Joueur Minimum invalide (@Min(1))
            "Catan, 7, 0, 5, 30, 2, 5.0, image, true",

            // 6. Durée invalide (@Min(1))
            "Catan, 7, 1, 5, 0, 2, 5.0, image, true",

            // 7. Nb Exemplaire invalide (@Min(0)) - (Exemple de test)
            "Catan, 7, 1, 5, 30, -1, 5.0, image, true",

            // 8. Note invalide (trop basse) (@DecimalMin("0.0"))
            "Catan, 7, 1, 5, 30, 2, -0.1, image, true",

            // 9. Note invalide (trop haute) (@DecimalMax("5.0"))
            "Catan, 7, 1, 5, 30, 2, 5.1, image, true",
    })
    @WithMockUser(roles = "EMPLOYE")
    void shouldUpdateStatusBadRequest(String nom, int age, int nbjMin, int nbjMax, int duree,
            int exemp, double note, String img, boolean gameMaster)
            throws Exception {
        // given
        // when
        ResultActions result = this.updateAndPut(nom, age, nbjMin, nbjMax, duree, exemp, note, img,
                gameMaster);

        // then
        result.andExpect(MockMvcResultMatchers.status().isBadRequest());

        Mockito.verify(this.srv, Mockito.never()).update(Mockito.eq(JEU_ID), Mockito.any());
    }

    private ResultActions updateAndPut(String nom, int age, int nbjMin, int nbjMax, int duree,
            int exemp, double note, String img, boolean gameMaster) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JeuRequest request = new JeuRequest();

        request.setNom(nom);
        request.setAgeMinimum(age);
        request.setNbJoueurMinimum(nbjMin);
        request.setNbJoueurMaximum(nbjMax);
        request.setDuree(duree);
        request.setNbExemplaire(exemp);
        request.setNote(note);
        request.setImgURL(img);
        request.setBesoinGameMaster(gameMaster);
        request.setTypesJeux(Set.of(TypeJeu.CARTES));
        request.setCategoriesJeux(Set.of(CategorieJeu.AMBIANCE));

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

        Mockito.verify(this.srv).deleteById(JEU_ID);
    }

}
