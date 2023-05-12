package aiss.gitminer.controller;

import aiss.gitminer.model.User;
import aiss.gitminer.repository.UserRepository;
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
    public List<User> findAll() {
        return repository.findAll();
    }

    // GET http://localhost:8080/api/users/{id}
    // TODO: añadir la excepción
    @GetMapping("/{id}")
    public User findOne(@Parameter(description = "id of the user to be searched") @PathVariable long id) {
        Optional<User> user = repository.findById(id);
        return user.get();
    }
}
