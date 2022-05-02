package MbfePetClinicApplication.controllers;

import MbfePetClinicApplication.model.Pet;
import MbfePetClinicApplication.model.Visit;
import MbfePetClinicApplication.services.PetService;
import MbfePetClinicApplication.services.VisitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;


@Controller
public class VisitController {
    //the better way that tutor didn't use in this session
    //private static final String VIEW_CREATE_OR_UPDATE_VISIT_FORM = "pets/createOrUpdateVisitForm";
    private final VisitService visitService;
    private final PetService petService;

    public VisitController(VisitService visitService, PetService petService) {
        this.visitService = visitService;
        this.petService = petService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }
    /**
     * Called before each and every @RequestMapping annotated method.
     * 2 goals:
     * - Make sure we always have fresh data
     * - Since we do not use the session scope, make sure that Pet object always has an id
     * (Even though id is not part of the form fields)
     *
     * @param petId
     * @return Pet
     */
    //with these codes after "return", we have our Pet obj(founded with it's id) and would have an empty visit obj
    //ready to be set by the user's form fields
    @ModelAttribute("visit")
    public Visit loadPetWithVisit(@PathVariable("petId") Long petId, Map<String, Object> model) {
        Pet pet = petService.findById(petId);
        model.put("pet", pet);
        Visit visit = new Visit();
        pet.getVisits().add(visit);
        visit.setPet(pet);
        return visit;
    }
    // Spring MVC calls method loadPetWithVisit(...) before initNewVisitForm is called
    @GetMapping("/owners/{ownerId}/pets/{petId}/visits/new")
    public String initNewVisit(Visit visit, Model model) {
        model.addAttribute("visit", visit);
        return "pets/createOrUpdateVisitForm";
    }
    // Spring MVC calls method loadPetWithVisit(...) before processNewVisitForm is called
    @PostMapping("/owners/{ownerId}/pets/{petId}/visits/new")
    public String processNewVisit(@Validated Visit visit, BindingResult result) {
        if (result.hasErrors()) {
            return "pets/createOrUpdateVisitForm";
        }
        visitService.save(visit);
        return "redirect:/owners/{ownerId}";

    }
}
