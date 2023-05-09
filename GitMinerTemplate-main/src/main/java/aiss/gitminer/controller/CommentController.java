package aiss.gitminer.controller;

import aiss.gitminer.Exception.CommentNotFoundException;
import aiss.gitminer.Exception.IssueNotFoundException;
import aiss.gitminer.model.Comment;
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
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Tag(name = "Comment", description = "Comment managementAPI")
@RestController
@RequestMapping("/gitminer")
public class CommentController {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    IssueRepository issueRepository;

    public CommentController(CommentRepository repository) {
        this.commentRepository = repository;
    }

    // GET http://localhost:8080/gitminer/comments
    @Operation(
            summary = "Retrieve a list of comments",
            description = "Get a list of comments",
            tags = { "comments", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de comments", content = {@Content(schema = @Schema(implementation = Comment.class), mediaType = "application/json")}),
    })
    @GetMapping("/comments/{id}")
    public List<Comment> findAll(@RequestParam(required = false) String author_id,
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
        }
        else
            paging = PageRequest.of(page, size);

        Page<Comment> pageProjects;

        if (author_id == null)
            pageProjects = commentRepository.findAll(paging);
        else
            pageProjects = commentRepository.findByAuthorID(author_id, paging);

        return pageProjects.getContent();
    }

    // GET http://localhost:8080/gitminer/comments/{id}
    @Operation(
            summary = "Retrieve a Comment by Id",
            description = "Get a Comment object by specifying its id",
            tags = { "comments", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Comment encontrado", content = {@Content(schema = @Schema(implementation = Comment.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Comment no encontrado", content = {@Content(schema = @Schema())})
    })
    @GetMapping("/comments/{id}")
    public Comment findOne(@Parameter(description = "id of the comment to be searched") @PathVariable Long id) throws CommentNotFoundException {
        Optional<Comment> comment = commentRepository.findById(id);
        if (!comment.isPresent()) {
            throw new CommentNotFoundException();
        }
        return comment.get();
    }

    // GET http://localhost:8080/gitminer/issues/{id}/comments
    @Operation(
            summary = "Retrieve a Comment by the Id of an Issue",
            description = "Get a Comment object by specifying the Id of an Issue",
            tags = {"comments", "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Comment encontrado", content = {@Content(schema = @Schema(implementation = Comment.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Issue no encontrado", content = {@Content(schema = @Schema())})
    })
    @GetMapping("/issues/{id}/comments")
    public List<Comment> findIssuesComment(@Parameter(description = "id of the issue to be searched for obtain the comment") @PathVariable Long id) throws IssueNotFoundException {
        Optional<Issue> issue = issueRepository.findById(id);
        if (!issue.isPresent()) {
            throw new IssueNotFoundException();
        }

        List<Comment> comments = new ArrayList<>(issue.get().getComments());
        return comments;

    }

}
