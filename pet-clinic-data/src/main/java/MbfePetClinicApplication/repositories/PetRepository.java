package MbfePetClinicApplication.repositories;

import MbfePetClinicApplication.model.Pet;
import org.springframework.data.repository.CrudRepository;

public interface PetRepository extends CrudRepository<Pet,Long> {
}
