package MbfePetClinicApplication.repositories;

import MbfePetClinicApplication.model.Owner;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface OwnerRepository extends CrudRepository<Owner, Long> {
    Owner findByLastName(String lastName);

    List<Owner> findAllByLastNameLike(String lastName);
}
