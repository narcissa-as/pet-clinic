package MbfePetClinicApplication.controllers;

import MbfePetClinicApplication.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping({"/owners"})
@Controller
public class OwnerController {
    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @RequestMapping({"/","","/index","/index.html"})
    public String ownersList(Model model){
        model.addAttribute("owners", ownerService.findAll());
        return "owners/index";
    }
    @RequestMapping({"/find"})
    public String findOwners(){
        //return "owners/index";
        return "notimplemented";
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
}
