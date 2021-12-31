package MbfePetClinicApplication.bootstrap;

import MbfePetClinicApplication.model.*;
import MbfePetClinicApplication.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Component
public class DataLoader implements CommandLineRunner {
    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final SpecialtiesService specialtiesService;
    private final VisitService visitService;


    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService, SpecialtiesService specialtiesService, VisitService visitService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.specialtiesService = specialtiesService;
        this.visitService = visitService;
    }

    /* public DataLoader() {
              ownerService = new OwnerServiceMap();
              vetService = new VetServiceMap();
          }*/
    @Override
    public void run(String... args) throws Exception {
        int count = petTypeService.findAll().size();
        if (count == 0) {
            loadData();
        }
    }

    private void loadData() {
        PetType dog = new PetType();
        dog.setName("Dog");
        //saving in DB
        PetType savedDogPetType = petTypeService.save(dog);

        PetType cat = new PetType();
        cat.setName("Cat");
        //saving in DB
        PetType savedCatPetType = petTypeService.save(cat);

        Specialty radiology = new Specialty();
        radiology.setDescription("Radiology");
        Specialty savedRadiology = specialtiesService.save(radiology);

        Specialty surgery = new Specialty();
        surgery.setDescription("Surgery");
        Specialty savedSurgery = specialtiesService.save(surgery);

        Specialty dentistry = new Specialty();
        dentistry.setDescription("Dentistry");
        Specialty savedDentistry = specialtiesService.save(dentistry);

        Owner owner1 = new Owner();
        owner1.setFirstName("Sarah");
        owner1.setLastName("Poly");
        owner1.setCity("london");
        owner1.setAddress("no 98 st1");
        owner1.setTelephone("03098765");
        
        ownerService.save(owner1);

        Owner owner2 = new Owner();
        owner2.setFirstName("Jema");
        owner2.setLastName("zamfrogna");
        owner2.setCity("Birmingham");
        owner2.setAddress("no 99 st2");
        owner2.setTelephone("03098765");

        Pet pet1 = new Pet();
        pet1.setName("Sarah's Cat");
        pet1.setOwner(owner2);
        pet1.setPetType(savedCatPetType);
        pet1.setBirthDate(LocalDate.now());

        owner2.getPets().add(pet1);
        ownerService.save(owner2);

        Visit visit1 = new Visit();
        visit1.setPet(pet1);
        visit1.setDescription("visit1");
        visit1.setDate(LocalDate.now());
        visitService.save(visit1);
        /*Set<Visit> catVisit=new HashSet<>();
        catVisit.add(visit1);
        pet1.setVisits(catVisit);*/

        System.out.println("Owners loaded...");
        Vet vet1 = new Vet();
        vet1.setFirstName("Salnai");
        vet1.setLastName("Taji");
        //adding specialty to Vet
        vet1.getSpecialties().add(savedRadiology);

        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("Sam");
        vet2.setLastName("Watson");
        vet2.getSpecialties().add(savedSurgery);

        vetService.save(vet2);

        System.out.println("Vets loaded...");
    }
}
