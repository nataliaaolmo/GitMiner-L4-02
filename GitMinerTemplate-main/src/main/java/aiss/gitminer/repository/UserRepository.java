package aiss.gitminer.repository;

import aiss.gitminer.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    Page<User> findById(String id, Pageable pageable);
    Page<User> findByUsername(String username, Pageable pageable);
    Page<User> findByName(String name, Pageable pageable);
    Page<User> findByAvatarUrl(String avatarUrl, Pageable pageable);
    Page<User> findByWebUrl(String webUrl, Pageable pageable);

}
