package MbfePetClinicApplication.controllers;

import MbfePetClinicApplication.model.Owner;
import MbfePetClinicApplication.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@RequestMapping({"/owners"})
@Controller
public class OwnerController {
    private static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";
    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @RequestMapping({"/", "", "/index", "/index.html"})
    public String ownersList(Model model) {
        model.addAttribute("owners", ownerService.findAll());
        return "owners/index";
    }

    //these 2 methods are the same,in different ways but the same exactly
    @GetMapping("/{ownerId}")
    public ModelAndView showOwner(@PathVariable("ownerId") Long ownerId) {
        ModelAndView mav = new ModelAndView("owners/ownerDetails");
        mav.addObject(ownerService.findById(ownerId));
        return mav;
    }/*
    @GetMapping ({"/{ownerId}"})
    public String showOwner(@PathVariable String ownerId,Model model){
        model.addAttribute("owner",ownerService.findById(Long.valueOf(ownerId)));
        return "owners/ownerDetails";
    }*/

    @GetMapping("/find")
    public String findOwners(Model model) {
        model.addAttribute("owner", Owner.builder().build());
        return "owners/findOwners";
    }

    @GetMapping()
    public String processFindForm(Owner owner, BindingResult result, Model model) {
        // allow parameterless GET request for /owners to return all records
        if (owner.getLastName() == null) {
            owner.setLastName("");
            //here we don't need return because in HTML form we said we come back to findOwners
            //return ("owners/findOwners");
        }
        //[ BindingResult ] is Spring's object that holds the result of the validation and binding and contains
        // errors that may have occurred. The BindingResult must come right after the model object that is
        // validated or else Spring will fail to validate the object and throw an exception.
        //find owners by last name
        List<Owner> results = ownerService.findAllByLastNameLike("%" + owner.getLastName() + "%");

        if (results.isEmpty()) {
            //no owners found
            result.rejectValue("lastName", "notFound", "not found");
            return "owners/findOwners";
        } else if (results.size() == 1) {
            //1 owner found
            owner = results.get(0);
            model.addAttribute("owner", owner);
            return "redirect:/owners/" + owner.getId();
        } else {
            //multiple owners found
            model.addAttribute("selections", results);
            return "owners/ownersList";
        }
    }

    @GetMapping("/new")
    public String initCreationForm(Model model) {
        model.addAttribute("owner", Owner.builder().build());
        return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/new")
    public String processCreationForm(@Validated Owner owner, BindingResult result) {
        if (result.hasErrors()) {
            return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
        } else {
            Owner savedOwner = ownerService.save(owner);
            return "redirect:/owners/" + savedOwner.getId();
        }

    }

    @GetMapping("/{ownerId}/edit")
    String initUpdateOwnerForm(@PathVariable Long ownerId, Model model) {
        model.addAttribute("owner", ownerService.findById(ownerId));
        return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
    }
    //The reason of getting ownerId separately is that because of using  @InitBinder at top of this class we except
    //id property, so we need to get it with Pathvariable
    @PostMapping("/{ownerId}/edit")
    String processUpdateOwnerForm(@Validated Owner owner, BindingResult result,@PathVariable Long ownerId) {
        if (result.hasErrors()) {
            return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
        } else {
            //I'Ve added this line because of the issue of saving Duplicate owners while editing
            owner.setId(ownerId);
            Owner savedOwner = ownerService.save(owner);
            return "redirect:/owners/" + savedOwner.getId();
        }
    }
}
