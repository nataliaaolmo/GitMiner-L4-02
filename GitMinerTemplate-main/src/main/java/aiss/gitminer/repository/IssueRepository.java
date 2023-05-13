package aiss.gitminer.repository;

import aiss.gitminer.model.Issue;
import aiss.gitminer.model.Issue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRepository extends JpaRepository<Issue, String> {
    Page<Issue> findById(String id, Pageable pageable);
    Page<Issue> findByRefId(String refId, Pageable pageable);
    Page<Issue> findByTitle(String title, Pageable pageable);
    Page<Issue> findByWebUrl(String webUrl, Pageable pageable);

}
