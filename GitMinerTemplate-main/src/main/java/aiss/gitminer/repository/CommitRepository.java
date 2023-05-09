package aiss.gitminer.repository;

import aiss.gitminer.model.Commit;
import aiss.gitminer.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommitRepository extends JpaRepository<Commit, Long> {
    Page<Project> findById(long id, Pageable pageable);

    Page<Project> findByName(String name, Pageable pageable);


}
