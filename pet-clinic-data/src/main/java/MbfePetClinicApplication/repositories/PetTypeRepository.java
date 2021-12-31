package MbfePetClinicApplication.repositories;

import MbfePetClinicApplication.model.PetType;
import org.springframework.data.repository.CrudRepository;

public interface PetTypeRepository extends CrudRepository<PetType,Long> {
}
