package aiss.gitminer.repository;

import aiss.gitminer.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, String> {
    Page<Project> findById(String id, Pageable pageable);
    Page<Project> findByName(String name, Pageable pageable);
    Page<Project> findByWebUrl(String web_url, Pageable pageable);
}

