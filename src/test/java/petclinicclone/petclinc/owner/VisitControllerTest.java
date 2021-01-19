package petclinicclone.petclinc.owner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import petclinicclone.petclinc.visit.VisitRepository;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VisitController.class)
class VisitControllerTests {

    private static final Long TEST_PET_ID = 1L;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VisitRepository visits;

    @MockBean
    private PetRepository pets;

    @BeforeEach
    void init() {
        given(this.pets.findById(TEST_PET_ID)).willReturn(new Pet());
    }

    @Test
    void testInitNewVisitForm() throws Exception {
        mockMvc.perform(get("/owners/*/pets/{petId}/visits/new", TEST_PET_ID)).andExpect(status().isOk())
                .andExpect(view().name("pets/createOrUpdateVisitForm"));
    }

    @Test
    void testProcessNewVisitFormSuccess() throws Exception {
        mockMvc.perform(post("/owners/*/pets/{petId}/visits/new", TEST_PET_ID).param("name", "George")
                .param("description", "Visit Description")).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/{ownerId}"));
    }

    @Test
    void testProcessNewVisitFormHasErrors() throws Exception {
        mockMvc.perform(post("/owners/*/pets/{petId}/visits/new", TEST_PET_ID).param("name", "George"))
                .andExpect(model().attributeHasErrors("visit")).andExpect(status().isOk())
                .andExpect(view().name("pets/createOrUpdateVisitForm"));
    }

}
