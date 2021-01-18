package petclinicclone.petclinc.visit;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import petclinicclone.petclinc.model.BaseEntity;
import petclinicclone.petclinc.owner.Pet;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Visit extends BaseEntity {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @NotEmpty
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id")
    private Pet pet;
}
