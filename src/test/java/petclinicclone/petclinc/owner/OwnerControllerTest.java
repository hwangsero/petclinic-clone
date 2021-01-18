package petclinicclone.petclinc.owner;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import petclinicclone.petclinc.visit.Visit;
import petclinicclone.petclinc.visit.VisitRepository;


import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OwnerController.class)
class OwnerControllerTest {

    private static final Long TEST_OWNER_ID = 1L;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OwnerRepository ownerRepository;

    @MockBean
    private VisitRepository visitRepository;

    private Owner owner;

    @BeforeEach
    void setup() {
        owner = new Owner();
        owner.setId(TEST_OWNER_ID);
        owner.setFirstName("sero");
        owner.setLastName("hwang");
        owner.setAddress("address");
        owner.setCity("city");
        owner.setTelephone("01011112222");
        Pet pet = new Pet();
        PetType dog = new PetType();
        dog.setName("dog");
        pet.setId(1L);
        pet.setType(dog);
        pet.setName("Max");
        pet.setBirthDate(LocalDate.now());
        owner.setPets(Collections.singleton(pet));
        given(ownerRepository.findById(TEST_OWNER_ID))
                .willReturn(owner);
        Visit visit = new Visit();
        visit.setDate(LocalDate.now());
        given(visitRepository.findByPetId(pet.getId()))
                .willReturn(Collections.singletonList(visit));
    }

    @Test
    public void testInitCreateForm() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(get("/owners/new")).andExpect(status().isOk())
                .andExpect(model().attributeExists("owner"))
                .andExpect(view().name("owners/createOrUpdateOwnerForm"));
    }

    @Test
    public void testProcessCreationFomSuccess() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(post("/owners/new").param("firstName","sero")
        .param("lastName","hwang").param("address","address")
                .param("city","city").param("telephone","01011112222")
        ).andExpect(status().is3xxRedirection());
    }

    @Test
    public void testProcessCreationFormHasErrors() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(post("/owners/new").param("firstName","sero")
                .param("lastName","hwang").param("city","city"))
        .andExpect(status().isOk()).andExpect(model().attributeHasFieldErrors("owner"))
        .andExpect(model().attributeHasFieldErrors("owner","address"))
        .andExpect(model().attributeHasFieldErrors("owner","telephone"))
        .andExpect(view().name("owners/createOrUpdateOwnerForm"));
    }

    @Test
    public void testInitFindForm() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(get("/owners/find")).andExpect(status().isOk())
                .andExpect(model().attributeExists("owner"))
                .andExpect(view().name("owners/findOwners"));
    }

    @Test
    public void testProcessFindFormSuccess() throws Exception {
        //given
        //when
        //then
        given(ownerRepository.findByLastName("")).willReturn(Lists.newArrayList(owner,new Owner()));
        mockMvc.perform(get("/owners")).andExpect(status().isOk())
        .andExpect(view().name("owners/ownersList"));
    }

}