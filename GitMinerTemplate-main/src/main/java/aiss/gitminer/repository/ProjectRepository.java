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
    //obtiene el nombre por el que queremos filtrar y un objeto de la clase Pageable
   // Page<Project> findByName(String name, Pageable pageable);

    //si queremos buscar no por el nombre completo, sino por un trozo de cadena que contenga el nombre usamos este metodo
//    Page<Project> findByNameContaining(String name, Pageable pageable);

    //podemos poner findBy y el atributo que sea que tenga nuestra clase

 //   Page<Project> findByIddd(long id, Pageable pageable);
 //   Page<Project> findByWeb_url(String web_url, Pageable pageable);
}

