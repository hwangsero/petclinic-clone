package petclinicclone.petclinc.vet;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

public interface VetRepository extends Repository<Vet, Integer> {

    @Transactional(readOnly = true)
    @Cacheable("vets")
    Collection<Vet> findAll() throws DataAccessException;

}
