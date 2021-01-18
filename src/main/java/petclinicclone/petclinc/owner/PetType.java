package petclinicclone.petclinc.owner;

import petclinicclone.petclinc.model.NamedEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "type")
public class PetType extends NamedEntity {
}
