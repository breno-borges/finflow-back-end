package br.com.brenoborges.finflow.modules.user.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.brenoborges.finflow.exceptions.UserNotFoundException;
import br.com.brenoborges.finflow.modules.user.dtos.UpdateUserRequestDTO;
import br.com.brenoborges.finflow.modules.user.entities.UserEntity;
import br.com.brenoborges.finflow.modules.user.repositories.UserRepository;

@Service
public class UpdateUserUseCase {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public UserEntity execute(UUID idUser, UpdateUserRequestDTO updateUserRequestDTO) {
        UserEntity user = this.userRepository.findById(idUser)
                .orElseThrow(() -> {
                    throw new UserNotFoundException();
                });

        user.setIdUser(user.getIdUser());
        user.setName(updateUserRequestDTO.name());
        user.setAge(updateUserRequestDTO.age());
        user.setGender(updateUserRequestDTO.gender());
        user.setEmail(user.getEmail());
        user.setPassword(user.getPassword());
        user.setResetPasswordToken(user.getResetPasswordToken());
        user.setActive(user.isActive());
        user.setCreatedAt(user.getCreatedAt());

        return this.userRepository.save(user);
    }
}