package instant.system.demo.service;

import instant.system.demo.model.Role;
import instant.system.demo.model.User;
import instant.system.demo.repository.RoleRepository;
import instant.system.demo.repository.UserRepository;
import instant.system.demo.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    public void testSaveUser() {
        User user = new User("username", "password", "email@example.com", new ArrayList<>());
        when(passwordEncoder.encode(anyString())).thenReturn("encoded_password");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = userService.saveUser(user);

        assertThat(savedUser).isEqualTo(user);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testSaveRole() {
        Role role = new Role("ROLE_USER");
        when(roleRepository.save(any(Role.class))).thenReturn(role);

        Role savedRole = userService.saveRole(role);

        assertThat(savedRole).isEqualTo(role);
        verify(roleRepository, times(1)).save(role);
    }

    @Test
    public void testAddRoleToUserSuccess() {
        User user = new User("username", "password", "email@example.com", new ArrayList<>());
        Role role = new Role("ROLE_USER");
        when(userRepository.findByUsername(anyString())).thenReturn(user);
        when(roleRepository.findByName(anyString())).thenReturn(role);

        boolean result = userService.addRoleToUser("username", "ROLE_USER");

        assertThat(result).isTrue();
        assertThat(user.getRoles()).contains(role);
        verify(userRepository, times(1)).findByUsername("username");
        verify(roleRepository, times(1)).findByName("ROLE_USER");
    }

    @Test
    public void testAddRoleToUserInvalidUserOrRole() {
        when(userRepository.findByUsername(anyString())).thenReturn(null);
        when(roleRepository.findByName(anyString())).thenReturn(null);

        boolean result = userService.addRoleToUser("nonexistent", "INVALID_ROLE");

        assertThat(result).isFalse();
        verify(userRepository, times(1)).findByUsername("nonexistent");
        verify(roleRepository, times(1)).findByName("INVALID_ROLE");
    }

    @Test
    public void testGetUser() {
        User user = new User("username", "password", "email@example.com", new ArrayList<>());
        when(userRepository.findByUsername("username")).thenReturn(user);

        User retrievedUser = userService.getUser("username");

        assertThat(retrievedUser).isEqualTo(user);
        verify(userRepository, times(1)).findByUsername("username");
    }

    @Test
    public void testLoadUserByUsernameUserFound() {
        String username = "testuser";
        User mockUser = new User("name", "testuser", "password", List.of(new Role("ROLE_USER")));

        when(userRepository.findByUsername(username)).thenReturn(mockUser);

        UserDetails userDetails = userService.loadUserByUsername(username);

        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(username);
        assertThat(userDetails.getAuthorities()).isNotEmpty();
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    public void testLoadUserByUsernameUserNotFound() {
        String username = "nonexistent";
        when(userRepository.findByUsername(username)).thenReturn(null);

        assertThrows(
                UsernameNotFoundException.class,
                () -> userService.loadUserByUsername(username)
        );

        verify(userRepository, times(1)).findByUsername(username);
    }
}
