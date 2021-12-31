package MbfePetClinicApplication.services;

import MbfePetClinicApplication.model.Owner;


public interface OwnerService extends CRUDService<Owner,Long>{
      Owner findByLastname(String lastName);
 }
