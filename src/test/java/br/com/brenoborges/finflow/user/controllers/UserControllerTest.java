package br.com.brenoborges.finflow.user.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Optional;

import static org.hamcrest.Matchers.is;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.brenoborges.finflow.modules.user.controllers.UserController;
import br.com.brenoborges.finflow.modules.user.dtos.ProfileRequestDTO;
import br.com.brenoborges.finflow.modules.user.dtos.ProfileResponseDTO;
import br.com.brenoborges.finflow.modules.user.entities.UserEntity;
import br.com.brenoborges.finflow.modules.user.repositories.UserRepository;
import br.com.brenoborges.finflow.modules.user.useCases.CreateUserUseCase;
import br.com.brenoborges.finflow.utils.TestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UserControllerTest {

    private MockMvc mvc; // Instância MockMvc que vai simular as requisições Http

    @Autowired
    private WebApplicationContext context; // Contexto da aplicação web

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity()) // Aplica mock do spring security
                .build();
    }

    @Mock
    private UserRepository userRepository;

    @Mock
    private CreateUserUseCase createUserUseCase;

    @InjectMocks
    private UserController userController;

    private ProfileRequestDTO profileRequestDTO = new ProfileRequestDTO("Zezinho", "email@email.com", 30, "123456",
            "Male");

    private ProfileResponseDTO profileResponseDTO = ProfileResponseDTO.builder()
            .age(profileRequestDTO.age())
            .email(profileRequestDTO.email())
            .gender(profileRequestDTO.gender())
            .name(profileRequestDTO.name())
            .build();

    private UserEntity user = new UserEntity(profileRequestDTO);

    @Test
    @DisplayName("Should be able to create a new user")
    public void shouldBeAbleToCreateANewUser() throws Exception {

        when(this.createUserUseCase.execute(profileRequestDTO)).thenReturn(profileResponseDTO);

        mvc.perform(MockMvcRequestBuilders.post("/user/signUp")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.objectToJSON(profileRequestDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.name", is(profileResponseDTO.getName())))
                .andExpect(jsonPath("$.email", is(profileResponseDTO.getEmail())))
                .andExpect(jsonPath("$.age", is(profileResponseDTO.getAge())))
                .andExpect(jsonPath("$.gender", is(profileResponseDTO.getGender())));
    }

    @Test
    @DisplayName("Should be return bad request status when user already exists")
    public void shouldReturnBadRequestStatuswhenUserAlreadyExists() throws Exception {

        this.createUserUseCase.execute(profileRequestDTO);

        when(this.userRepository.findByEmail(profileRequestDTO.email())).thenReturn(Optional.of(user));

        mvc.perform(MockMvcRequestBuilders.post("/user/signUp")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.objectToJSON(profileRequestDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$", is("E-mail já cadastrado no sistema!")));
    }
}
