package aiss.gitminer.controller;

import aiss.gitminer.model.Issue;
import aiss.gitminer.model.User;
import aiss.gitminer.repository.UserRepository;
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

@Tag(name = "User", description = "Git Users Management API")
@RestController
@RequestMapping("gitminer/users")
public class UserController {

    @Autowired
    UserRepository repository;

    // GET http://localhost:8080/api/users
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema())})
    })
    @GetMapping
    public List<User> findAll(
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String avatarUrl,
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

        Page<User> pageProjects;

        if (id == null && username == null && name == null && avatarUrl == null && webUrl == null)
            pageProjects = repository.findAll(paging);
        else if (id != null)
            pageProjects = repository.findById(id, paging);
        else if (username != null)
            pageProjects = repository.findByUsername(username, paging);
        else if (name != null)
            pageProjects = repository.findByName(name, paging);
        else if (avatarUrl != null)
            pageProjects = repository.findByAvatarUrl(avatarUrl, paging);
        else
            pageProjects = repository.findByWebUrl(webUrl, paging);

        return pageProjects.getContent();
    }

    // GET http://localhost:8080/api/users/{id}
    // TODO: añadir la excepción
    @GetMapping("/{id}")
    public User findOne(@Parameter(description = "id of the user to be searched") @PathVariable String id) {
        Optional<User> user = repository.findById(id);
        return user.get();
    }
}
