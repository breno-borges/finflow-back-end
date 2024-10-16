package br.com.brenoborges.finflow.modules.user.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.brenoborges.finflow.exceptions.UserFoundException;
import br.com.brenoborges.finflow.modules.user.entities.UserEntity;
import br.com.brenoborges.finflow.modules.user.repositories.UserRepository;

@Service
public class CreateUserUseCase {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public UserEntity execute(UserEntity userEntity) {
        this.userRepository.findByEmail(userEntity.getEmail())
                .ifPresent((email) -> {
                    throw new UserFoundException();
                });

        String password = passwordEncoder.encode(userEntity.getPassword());
        userEntity.setPassword(password);
        userEntity.setActive(true);

        return this.userRepository.save(userEntity);
    }

}
