package aiss.gitminer.repository;

import aiss.gitminer.model.Commit;
import aiss.gitminer.model.Project;
import aiss.gitminer.model.Commit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface CommitRepository extends JpaRepository<Commit, String> {
    Page<Commit> findById(String id, Pageable pageable);
    Page<Commit> findByTitle(String title, Pageable pageable);
    Page<Commit> findByAuthorName(String authorName, Pageable pageable);
    Page<Commit> findByWebUrl(String webUrl, Pageable pageable);
}
