package MbfePetClinicApplication.services.map;

import MbfePetClinicApplication.model.Specialty;
import MbfePetClinicApplication.model.Vet;
import MbfePetClinicApplication.services.SpecialtiesService;
import MbfePetClinicApplication.services.VetService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;
@Service
@Profile({"default","map"})
public class VetMapService extends AbstractMapService<Vet,Long> implements VetService {
    private final SpecialtiesService specialtiesService;

    public VetMapService(SpecialtiesService specialtiesService) {
        this.specialtiesService = specialtiesService;
    }


    @Override
    public Set<Vet> findAll() {
        return super.findAll();
    }

    @Override
    public Vet findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Vet save(Vet object) {

        if(object!=null)
        {
            if(object.getSpecialties().size()>0){
                object.getSpecialties().forEach(specialty -> {
                  if (specialty.getId()==null){
                     Specialty savedSpecialty= specialtiesService.save(specialty);
                     specialty.setId(savedSpecialty.getId());
                  }
                    });

            }
            //else {
               // throw RuntimeException("Specialty is required");
            //}

        }
        return super.save(object);
    }

    @Override
    public void deleteById(Long id) {
super.deleteById(id);
    }

    @Override
    public void delete(Vet object) {
        super.delete(object);
    }

    @Override
    public Vet findByLastname(String lastName) {
        return null;
    }
}
