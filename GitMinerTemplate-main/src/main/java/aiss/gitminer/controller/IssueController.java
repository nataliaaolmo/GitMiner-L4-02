package aiss.gitminer.controller;

import aiss.gitminer.model.Issue;
import aiss.gitminer.repository.IssueRepository;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Tag(name = "Issue", description = "Git Projects Issues Management API")
@RestController
@RequestMapping("/gitminer/issues")
public class IssueController {

    @Autowired
    IssueRepository issueRepository;

    // GET http://localhost:8080/gitminer/issues
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Issue.class))}),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema())})
    })
    @GetMapping
    public List<Issue> findAllIssues() {
        return issueRepository.findAll();
    }

    // GET http://localhost:8080/gitminer/issues/{id}
    // TODO: añadir la excepción
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Issue.class))}),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema())})
    })
    @GetMapping("/{id}")
    public Issue findOneIssueById(@Parameter(description = "id of the issue to be searched") @PathVariable long id) {
        Optional<Issue> issue = issueRepository.findById(id);
        return issue.get();
    }

    // GET http://localhost:8080/gitminer/issues?authorId=5122337
    // TODO: revisar, no sé si se hace así
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Issue.class))}),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema())})
    })
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

}
