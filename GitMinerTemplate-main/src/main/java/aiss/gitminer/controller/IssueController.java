package aiss.gitminer.controller;

import aiss.gitminer.Exception.IssueNotFoundException;
import aiss.gitminer.model.Comment;
import aiss.gitminer.model.Commit;
import aiss.gitminer.model.Issue;
import aiss.gitminer.repository.CommentRepository;
import aiss.gitminer.repository.IssueRepository;
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

import java.util.List;
import java.util.Optional;

@Tag(name = "Issue", description = "Git Projects Issues Management API")
@RestController
@RequestMapping("/gitminer/issues")
public class IssueController {

    @Autowired
    IssueRepository issueRepository;

    @Autowired
    CommentRepository commentRepository;

    @Operation(
            summary = "Retrieve issues list",
            description =  "Get a list of issues",
            tags = {"get"}
    )

    // GET http://localhost:8080/gitminer/issues
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de issues", content = { @Content(schema = @Schema(implementation = Issue.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "No hay listado de issues", content = { @Content(schema = @Schema())})
    })
    @GetMapping()
    public List<Issue> findAll(
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String refId,
            @RequestParam(required = false) String title,
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

        Page<Issue> pageProjects;

        if (id == null && refId == null && title == null && webUrl == null)
            pageProjects = issueRepository.findAll(paging);
        else if (id != null)
            pageProjects = issueRepository.findById(id, paging);
        else if (refId != null)
            pageProjects = issueRepository.findByRefId(refId, paging);
        else if (title != null)
            pageProjects = issueRepository.findByTitle(title, paging);
        else
            pageProjects = issueRepository.findByWebUrl(webUrl, paging);

        return pageProjects.getContent();
    }
        @Operation(
            summary = "Retrieve issues list",
            description =  "Get a list of issues",
            tags = {"get"}
    )
    // GET http://localhost:8080/gitminer/issues/{id}
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de issues", content = { @Content(schema = @Schema(implementation = Issue.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "No hay listado de issues", content = { @Content(schema = @Schema())})
    })

    @GetMapping("/{id}")
    public Issue findById(@Parameter(description = "id of the issue to be searched") @PathVariable String id) throws IssueNotFoundException {
        Optional<Issue> issue = issueRepository.findById(id);
        if(!issue.isPresent()){
            throw new IssueNotFoundException();
        }
        return issue.get();
    }

    // GET http://localhost:8080/gitminer/issues?authorId=5122337

    @Operation(
            summary = "Retrieve issues list",
            description =  "Get a list of issues",
            tags = {"get"}
    )
    // GET http://localhost:8080/gitminer/issues/{id}
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de issues", content = { @Content(schema = @Schema(implementation = Issue.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "No hay listado de issues", content = { @Content(schema = @Schema())})
    })
    @GetMapping("/{id}/comments")
    public List<Comment> findCommentByIssueId(@Parameter(description = "id of issue whose comments are searched") @PathVariable String id) throws IssueNotFoundException{
        Optional<Issue> issue = issueRepository.findById(id);
        if(!issue.isPresent()){
            throw new IssueNotFoundException();
        }
        return issue.get().getComments();
    }


    @Operation(
            summary = "Retrieve all author issues",
            description = "Get all author issues by specifying the author Id",
            tags = {"get"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = { @Content(schema = @Schema(implementation = Issue.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Not Found", content = { @Content(schema = @Schema())})
    })
    // GET localhost:8080/gitminer/issues?author=:authorId
    @GetMapping(params = "id")
    public List<Issue> findByAuthor(@Parameter(description = "id of the issues' author") @RequestParam(name = "id") String authorId){
        List<Issue> issues = issueRepository.findAll();
        List<Issue> issuesByAuthor = issues.stream().filter(issue -> issue.getAuthor().getId().equals(authorId)).toList();
        return issuesByAuthor;
    }

    //GET localhost:8080/gitminer/issues?state=open/close
    @Operation(
            summary = "Retrieve all issues by state",
            description = "Get all issues whose state equals the state parameter specified",
            tags = {"get"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = { @Content(schema = @Schema(implementation = Issue.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Not Found", content = { @Content(schema = @Schema())})
    })
    @GetMapping(params = "state")
    public List<Issue> findIssueByState(@Parameter(description = "state of the issues to be searched") @RequestParam (name = "state") String state){
        List<Issue> issues = issueRepository.findAll();
        List<Issue> issuesByState = issues.stream().filter(issue -> issue.getState().equals(state)).toList();
        return issuesByState;
    }
}
    /*
    @GetMapping("?{authorId}")
    public List<Issue> getIssuesByAuthorId(@Parameter(description = "id of the author of the issues to be searched") @PathVariable long authorId) {
        List<Issue> issues = issueRepository.findAll();
        return issues.stream().filter(i -> i.getAuthor().getId().equals(authorId)).toList();
    }

    @GetMapping("?{state}")
    public List<Issue> getIssuesByState(@Parameter(description = "state of the issues to be searched") @PathVariable String state) {
        List<Issue> issues = issueRepository.findAll();
        return issues.stream().filter(i -> i.getState().equals(state)).toList();
    }

     */

