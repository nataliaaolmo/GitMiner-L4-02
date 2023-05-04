package aiss.gitminer.repository;

import aiss.gitminer.model.Project;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class ProjectRepository {
    // actua de interfaz con la base de datos, así nos facilita  las operaciones CRUD
    List<Project> projects = new ArrayList<>();
    // definimos datos de prueba porque no tenemos baso de datos y vamos añadiendolos al constructor
    //para ello tenemos que definir e iniciar la lista projects
    public ProjectRepository(){
        projects.add(new Project(
                UUID.randomUUID().toString(), //id generado aleatoriamente
                "Contenedores Docker",
                "http/contenedoresdockdock"

        ));
        projects.add(new Project(
                UUID.randomUUID().toString(),
                "Nevermind",
                "http/nevermindness"
        ));
    }
    public List<Project> findAll(){
        return projects;
    }

    public Project findProjectById(String id){
        return projects.stream()
                .filter(project->project.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Project create(Project project){
        Project newProject = new Project(
                UUID.randomUUID().toString(),
                project.getName(),
                project.getWebUrl());
        //como la lista de issues y de commits está vacía no se pone
        projects.add(newProject);
        return newProject;
    }

    public void update(Project updatedProject, String id){
        Project existing = findProjectById(id);
        int i = projects.indexOf(existing);
        updatedProject.setId(existing.getId());
        projects.set(i, updatedProject);
    }

    public void delete(String id) {
        projects.removeIf(project->project.getId().equals(id));
    }
}
