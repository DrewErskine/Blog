package peep.pea.blog.user;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
interface UserRepository extends PagingAndSortingRepository<User, Long>{
    Optional<User> findById(Long id);
    User save(User user);
    Page<User> findAll(Pageable pageable);

    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    void deleteById(Long id);
}
