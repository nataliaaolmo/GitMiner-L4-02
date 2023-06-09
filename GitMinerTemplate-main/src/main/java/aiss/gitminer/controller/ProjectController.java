package aiss.gitminer.controller;

import aiss.gitminer.Exception.ProjectNotFoundException;
import aiss.gitminer.model.Issue;
import aiss.gitminer.model.Project;
import aiss.gitminer.repository.ProjectRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Tag(name= "Project", description= "Project management API")
@RestController
@RequestMapping("/gitminer/projects")
public class ProjectController {
    @Autowired
    ProjectRepository projectRepository;

    @Operation(summary= "Retrieve project list",
            description= "Get a list of projects ",
            tags= { "projects", "get" })

    @ApiResponses({
            @ApiResponse(responseCode = "200", description= "Listado de projects",
                    content= { @Content(schema = @Schema(implementation = Project.class), mediaType= "application/json") })
            ,@ApiResponse(responseCode = "404", description="Project no encontrado",
            content= { @Content(schema = @Schema()) })})

    // GET http://localhost:8080/api/projects
    @GetMapping
    public List<Project> findAll(
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String webUrl,
            @RequestParam(required = false) String order,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable paging;

        if (order != null) {
            if (order.startsWith("-"))
                paging = PageRequest.of(page, size, Sort.by(order.substring(1)).descending());
            else
                paging = PageRequest.of(page, size, Sort.by(order).ascending());
        } else
            paging = PageRequest.of(page, size);

        Page<Project> pageProjects;

        if (id == null && name == null && webUrl == null )
            pageProjects = projectRepository.findAll(paging);
        else if (id != null)
            pageProjects = projectRepository.findById(id, paging);
        else if (name != null)
            pageProjects = projectRepository.findByName(name, paging);
        else
            pageProjects = projectRepository.findByWebUrl(webUrl, paging);

        return pageProjects.getContent();


    }

    @Operation(summary= "Retrieve project by Id",
            description= "Get project by id",
            tags= { "get" })

    @ApiResponses({
            @ApiResponse(responseCode = "200", description= "Proyecto por id",
                    content= { @Content(schema = @Schema(implementation = Project.class), mediaType= "application/json") })
            ,@ApiResponse(responseCode = "404", description="Project no encontrado",
            content= { @Content(schema = @Schema()) })})

    // GET http://localhost:8080/api/projects/{id}
    @GetMapping("/{id}")
    public Project findOne(@Parameter(description= "id of project to be searched") @PathVariable String id) throws ProjectNotFoundException {
        Optional<Project> project = projectRepository.findById(id);
        if(!project.isPresent()){
            throw new ProjectNotFoundException();
        }
        return project.get();
    }



    @Operation(summary= "Create project",
            description= "Create a project",
            tags= { "post" })

    @ApiResponses({
            @ApiResponse(responseCode = "201", description= "Project creado",
                    content= { @Content(schema = @Schema(implementation = Project.class), mediaType= "application/json") })
            ,@ApiResponse(responseCode = "400",
            content= { @Content(schema = @Schema()) })})
    // POST http://localhost:8080/api/projects
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Project createProject(@Valid @RequestBody Project project) {
        Project _project = projectRepository.save(new Project(project.getId(), project.getName(), project.getWebUrl(), project.getCommits(), project.getIssues()));
        return _project;
    }

    /*
    @Operation(summary= "Update project",
            description= "Update a project",
            tags= { "projects", "put" })

    @ApiResponses({
            @ApiResponse(responseCode = "204", //204 significa sin contenido y se ha realizado la peticion correctamente
                    content= { @Content(schema = @Schema()) })
            ,@ApiResponse(responseCode = "400", //400 para cuando haya error en la peticion
            content= { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", //404 para cuando no se encuentre el id
                    content= { @Content(schema = @Schema()) })})
    // PUT http://localhost:8080/api/projects/{id}
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProject(@RequestBody @Valid Project updatedProject, @PathVariable Long id) throws ProjectNotFoundException { //aqui podriamos poner tambien la excepcion ProjectNotFoundException
        Optional<Project> projectData = projectRepository.findById(id);

        if(!projectData.isPresent()){
            throw new ProjectNotFoundException();
        }
        Project _project = projectData.get();
        _project.setName(updatedProject.getName());
        _project.setCommits(updatedProject.getCommits());
        projectRepository.save(_project);
    }

    @Operation(summary= "Delete project",
            description= "Delete a project",
            tags= { "projects", "delete" })

    @ApiResponses({
            @ApiResponse(responseCode = "204",
                    content= { @Content(schema = @Schema()) })
            ,@ApiResponse(responseCode = "400",
            content= { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404",
                    content= { @Content(schema = @Schema()) })})
    // DELETE http://localhost:8080/api/projects/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProject(@PathVariable Long id) {
        if(projectRepository.existsById(id)){ //comprobamos que el project existe
            projectRepository.deleteById(id);
        }
    }*/

}
