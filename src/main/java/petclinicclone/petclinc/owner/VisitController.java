package petclinicclone.petclinc.owner;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import petclinicclone.petclinc.visit.Visit;
import petclinicclone.petclinc.visit.VisitRepository;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class VisitController {

    private final VisitRepository visitRepository;

    private final PetRepository petRepository;

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @ModelAttribute("visit")
    public Visit loadPetWithVisit(@PathVariable("petId") Long petId, Map<String, Object> model) {
        Pet pet = this.petRepository.findById(petId);
        pet.setVisitsInternal(this.visitRepository.findByPetId(petId));
        model.put("pet", pet);
        Visit visit = new Visit();
        pet.addVisit(visit);
        return visit;
    }

    @GetMapping("/owners/*/pets/{petId}/visits/new")
    public String initNewVisitForm(@PathVariable("petId") int petId, Map<String, Object> model) {
        return "pets/createOrUpdateVisitForm";
    }

    @PostMapping("/owners/{ownerId}/pets/{petId}/visits/new")
    public String processNewVisitForm(@Valid Visit visit, BindingResult result) {
        if (result.hasErrors()) {
            return "pets/createOrUpdateVisitForm";
        }
        else {
            this.visitRepository.save(visit);
            return "redirect:/owners/{ownerId}";
        }
    }
}
