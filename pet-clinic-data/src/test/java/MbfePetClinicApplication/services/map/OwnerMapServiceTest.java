package MbfePetClinicApplication.services.map;

import MbfePetClinicApplication.model.Owner;
import MbfePetClinicApplication.services.PetService;
import MbfePetClinicApplication.services.PetTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
@Service
class OwnerMapServiceTest {

    OwnerMapService ownerMapService;
final Long ownerId=1L;
    final String lastName="Smith";
    @BeforeEach
    void setUp() {
        //injecting manually mimicking spring injection, Because here we have just a simple Hashmap we can do it like
        // this:creating an object by new an object, how we want and not by injection
     ownerMapService =new OwnerMapService(new PetTypeServiceMap(),new PetMapService());
        ownerMapService.save(Owner.builder().id(ownerId).lastName(lastName).build());
        }


    @Test
    void findAll() {
       Set<Owner> ownerSet =ownerMapService.findAll();
       assertEquals(1,ownerSet.size());
    }

    @Test
    void findById() {
        Owner owner=ownerMapService.findById(ownerId);
        assertEquals(ownerId,owner.getId());
    }

    @Test
    void saveExistingId() {
        Long id=2L;
        Owner owner2=Owner.builder().id(id).build();
        Owner savedOwner=ownerMapService.save(owner2);
        assertEquals(id,savedOwner.getId());
    }
    //Checking if creating an object without given id,test it to not be empty,and also the id won't be null

    @Test
void saveNoId(){
        Owner savedOwner=ownerMapService.save(Owner.builder().build()) ;
        assertNotNull(savedOwner);
        assertNotNull(savedOwner.getId());
}
    @Test
    void deleteById() {
ownerMapService.deleteById(ownerId);
        assertEquals(0,ownerMapService.findAll().size());
    }

    @Test
    void delete() {
        ownerMapService.delete(ownerMapService.findById(ownerId));
        assertEquals(0,ownerMapService.findAll().size());
    }


    @Test
    void findByLastname() {
        Owner smith=ownerMapService.findByLastname(lastName);
        assertNotNull(smith);
        assertEquals(ownerId,smith.getId());
    }
    @Test
    void findByLastnameNotFound() {
        Owner owner=ownerMapService.findByLastname("foo");
        assertNull(owner);

    }
}