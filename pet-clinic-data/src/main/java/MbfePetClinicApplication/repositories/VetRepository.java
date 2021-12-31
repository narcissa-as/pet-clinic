package MbfePetClinicApplication.repositories;

import MbfePetClinicApplication.model.Vet;
import org.springframework.data.repository.CrudRepository;

public interface VetRepository extends CrudRepository<Vet,Long> {
    Vet findByLastName (String lastName);
}
