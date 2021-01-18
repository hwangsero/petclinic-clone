package petclinicclone.petclinc.visit;


import org.hibernate.mapping.Collection;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VisitRepository extends Repository<Visit, Long> {

    List<Visit> findByPetId(@Param("petId")Long petId);

}
