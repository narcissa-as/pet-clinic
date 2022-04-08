package MbfePetClinicApplication.controllers;

import MbfePetClinicApplication.model.Owner;
import MbfePetClinicApplication.model.PetType;
import MbfePetClinicApplication.services.OwnerService;
import MbfePetClinicApplication.services.PetService;
import MbfePetClinicApplication.services.PetTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller
//because we have ownerId here, in owner's @modelAttribute we can get ownerId with @Pathvariable
@RequestMapping("/owners/{ownerId}")
public class PetController {
    private final PetService petService;
    private final OwnerService ownerService;
    private final PetTypeService petTypeService;

    public PetController(PetService petService, OwnerService ownerService, PetTypeService petTypeService) {
        this.petService = petService;
        this.ownerService = ownerService;
        this.petTypeService = petTypeService;
    }
    //usage of @ModelAttribute for showing a list in drop down lists
    @ModelAttribute("types")
    public Collection<PetType>populatePetTypes(){
        return petTypeService.findAll();
    }
    @ModelAttribute("owner")
    public Owner populateOwner(@PathVariable Long ownerId){
        return ownerService.findById(ownerId);
    }
    @InitBinder("owner")
    public void initOwnerBinder(WebDataBinder dataBinder){
        dataBinder.setDisallowedFields("id");
    }
}
