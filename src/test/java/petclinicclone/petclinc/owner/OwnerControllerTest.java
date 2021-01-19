package petclinicclone.petclinc.owner;

import org.assertj.core.util.Lists;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
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
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.empty;
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

    @Test
    public void testProcessFindFormByLastName() throws Exception {
        //given
        //when
        //then
        given(ownerRepository.findByLastName("")).willReturn(Lists.newArrayList(owner));
        mockMvc.perform(get("/owners")).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/" + owner.getId()));
    }

    @Test
    public void testProcessFindNoAOwnersFound() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(get("/owners").param("lastName", "Unknown Surname")).andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("owner", "lastName"))
                .andExpect(model().attributeHasFieldErrorCode("owner", "lastName", "notFound"))
                .andExpect(view().name("owners/findOwners"));
    }

    @Test
    public void testInitUpdateOwnerForm() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(get("/owners/{ownerId}/edit", TEST_OWNER_ID)).andExpect(status().isOk())
                .andExpect(model().attributeExists("owner"))
                .andExpect(model().attribute("owner", hasProperty("lastName", is("hwang"))))
                .andExpect(model().attribute("owner", hasProperty("firstName", is("sero"))))
                .andExpect(model().attribute("owner", hasProperty("address", is("address"))))
                .andExpect(model().attribute("owner", hasProperty("city", is("city"))))
                .andExpect(model().attribute("owner", hasProperty("telephone", is("01011112222"))))
                .andExpect(view().name("owners/createOrUpdateOwnerForm"));
    }

    @Test
    public void testProcessUpdateForm() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(post("/owners/{ownerId}/edit",TEST_OWNER_ID).param("firstName", "Joe")
                .param("lastName", "Bloggs").param("address", "123 Caramel Street").param("city", "London")
                .param("telephone", "01616291589")).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/{ownerId}"));
    }

    @Test
    public void testProcessUpdateFormHasErrors() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(post("/owners/{ownerId}/edit", TEST_OWNER_ID).param("firstName", "Joe")
                .param("lastName", "Bloggs").param("city", "London")).andExpect(status().isOk())
                .andExpect(model().attributeHasErrors("owner"))
                .andExpect(model().attributeHasFieldErrors("owner", "address"))
                .andExpect(model().attributeHasFieldErrors("owner", "telephone"))
                .andExpect(view().name("owners/createOrUpdateOwnerForm"));
    }

    @Test
    public void testShowOwner() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(get("/owners/{ownerId}", TEST_OWNER_ID)).andExpect(status().isOk())
                .andExpect(model().attribute("owner", hasProperty("lastName", is("hwang"))))
                .andExpect(model().attribute("owner", hasProperty("firstName", is("sero"))))
                .andExpect(model().attribute("owner", hasProperty("address", is("address"))))
                .andExpect(model().attribute("owner", hasProperty("city", is("city"))))
                .andExpect(model().attribute("owner", hasProperty("telephone", is("01011112222"))))
                .andExpect(model().attribute("owner", hasProperty("pets", not(empty()))));

    }


}