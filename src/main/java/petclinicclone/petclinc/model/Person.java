package petclinicclone.petclinc.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@MappedSuperclass
public abstract class Person extends BaseEntity {

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

}
