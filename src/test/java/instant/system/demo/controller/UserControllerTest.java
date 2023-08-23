package instant.system.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import instant.system.demo.model.Parking;
import instant.system.demo.model.Role;
import instant.system.demo.model.User;
import instant.system.demo.service.ParkingApiService;
import instant.system.demo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void getUsers() throws Exception
    {
        User u1 = new User( "Rachid", "rachid55", "password", List.of());
        User u2 = new User( "Rachida", "rachida55", "password", List.of());

        List<User> listUsers = Arrays.asList(u1, u2);

        when(userService.getUsers()).thenReturn(listUsers);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/user/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Rachid"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].username").value("rachid55"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].password").value("password"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Rachida"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].username").value("rachida55"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].password").value("password"));

        verify(userService, times(1)).getUsers();
        verifyNoMoreInteractions(userService);

    }

    @Test
    void saveUser() throws Exception {
        User u1 = new User( "Rachid", "rachid55", "password", List.of());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/user/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Rachid\",\"username\":\"rachid55\",\"password\":\"password\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        verify(userService, times(1)).saveUser(u1);
        verifyNoMoreInteractions(userService);
    }

    @Test
    void saveRole() throws Exception {
        Role r1 = new Role("ROLE_ADMIN");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/role/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"ROLE_ADMIN\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        verify(userService, times(1)).saveRole(r1);
        verifyNoMoreInteractions(userService);
    }

    @Test
    void addroletouserOK() throws Exception {
        this.saveUser();
        this.saveRole();
        when(userService.addRoleToUser("rachid55", "ROLE_ADMIN")).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/role/addroletouser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("username", "rachid55")
                        .param("roleName", "ROLE_ADMIN"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(userService, times(1)).addRoleToUser("rachid55", "ROLE_ADMIN");
        verifyNoMoreInteractions(userService);
    }

    @Test
    void addroletouserERROR() throws Exception {
        when(userService.addRoleToUser("rachid55", "ROLE_ADMIN")).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/role/addroletouser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("username", "rachid55")
                        .param("roleName", "ROLE_ADMIN"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        verify(userService, times(1)).addRoleToUser("rachid55", "ROLE_ADMIN");
        verifyNoMoreInteractions(userService);
    }



}
