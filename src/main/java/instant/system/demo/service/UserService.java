package instant.system.demo.service;

import instant.system.demo.controller.ParkingApiController;
import instant.system.demo.model.Role;
import instant.system.demo.model.User;
import instant.system.demo.repository.RoleRepository;
import instant.system.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    public User saveUser(User u)
    {
        LOGGER.info("saving new user to the database {}", u);
        u.setPassword(passwordEncoder.encode(u.getPassword()));
        return userRepository.save(u);
    }
    public Role saveRole(Role r)
    {
        LOGGER.info("saving new role to the database {}", r);
        return roleRepository.save(r);
    }

    public void addRoleToUser(String un, String r)
    {
        User u = userRepository.findByUsername(un);
        Role role = roleRepository.findByName(r);
        LOGGER.info("add role {}, to user {}", un, r);
        u.getRoles().add(role);
        // we dont need to save u again because we have @Transactional
    }
    public List<User> getUsers()
    {
        LOGGER.info("get Users");
        return userRepository.findAll();
    }

    public User getUser(String username)
    {
        LOGGER.info("get User {}", username);
        return userRepository.findByUsername(username);
    }


    @Override
    public UserDetails loadUserByUsername(String un) throws UsernameNotFoundException {
        User u = userRepository.findByUsername(un);
        if(u == null)
            LOGGER.warn("User not found {}", un);
        else
            LOGGER.info("User found {}", un);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        u.getRoles().forEach(role ->
        {authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(u.getUsername(), u.getPassword(), authorities);
    }
}
