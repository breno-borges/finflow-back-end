package br.com.brenoborges.finflow.user.useCases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.brenoborges.finflow.exceptions.UserFoundException;
import br.com.brenoborges.finflow.modules.user.dtos.ProfileRequestDTO;
import br.com.brenoborges.finflow.modules.user.dtos.ProfileResponseDTO;
import br.com.brenoborges.finflow.modules.user.entities.UserEntity;
import br.com.brenoborges.finflow.modules.user.repositories.UserRepository;
import br.com.brenoborges.finflow.modules.user.useCases.CreateUserUseCase;

@ExtendWith(MockitoExtension.class)
public class UserUseCasesTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CreateUserUseCase createUserUseCase;

    @Test
    @DisplayName("Should be able to create a new user")
    public void shouldBeAbleToCreateANewUser() throws Exception {

        ProfileRequestDTO profileRequestDTO = new ProfileRequestDTO(
                UUID.fromString("123e4567-e89b-12d3-a456-426614174000"), "Zezinho", "email@email.com", 30, "123456",
                LocalDateTime.now(), "Male");

        when(this.userRepository.findByEmail(profileRequestDTO.email())).thenReturn(Optional.empty());
        when(this.passwordEncoder.encode(profileRequestDTO.password())).thenReturn("Password Encoded");

        ProfileResponseDTO response = this.createUserUseCase.execute(profileRequestDTO);

        // Verifica o retorno do método
        assertThat(response).isNotNull();
        assertThat(response.getEmail()).isEqualTo(profileRequestDTO.email());
        assertThat(response.getGender()).isEqualTo(profileRequestDTO.gender());
        assertThat(response.getAge()).isEqualTo(profileRequestDTO.age());
        assertThat(response.getName()).isEqualTo(profileRequestDTO.name());

        // Verifica se o save foi chamado só uma vez
        verify(this.userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    @DisplayName("Should not be able to create a new user if the user already exists")
    public void shouldNotBeAbleToCreateANewUser() throws Exception {
        ProfileRequestDTO profileRequestDTO = new ProfileRequestDTO(
                UUID.fromString("123e4567-e89b-12d3-a456-426614174000"), "Zezinho", "email@email.com", 30, "123456",
                LocalDateTime.now(), "Male");

        when(this.userRepository.findByEmail(profileRequestDTO.email())).thenReturn(Optional.of(new UserEntity()));

        assertThatThrownBy(() -> {
            this.createUserUseCase.execute(profileRequestDTO);
        })
                .isInstanceOf(UserFoundException.class);

        verify(this.userRepository, times(0)).save(any(UserEntity.class));
    }
}
