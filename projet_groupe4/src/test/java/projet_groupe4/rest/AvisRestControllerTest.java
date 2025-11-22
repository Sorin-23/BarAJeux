package projet_groupe4.rest;

import java.util.List;
import java.util.Optional;

import projet_groupe4.dao.IDAOPersonne;
import projet_groupe4.dto.request.AvisRequest;
import projet_groupe4.model.Avis;
import projet_groupe4.service.AvisService;

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

@WebMvcTest(AvisRestController.class)
public class AvisRestControllerTest {
	private static final int AVIS_ID = 1;
    private static final int AVIS_NOTE = 5;
    private static final String AVIS_TITRE = "Titre";
    private static final String AVIS_COMMENTAIRE = "commentaire";
    private static final String API_URL = "/api/avis";
    private static final String API_URL_BY_ID = API_URL + "/" + AVIS_ID;
    
    @MockitoBean
    private AvisService srv;
    
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
        Avis a1 = new Avis();
        a1.setId(AVIS_ID);
        a1.setNote(AVIS_NOTE);
        a1.setTitre(AVIS_TITRE);
        a1.setCommentaire(AVIS_COMMENTAIRE);

        // On retourne la liste de AVIS
        Mockito.when(srv.getAll()).thenReturn(List.of(a1));

        // --- WHEN ---
        ResultActions result = this.mockMvc.perform(
                MockMvcRequestBuilders.get(API_URL)
        );

        // --- THEN ---
        
        result.andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(AVIS_ID))
              .andExpect(MockMvcResultMatchers.jsonPath("$[0].note").value(AVIS_NOTE))
              .andExpect(MockMvcResultMatchers.jsonPath("$[0].titre").value(AVIS_TITRE))
              .andExpect(MockMvcResultMatchers.jsonPath("$[0].commentaire").value(AVIS_COMMENTAIRE));
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
    	Mockito.when(this.srv.getById(AVIS_ID)).thenReturn(Optional.of(new Avis()));
        // when
        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL_BY_ID));

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser
    void shouldGetByIdUseServiceGetById() throws Exception {
        // given
        Mockito.when(this.srv.getById(AVIS_ID)).thenReturn(Optional.of(new Avis()));

        // when
        this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL_BY_ID));

        // then
        Mockito.verify(this.srv).getById(AVIS_ID);
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
    	Avis a1 = new Avis();
        a1.setId(AVIS_ID);
        a1.setNote(AVIS_NOTE);
        a1.setTitre(AVIS_TITRE);
        a1.setCommentaire(AVIS_COMMENTAIRE);
        Mockito.when(this.srv.getById(AVIS_ID)).thenReturn(Optional.of(a1));

        // when
        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL_BY_ID));

        // then
        result.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.note").exists());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.titre").exists());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.commentaire").exists());
    }
    
    
    @Test
    void shouldCreateStatusUnauthorized() throws Exception {
        // given

        // when
        ResultActions result = this.createAndPost(AVIS_NOTE, AVIS_TITRE, AVIS_COMMENTAIRE);

        // then
        result.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void shouldCreateStatusForbidden() throws Exception {
        // given

        // when
        ResultActions result = this.createAndPost(AVIS_NOTE, AVIS_TITRE, AVIS_COMMENTAIRE);

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = "EMPLOYE")
    void shouldCreateStatusOk() throws Exception {
        // given

        // when
        ResultActions result = this.createAndPost(AVIS_NOTE, AVIS_TITRE, AVIS_COMMENTAIRE);

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = "EMPLOYE")
    void shouldCreateUseDaoSave() throws Exception {
        // given
        ArgumentCaptor<Avis> avisCaptor = ArgumentCaptor.captor();

        // when
        this.createAndPost(AVIS_NOTE, AVIS_TITRE, AVIS_COMMENTAIRE);

        // then
        Mockito.verify(this.srv).create(avisCaptor.capture());

        Avis avis = avisCaptor.getValue();

        Assertions.assertEquals(AVIS_NOTE, avis.getNote());
        Assertions.assertEquals(AVIS_TITRE, avis.getTitre());
        Assertions.assertEquals(AVIS_COMMENTAIRE, avis.getCommentaire());
    }

    @ParameterizedTest
    @CsvSource({
        "0,'',''",
        "0,'   ', '   '",
        "0,,",
        "0,'',commentaire",
        "0,'    ',commentaire",
        "0,,commentaire"
    })
    @WithMockUser(roles = "EMPLOYE")
    void shouldCreateStatusBadRequest(int note, String titre, String commentaire) throws Exception {
        // given

        // when
        ResultActions result = this.createAndPost(note, titre, commentaire);

        // then
        result.andExpect(MockMvcResultMatchers.status().isBadRequest());

        Mockito.verify(this.srv, Mockito.never()).create(Mockito.any());
    }

    private ResultActions createAndPost(int note, String titre, String commentaire) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        AvisRequest request = new AvisRequest();

        request.setNote(note);
        request.setTitre(titre);
        request.setCommentaire(commentaire);

        return this.mockMvc.perform(MockMvcRequestBuilders
            .post(API_URL)
            .with(SecurityMockMvcRequestPostProcessors.csrf())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(mapper.writeValueAsString(request))
        );
    }
    
    
    @Test
    void shouldUpdateStatusUnauthorized() throws Exception {
        // given

        // when
        ResultActions result = this.updateAndPut(AVIS_NOTE, AVIS_TITRE, AVIS_COMMENTAIRE);

        // then
        result.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    void shouldUpdateStatusForbidden() throws Exception {
        // given

        // when
        ResultActions result = this.updateAndPut(AVIS_NOTE, AVIS_TITRE, AVIS_COMMENTAIRE);

        // then
        result.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "EMPLOYE")
    void shouldUpdateStatusOk() throws Exception {
        // given
    	Mockito.when(srv.getById(AVIS_ID)).thenReturn(Optional.of(new Avis()));

        // when
        ResultActions result = this.updateAndPut(AVIS_NOTE, AVIS_TITRE, AVIS_COMMENTAIRE);

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = "EMPLOYE")
    void shouldUpdateUseSrvUpdate() throws Exception {
        // given
    	Avis a = new Avis();
        a.setId(AVIS_ID);

        Mockito.when(srv.getById(AVIS_ID)).thenReturn(Optional.of(a));
        ArgumentCaptor<Avis> avisCaptor = ArgumentCaptor.captor();

        // when
        this.updateAndPut(AVIS_NOTE, AVIS_TITRE, AVIS_COMMENTAIRE);

        // then
        Mockito.verify(this.srv).update(avisCaptor.capture());

        Avis avis = avisCaptor.getValue();

        Assertions.assertEquals(AVIS_NOTE, avis.getNote());
        Assertions.assertEquals(AVIS_TITRE, avis.getTitre());
        Assertions.assertEquals(AVIS_COMMENTAIRE, avis.getCommentaire());
    }

    @ParameterizedTest
    @CsvSource({
        "0,'',''",
        "0,'   ', '   '",
        "0,,",
        "0,'',commentaire",
        "0,'    ',commentaire",
        "0,,commentaire"
    })
    @WithMockUser(roles = "EMPLOYE")
    void shouldUpdateStatusBadRequest(int note, String titre, String commentaire) throws Exception {
        // given

        // when
        ResultActions result = this.updateAndPut(note, titre, commentaire);

        // then
        result.andExpect(MockMvcResultMatchers.status().isBadRequest());

        Mockito.verify(this.srv, Mockito.never()).update(Mockito.any());
    }

    private ResultActions updateAndPut(int note, String titre, String commentaire) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        AvisRequest request = new AvisRequest();

        request.setNote(note);
        request.setTitre(titre);
        request.setCommentaire(commentaire);

        return this.mockMvc.perform(MockMvcRequestBuilders
            .put(API_URL_BY_ID)
            .with(SecurityMockMvcRequestPostProcessors.csrf())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(mapper.writeValueAsString(request))
        );
    }
    
    @Test
    void shouldDeleteStatusUnauthorized() throws Exception {
        ResultActions result = this.mockMvc.perform(
                MockMvcRequestBuilders.delete(API_URL_BY_ID)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
        );

        result.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void shouldDeleteStatusForbidden() throws Exception {
        ResultActions result = this.mockMvc.perform(
                MockMvcRequestBuilders.delete(API_URL_BY_ID)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
        );

        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = "EMPLOYE")
    void shouldDeleteStatusOk() throws Exception {

        ResultActions result = this.mockMvc.perform(
                MockMvcRequestBuilders.delete(API_URL_BY_ID)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
        );

        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(roles = "EMPLOYE")
    void shouldDeleteUseServiceDeleteById() throws Exception {

        this.mockMvc.perform(
                MockMvcRequestBuilders.delete(API_URL_BY_ID)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
        );

        Mockito.verify(this.srv).deleteById(AVIS_ID);
    }
    

}
