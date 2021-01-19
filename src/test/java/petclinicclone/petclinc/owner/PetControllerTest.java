package petclinicclone.petclinc.owner;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = PetController.class,
        includeFilters = @ComponentScan.Filter(value = PetTypeFormatter.class, type = FilterType.ASSIGNABLE_TYPE))
public class PetControllerTest {

    private static final Long TEST_OWNER_ID = 1L;

    private static final Long TEST_PET_ID = 1L;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private PetRepository petRepository;

    @MockBean
    private OwnerRepository ownerRepository;

    @BeforeEach
    void setup() {
        PetType cat = new PetType();
        cat.setId(3L);
        cat.setName("hamster");
        given(this.petRepository.findPetTypes()).willReturn(Lists.newArrayList(cat));
        given(this.ownerRepository.findById(TEST_OWNER_ID)).willReturn(new Owner());
        given(this.petRepository.findById(TEST_PET_ID)).willReturn(new Pet());

    }

    @Test
    public void testInitCreateForm() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(get("/owners/{ownerId}/pets/new",TEST_OWNER_ID)).andExpect(status().isOk())
                .andExpect(view().name("pets/createOrUpdatePetForm"))
                .andExpect(model().attributeExists("pet"));
    }

    @Test
    public void testProcessInitCreateFormSuccess() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(post("/owners/{ownerId}/pets/new",TEST_OWNER_ID).param("birthDate", "2021-01-19")
        .param("name","Betty")).andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/owners/{ownerId}"));
    }

//    @Test
//    void testProcessCreationFormHasErrors() throws Exception {
//        mockMvc.perform(post("/owners/{ownerId}/pets/new", TEST_OWNER_ID).param("name", "Betty").param("birthDate",
//                "2015-02-12")).andExpect(model().attributeHasNoErrors("owner"))
//                .andExpect(model().attributeHasErrors("pet")).andExpect(model().attributeHasFieldErrors("pet", "type"))
//                .andExpect(model().attributeHasFieldErrorCode("pet", "type", "required")).andExpect(status().isOk())
//                .andExpect(view().name("pets/createOrUpdatePetForm"));
//    }

    @Test
    void testInitUpdateForm() throws Exception {
        mockMvc.perform(get("/owners/{ownerId}/pets/{petId}/edit", TEST_OWNER_ID, TEST_PET_ID))
                .andExpect(status().isOk()).andExpect(model().attributeExists("pet"))
                .andExpect(view().name("pets/createOrUpdatePetForm"));
    }

    @Test
    void testProcessUpdateFormSuccess() throws Exception {
        mockMvc.perform(post("/owners/{ownerId}/pets/{petId}/edit", TEST_OWNER_ID, TEST_PET_ID).param("name", "Betty")
                .param("type", "hamster").param("birthDate", "2015-02-12")).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/{ownerId}"));
    }

    @Test
    void testProcessUpdateFormHasErrors() throws Exception {
        mockMvc.perform(post("/owners/{ownerId}/pets/{petId}/edit", TEST_OWNER_ID, TEST_PET_ID).param("name", "Betty")
                .param("birthDate", "2015/02/12")).andExpect(model().attributeHasNoErrors("owner"))
                .andExpect(model().attributeHasErrors("pet")).andExpect(status().isOk())
                .andExpect(view().name("pets/createOrUpdatePetForm"));
    }
}
