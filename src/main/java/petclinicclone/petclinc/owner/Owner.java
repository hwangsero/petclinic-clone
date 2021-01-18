package petclinicclone.petclinc.owner;

import lombok.*;
import petclinicclone.petclinc.model.Person;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

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
}
