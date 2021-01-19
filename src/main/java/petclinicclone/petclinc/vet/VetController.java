package petclinicclone.petclinc.vet;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
class VetController {

    private final VetRepository vetRepository;

    public VetController(VetRepository clinicService) {
        this.vetRepository = clinicService;
    }

    @GetMapping("/vets.html")
    public String showVetList(Map<String, Object> model) {
        Vets vets = new Vets();
        vets.getVetList().addAll(this.vetRepository.findAll());
        model.put("vets", vets);
        return "vets/vetList";
    }

    @GetMapping({ "/vets" })
    public @ResponseBody
    Vets showResourcesVetList() {
        Vets vets = new Vets();
        vets.getVetList().addAll(this.vetRepository.findAll());
        return vets;
    }
}
