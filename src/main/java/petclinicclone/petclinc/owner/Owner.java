package petclinicclone.petclinc.owner;

import lombok.*;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import petclinicclone.petclinc.model.Person;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Owner extends Person {

    @NotEmpty
    private String address;

    @NotEmpty
    private String city;

    @NotEmpty
    @Digits(fraction = 0, integer =  10)
    private String telephone;

    @OneToMany(mappedBy = "owner")
    private Set<Pet> pets = new HashSet<>();

    @Builder
    public Owner(@NotEmpty String address, @NotEmpty String city, @NotEmpty @Digits(fraction = 0, integer = 10) String telephone, Set<Pet> pets) {
        this.address = address;
        this.city = city;
        this.telephone = telephone;
        this.pets = pets;
    }

    protected Set<Pet> getPetsInternal() {
        if (this.pets == null) {
            this.pets = new HashSet<>();
        }
        return this.pets;
    }

    public void addPet(Pet pet) {
        if (pet.isNew()) {
            getPetsInternal().add(pet);
        }
        pet.setOwner(this);
    }

    public List<Pet> getListPets() {
        List<Pet> sortedPets = new ArrayList<>(getPetsInternal());
        PropertyComparator.sort(sortedPets, new MutableSortDefinition("name", true, true));
        return Collections.unmodifiableList(sortedPets);
    }

    public Pet getPet(String name, boolean ignoreNew) {
        name = name.toLowerCase();
        for (Pet pet : getPetsInternal()) {
            if (!ignoreNew || !pet.isNew()) {
                String compName = pet.getName();
                compName = compName.toLowerCase();
                if (compName.equals(name)) {
                    return pet;
                }
            }
        }
        return null;
    }
}
