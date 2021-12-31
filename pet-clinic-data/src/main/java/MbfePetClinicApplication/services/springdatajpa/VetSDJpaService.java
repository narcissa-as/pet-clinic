package MbfePetClinicApplication.services.springdatajpa;

import MbfePetClinicApplication.model.Vet;
import MbfePetClinicApplication.repositories.SpecialtyRepository;
import MbfePetClinicApplication.repositories.VetRepository;
import MbfePetClinicApplication.services.VetService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Profile("springdatajpa")
public class VetSDJpaService implements VetService {
    private final VetRepository vetRepository;

    public VetSDJpaService(VetRepository vetRepository, SpecialtyRepository specialtyRepository) {
        this.vetRepository = vetRepository;
            }

    @Override
    public Set<Vet> findAll() {
        Set<Vet> vets = new HashSet<>();
        vetRepository.findAll().forEach(vets::add);
        return vets;
    }

    @Override
    public Vet findById(Long aLong) {
        Optional<Vet> vetOptional = vetRepository.findById(aLong);
        return vetOptional.orElse(null);
    }

    @Override
    public Vet save(Vet object) {
        return vetRepository.save(object);
    }

    @Override
    public void deleteById(Long aLong) {
        vetRepository.deleteById(aLong);
    }

    @Override
    public void delete(Vet object) {
        vetRepository.delete(object);
    }

    @Override
    public Vet findByLastname(String lastName) {
        return vetRepository.findByLastName(lastName);
    }
}
