package aiss.gitminer.controller;

import aiss.gitminer.model.Project;
import aiss.gitminer.repository.ProjectRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    //permiten manejar solicitudes HTTP que llegan a la aplicación
    //implementa la funcionalidad del sistema para que un usuario externo pueda consumirlo
    //este controller contacta con el repository para obtener la información

    //iniciliazimos elrepositorio con los datos de prueba
    private final ProjectRepository projectRepository;

    public ProjectController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    //GET http://localhost:8080/gitminer/projects
    @GetMapping
    public List<Project> findAll(){
        return projectRepository.findAll();
    }

    //Get http://localhost:8080/gitminer/projects/{id}
    @GetMapping("/{id}")
    public Project finOne(@PathVariable String id){
        return projectRepository.findProjectById(id);
    }

    //POST http://localhost:8080/gitminer/projects
    @ResponseStatus(HttpStatus.CREATED) //201=operación con éxito
    // en los GET no hace falta ponerlo pero en los POST sí
    @PostMapping
    public Project create(@Valid @RequestBody Project project){
        return projectRepository.create(project);
    }

    //PUT http://localhost:8080/gitminer/projects/{id}
    @ResponseStatus(HttpStatus.NO_CONTENT) //devuelve un código 204
    @PutMapping("/{id}")
    public void update(@Valid @RequestBody Project updatedProject, @PathVariable String id){
        projectRepository.update(updatedProject, id);
    }

    //DELETE http://localhost:8080/gitminer/projects/{id}
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id){
        projectRepository.delete(id);
    }
}
