package MbfePetClinicApplication.services;

import MbfePetClinicApplication.model.Vet;


public interface VetService extends CRUDService<Vet,Long> {
       Vet findByLastname(String lastName);
  }
