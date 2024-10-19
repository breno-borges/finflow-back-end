package br.com.brenoborges.finflow.modules.user.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.brenoborges.finflow.exceptions.UserNotFoundException;
import br.com.brenoborges.finflow.modules.user.dtos.ProfileResponseDTO;
import br.com.brenoborges.finflow.modules.user.entities.UserEntity;
import br.com.brenoborges.finflow.modules.user.repositories.UserRepository;

@Service
public class UserProfileUseCase {

    @Autowired
    private UserRepository userRepository;

    public ProfileResponseDTO execute(UUID idUser) {

        UserEntity user = this.userRepository.findById(idUser)
                .orElseThrow(() -> {
                    throw new UserNotFoundException();
                });

        return ProfileResponseDTO.builder()
                .name(user.getName())
                .email(user.getEmail())
                .age(user.getAge())
                .gender(user.getGender())
                .build();
    }
}
