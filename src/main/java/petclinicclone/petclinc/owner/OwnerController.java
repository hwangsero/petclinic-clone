package petclinicclone.petclinc.owner;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import petclinicclone.petclinc.visit.VisitRepository;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/owners")
public class OwnerController {

    private static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";

    private final OwnerRepository ownerRepository;

    private final VisitRepository visits;

    @GetMapping("/new")
    public String initCreateForm(Map<String, Object> model) {
        Owner owner = new Owner();
        model.put("owner",owner);
        return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/new")
    public String processCreateForm(@Valid Owner owner, BindingResult result) {
        if(result.hasErrors()) {
            return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
        }
        else {
            ownerRepository.save(owner);
            return "redirect:/owners/" + owner.getId();
        }
    }

    @GetMapping("/find")
    public String initFindForm(Model model) {
        Owner owner = new Owner();
        model.addAttribute(owner);
        return "owners/findOwners";
    }

    @GetMapping("")
    public String ProcessFindForm(Owner owner, BindingResult result, Map<String, Object> model) {

        if(owner.getLastName() == null)
            owner.setLastName("");

        Collection<Owner> findOwners = ownerRepository.findByLastName(owner.getLastName());
        if(findOwners.isEmpty()) {
            result.rejectValue("lastName", "notFound", "notFound");
            return "/findOwners";
        }
        else if(findOwners.size() == 1) {
            owner = findOwners.iterator().next();
            return "redirect:/owners/" + owner.getId();
        }
        else {
            model.put("selections", findOwners);
            return "owners/ownersList";
        }
    }

    @GetMapping("/{ownerId}/edit")
    public String initUpdateOwnerForm(@PathVariable("ownerId") Long ownerId, Model model) {
        Owner owner = ownerRepository.findById(ownerId);
        model.addAttribute(owner);
        return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/{ownerId}/edit")
    public String processUpdateForm(@Valid Owner owner, BindingResult result
                                     , @PathVariable("ownerId") Long ownerId) {
        if(result.hasErrors())
            return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
        else {
            owner.setId(ownerId);
            ownerRepository.save(owner);
            return "redirect:/owners/{ownerId}";
        }
    }

    @GetMapping("/{ownerId}")
    public ModelAndView showOwner(@PathVariable("ownerId") Long ownerId) {
        ModelAndView mav = new ModelAndView("owners/ownerDetails");
        Owner owner = ownerRepository.findById(ownerId);
//        for (Pet pet : owner.getPets()) {
//            pet.setVisitsInternal(visits.findByPetId(pet.getId()));
//        }
        mav.addObject(owner);
        return mav;
    }
}
