package MbfePetClinicApplication.services;

import MbfePetClinicApplication.model.Owner;

import java.util.List;


public interface OwnerService extends CRUDService<Owner,Long>{
      Owner findByLastname(String lastName);
      List<Owner> findAllByLastNameLike(String lastName);


 }
