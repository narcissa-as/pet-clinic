package MbfePetClinicApplication.services.map;

import MbfePetClinicApplication.model.Owner;
import MbfePetClinicApplication.model.Visit;
import MbfePetClinicApplication.repositories.VisitRepository;
import MbfePetClinicApplication.services.OwnerService;
import MbfePetClinicApplication.services.VisitService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;
@Service
@Profile({"default","map"})
public class VisitMapService extends AbstractMapService<Visit, Long> implements VisitService {
    private final VisitRepository visitRepository;

    public VisitMapService(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    @Override
    public Set<Visit> findAll() {
        return super.findAll();
    }

    @Override
    public void delete(Visit object) {
super.delete(object);
    }

    @Override
    public void deleteById(Long id) {
super.deleteById(id);
    }

    @Override
    public Visit save(Visit visit) {
        if(visit.getPet()==null
                ||visit.getPet().getId()==null
                ||visit.getPet().getOwner()==null
                ||visit.getPet().getOwner().getId()==null
        ){
            throw new RuntimeException("Invalid visit");
        }
        return super.save(visit);
    }

    @Override
    public Visit findById(Long id) {
        return super.findById(id);
    }
}
