package in.reinventing.cashrich.controller;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.reinventing.cashrich.dtos.JwtRequest;
import in.reinventing.cashrich.dtos.JwtResponse;
import in.reinventing.cashrich.dtos.UserDTO;
import in.reinventing.cashrich.entities.User;
import in.reinventing.cashrich.exception.UserAlreadyPresentException;
import in.reinventing.cashrich.exception.UserDisableException;
import in.reinventing.cashrich.exception.UserDoesNotExistsException;
import in.reinventing.cashrich.repository.UserRepository;
import in.reinventing.cashrich.securityconfig.JwtTokenUtil;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@Valid @RequestBody JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDTO userDTO) throws Exception {
        
    	if (userRepository.findByUsernameOrEmail(userDTO.getUsername(),userDTO.getEmail()).isPresent()) {
           // return ResponseEntity.badRequest().body("Username is already taken");
    		throw new UserAlreadyPresentException();
        }

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setMobile(userDTO.getMobile());

        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }
    
    @PostMapping("/update")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserDTO userDTO) throws Exception {
        Optional<User> optionalUser=userRepository.findByUsernameOrEmail(userDTO.getUsername(),userDTO.getEmail());
    	if (!optionalUser.isPresent()) {
    		throw new UserDoesNotExistsException();
        }

        User user = optionalUser.get();
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setMobile(userDTO.getMobile());

        userRepository.save(user);
        return ResponseEntity.ok("User updated successfully");
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new UserDisableException();
        } catch (BadCredentialsException | UsernameNotFoundException e) {
            throw new UsernameNotFoundException("USER NOT FOUND!");
        }catch (Exception e) {
            throw new Exception("AUTH_FAILED", e);
        }
    }
}
