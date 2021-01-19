package petclinicclone.petclinc.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotEmpty;

@MappedSuperclass
@Getter
@Setter
@ToString
public abstract class NamedEntity extends BaseEntity{

    @NotEmpty
    private String name;
}
