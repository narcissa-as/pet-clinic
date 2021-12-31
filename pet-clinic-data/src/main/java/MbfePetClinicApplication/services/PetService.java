package MbfePetClinicApplication.services;

import MbfePetClinicApplication.model.BaseEntity;
import MbfePetClinicApplication.model.Owner;
import MbfePetClinicApplication.model.Pet;


//Edited because of not like other interfaces and has a problem when creating methods in PetSDJpaService(created object
//rather than Pet,..)so i edit this interface tobe like other interfaces
// public interface PetService<P extends BaseEntity, L extends Number> extends CRUDService<Pet,Long> {
public interface PetService extends CRUDService<Pet,Long> {

}
