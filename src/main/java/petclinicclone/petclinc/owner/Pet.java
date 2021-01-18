package petclinicclone.petclinc.owner;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import petclinicclone.petclinc.model.BaseEntity;
import petclinicclone.petclinc.model.NamedEntity;
import petclinicclone.petclinc.visit.Visit;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pet extends NamedEntity {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    private PetType type;

    @OneToMany(mappedBy = "pet")
    private Set<Visit> visits = new LinkedHashSet<>();

    @Builder
    public Pet(LocalDate birthDate, Owner owner, PetType type, Set<Visit> visits) {
        this.birthDate = birthDate;
        this.owner = owner;
        this.type = type;
        this.visits = visits;
    }
}
