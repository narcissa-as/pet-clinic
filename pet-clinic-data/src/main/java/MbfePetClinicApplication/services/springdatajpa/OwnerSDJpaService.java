package MbfePetClinicApplication.services.springdatajpa;

import MbfePetClinicApplication.model.Owner;
import MbfePetClinicApplication.repositories.OwnerRepository;
import MbfePetClinicApplication.repositories.PetRepository;
import MbfePetClinicApplication.repositories.PetTypeRepository;
import MbfePetClinicApplication.services.OwnerService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
@Service
@Profile("springdatajpa")
public class OwnerSDJpaService implements OwnerService {
    private final OwnerRepository ownerRepository;
    private final PetRepository petRepository;

    public OwnerSDJpaService(OwnerRepository ownerRepository, PetRepository petRepository, PetTypeRepository petTypeRepository) {
        this.ownerRepository = ownerRepository;
        this.petRepository = petRepository;
        this.petTypeRepository = petTypeRepository;
    }

    private final PetTypeRepository petTypeRepository;


    @Override
    public Set<Owner> findAll() {
        Set<Owner>owners=new HashSet<>();
      ownerRepository.findAll().forEach(owners::add);
        return owners;
    }

    @Override
    public Owner findById(Long aLong) {
        Optional<Owner>optionalOwner=ownerRepository.findById(aLong);
        return optionalOwner.orElse(null);
    }

    @Override
    public Owner save(Owner object) {
        return ownerRepository.save(object);
    }

    @Override
    public void deleteById(Long aLong) {
ownerRepository.deleteById(aLong);
    }

    @Override
    public void delete(Owner object) {
ownerRepository.delete(object);
    }

    @Override
    public Owner findByLastname(String lastName) {
        return ownerRepository.findByLastName(lastName);
    }
}
