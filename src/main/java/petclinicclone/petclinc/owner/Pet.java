package petclinicclone.petclinc.owner;

import lombok.*;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.format.annotation.DateTimeFormat;
import petclinicclone.petclinc.model.BaseEntity;
import petclinicclone.petclinc.model.NamedEntity;
import petclinicclone.petclinc.visit.Visit;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
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
    protected Set<Visit> getVisitsInternal() {
        if (this.visits == null) {
            this.visits = new HashSet<>();
        }
        return this.visits;
    }

    protected void setVisitsInternal(Collection<Visit> visits) {
        this.visits = new LinkedHashSet<>(visits);
    }

    public List<Visit> getVisits() {
        List<Visit> sortedVisits = new ArrayList<>(getVisitsInternal());
        PropertyComparator.sort(sortedVisits, new MutableSortDefinition("date", false, false));
        return Collections.unmodifiableList(sortedVisits);
    }

    public void addVisit(Visit visit) {
        getVisitsInternal().add(visit);
        visit.setPet(this);
    }
}
