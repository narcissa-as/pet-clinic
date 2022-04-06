package MbfePetClinicApplication.services.springdatajpa;

import MbfePetClinicApplication.model.Owner;
import MbfePetClinicApplication.repositories.OwnerRepository;
import MbfePetClinicApplication.repositories.PetRepository;
import MbfePetClinicApplication.repositories.PetTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerSDJpaServiceTest {
    public static final String LAST_NAME = "Smith";
    @Mock
    OwnerRepository ownerRepository;
    @Mock
    PetRepository petRepository;
    @Mock
    PetTypeRepository petTypeRepository;
    @InjectMocks
    OwnerSDJpaService service;

    Owner returnOwner;

    @BeforeEach
    void setUp() {
         returnOwner = Owner.builder().id(1L).lastName(LAST_NAME).build();

    }

    @Test
    void findAll() {
        /*my test
        Set<Owner> ownerSet = new HashSet<>();
        ownerRepository.findAll().iterator().forEachRemaining(ownerSet::add);
        Set<Owner> serviceSet = new HashSet<>();
        service.findAll().iterator().forEachRemaining(serviceSet::add);
        assertEquals(serviceSet,ownerSet);
        */
        //teacher test
        Set<Owner> ownerSet = new HashSet<>();
        ownerSet.add(Owner.builder().id(1L).build());
        ownerSet.add(Owner.builder().id(2L).build());

        when(ownerRepository.findAll()).thenReturn(ownerSet);

        Set<Owner> serviceSet = service.findAll();

        assertNotNull(serviceSet);
        assertEquals(2, serviceSet.size());
    }

    /*
        @Test
        void findById() {
            when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(owner));
            Owner owner=service.findById(1L);
            assertNotNull(owner);
        }*/
    @Test
    void findByIdNotFound() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.empty());
        Owner owner = service.findById(1L);
        assertNull(owner);
    }

    @Test
    void save() {
        Owner ownerToSave = Owner.builder().id(1L).build();
        when(ownerRepository.save(any())).thenReturn(returnOwner);
        Owner savedOwner = service.save(ownerToSave);
        assertNotNull(savedOwner);
        //times?
        verify(ownerRepository).save(any());
    }

    @Test
    void deleteById() {
        service.deleteById(1L);
        verify(ownerRepository).deleteById(anyLong());

    }

    @Test
    void delete() {
        service.delete(returnOwner);
        verify(ownerRepository, times(1)).delete(any());

    }

    @Test
    void findByLastname() {
        String lastName = "lastName";
        Owner returnedOwner = Owner.builder().lastName(lastName).build();
        when(ownerRepository.findByLastName(any())).thenReturn(returnedOwner);
        Owner smith = service.findByLastname("lastName");
        assertEquals(smith, returnedOwner);
        verify(ownerRepository).findByLastName(any());

    }
}