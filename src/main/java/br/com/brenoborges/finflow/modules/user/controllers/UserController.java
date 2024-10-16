package br.com.brenoborges.finflow.modules.user.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.brenoborges.finflow.modules.user.dtos.ProfileRequestDTO;
import br.com.brenoborges.finflow.modules.user.entities.UserEntity;
import br.com.brenoborges.finflow.modules.user.useCases.CreateUserUseCase;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CreateUserUseCase createUserUseCase;

    @PostMapping("/signIn")
    public ResponseEntity<Object> signIn(@Valid @RequestBody ProfileRequestDTO profileRequestDTO) {
        UserEntity newUser = new UserEntity(profileRequestDTO);

        try {
            UserEntity result = this.createUserUseCase.execute(newUser);
            return ResponseEntity.created(null).body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
