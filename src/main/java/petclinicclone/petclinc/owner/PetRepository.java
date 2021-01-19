package petclinicclone.petclinc.owner;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PetRepository extends Repository<Pet,Long> {

    @Query("SELECT ptype FROM PetType ptype ORDER BY ptype.name")
    @Transactional(readOnly = true)
    List<PetType> findPetTypes();

    @Transactional(readOnly = true)
    Pet findById(Long id);

    void save(Pet pet);
}
