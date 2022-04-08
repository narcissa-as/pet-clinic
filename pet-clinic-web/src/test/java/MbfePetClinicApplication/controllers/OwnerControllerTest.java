package MbfePetClinicApplication.controllers;

import MbfePetClinicApplication.model.Owner;
import MbfePetClinicApplication.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {
    @Mock
    OwnerService ownerService;


    @InjectMocks
    OwnerController ownerController;

    MockMvc mockMvc;
    Set<Owner> owners;

    @BeforeEach
    void setUp() {
        owners = new HashSet<>();
        owners.add(Owner.builder().id(1L).build());
        owners.add(Owner.builder().id(2L).build());
        mockMvc = MockMvcBuilders
                .standaloneSetup(ownerController)
                .build();

    }

    @Test
    void ownersList() throws Exception {
        when(ownerService.findAll()).thenReturn(owners);
        mockMvc.perform(get("/owners/", "/owners/index", "/owners/index.html"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/index"))
                .andExpect(model().attribute("owners", hasSize(2)));

    }

    @Test
    void findOwners() throws Exception {
        //when(ownerController.findOwners()).thenReturn("notimplemented");
        mockMvc.perform(get("/owners/find"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/findOwners"));
        model().attributeExists("owner");
        verifyNoInteractions(ownerService);
    }

    @Test
    void displayOwner() throws Exception {
        //given
        Owner owner = new Owner();
        owner.setId(1L);

        when(ownerService.findById(anyLong())).thenReturn(Owner.builder().id(1L).build());

        mockMvc.perform(get("/owners/1"))
                .andExpect(view().name("owners/ownerDetails"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("owner", hasProperty("id", is(1L))));
    }

    @Test
    void processFindFormReturnMany() throws Exception {
        //given
        List<Owner> results = new ArrayList<>();

        results.add(Owner.builder().id(1L).build());
        results.add(Owner.builder().id(2L).build());

        when(ownerService.findAllByLastNameLike(anyString())).thenReturn(results);
        //then
        mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownersList"))
                .andExpect(model().attributeExists("selections"));

    }

    @Test
    void processFindFormReturnOne() throws Exception {
        List<Owner> results = new ArrayList<>();

        results.add(Owner.builder().id(2L).build());

        when(ownerService.findAllByLastNameLike(anyString())).thenReturn(results);

        mockMvc.perform(get("/owners"))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attributeExists("owner"))
                .andExpect(view().name("redirect:/owners/2"));
    }

    //i write it by myself trainer didn't write this test
    @Test
    void processFindFormNoResults() throws Exception {
        when(ownerService.findAllByLastNameLike(anyString())).thenReturn(Arrays.asList());
        mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/findOwners"));
    }

    @Test
    void initCreationForm() throws Exception {

        mockMvc.perform(get("/owners/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("owner"))
                .andExpect(view().name("owners/createOrUpdateOwnerForm"));

        verifyNoMoreInteractions(ownerService);
    }

    //it has error
    @Test
    void processCreationForm() throws Exception {
        //my test because
       /* Owner newOwner = new Owner();
        newOwner.setId(1l);
        newOwner.setLastName("a");
        newOwner.setAddress("a");
        newOwner.setCity("a");
        newOwner.setTelephone("12");
        newOwner.setFirstName("a");
        when(ownerService.save(ArgumentMatchers.any())).thenReturn(newOwner);*/
        when(ownerService.save(ArgumentMatchers.any())).thenReturn(Owner.builder().id(1l).build());

        mockMvc.perform(post("/owners/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"));
        verify(ownerService).save(ArgumentMatchers.any());
    }

    @Test
    void initUpdateOwnerForm() throws Exception {
        Owner newOwner = new Owner();
        newOwner.setId(1l);

        when(ownerService.findById(anyLong())).thenReturn(newOwner);

        //then
        mockMvc.perform(get("/owners/1/edit"))
                .andExpect(model().attributeExists("owner"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/createOrUpdateOwnerForm"));
        verifyNoMoreInteractions(ownerService);

    }

    @Test
    void processUpdateOwnerForm() throws Exception {
        //given
        Owner owner = new Owner();
        owner.setId(1l);

        when(ownerService.save(ArgumentMatchers.any())).thenReturn(owner);

        mockMvc.perform(post("/owners/1/edit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"));

        verify(ownerService).save(ArgumentMatchers.any());
    }
}