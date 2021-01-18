package petclinicclone.petclinc.model;

import lombok.Getter;
import lombok.Setter;


import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotEmpty;

@MappedSuperclass
@Getter
@Setter
public abstract class NamedEntity extends BaseEntity{

    @NotEmpty
    private String name;
}
