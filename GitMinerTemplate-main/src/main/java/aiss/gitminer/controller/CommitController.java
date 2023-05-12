package aiss.gitminer.controller;

import aiss.gitminer.Exception.CommitNotFoundException;
import aiss.gitminer.model.Commit;
import aiss.gitminer.model.Project;
import aiss.gitminer.repository.CommitRepository;
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
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Tag(name= "Commit", description= "Commit management API")
@RestController
@RequestMapping("/gitminer/commits")
public class CommitController {

   // @Autowired
    //ProjectRepository projectRepository;
    @Autowired
    CommitRepository commitRepository;

    @Operation(summary= "Retrieve commit list",
            description= "Get a list of commits ",
            tags= {"get" })

    @ApiResponses({
            @ApiResponse(responseCode = "200", description= "Listado de commits",content= { @Content(schema = @Schema(implementation = Commit.class), mediaType= "application/json") })
           // ,@ApiResponse(responseCode = "404", description="Commit no encontrado",content= { @Content(schema = @Schema()) })
            })

    @GetMapping() //"/projects/{projectId}/commits"
    public List<Commit> findAll(){
        /*
            @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size,
                                                 @RequestParam(required= false) long projectId,

                                                 @RequestParam(required = false) String order) {
        Pageable paging;
        Page<Project> pageProject;
        if(order!= null) {
            if(order.startsWith("-"))
                paging= PageRequest.of(page, size, Sort.by(order.substring(1)).descending());
            else
                paging= PageRequest.of(page, size, Sort.by(order).ascending());
        }
        else
            paging= PageRequest.of(page, size);

        pageProject= projectRepository.findByIddd(projectId, paging);
        Project project= (Project) pageProject.get().filter(p -> p.getId().equals(projectId));
        return project.getCommits();

         */
        return commitRepository.findAll();
    }


    @Operation(summary= "Retrieve commit Id",
            description= "Get commit",
            tags= { "get" })

    @ApiResponses({
            @ApiResponse(responseCode = "200", description= "Commit por id",
                    content= { @Content(schema = @Schema(implementation = Commit.class), mediaType= "application/json") })
            ,@ApiResponse(responseCode = "404", description="Commit no encontrado",
            content= { @Content(schema = @Schema()) })})

    @GetMapping("/{id}") //"/commits/{id}"
    public Commit findOne(@Parameter(description= "id of commit to be searched") @PathVariable(value="id") String id) throws CommitNotFoundException {
        Optional<Commit> commit = commitRepository.findById(id);
        if(!commit.isPresent()){
            throw new CommitNotFoundException();
        }
        return commit.get();
    }

}
