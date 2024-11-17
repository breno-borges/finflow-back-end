package br.com.brenoborges.finflow.modules.user.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.brenoborges.finflow.exceptions.UserFoundException;
import br.com.brenoborges.finflow.modules.user.dtos.ProfileRequestDTO;
import br.com.brenoborges.finflow.modules.user.dtos.ProfileResponseDTO;
import br.com.brenoborges.finflow.modules.user.entities.UserEntity;
import br.com.brenoborges.finflow.modules.user.repositories.UserRepository;

@Service
public class CreateUserUseCase {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public ProfileResponseDTO execute(ProfileRequestDTO profileRequestDTO) {

        this.userRepository.findByEmail(profileRequestDTO.email())
                .ifPresent((email) -> {
                    throw new UserFoundException();
                });

        UserEntity user = new UserEntity(profileRequestDTO);

        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        user.setActive(true);

        this.userRepository.save(user);

        return ProfileResponseDTO.builder()
                .name(user.getName())
                .email(user.getEmail())
                .age(user.getAge())
                .gender(user.getGender())
                .build();
    }

}
