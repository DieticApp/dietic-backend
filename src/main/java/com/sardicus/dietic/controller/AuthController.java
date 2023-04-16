package com.sardicus.dietic.controller;

import com.sardicus.dietic.dto.LoginDto;
import com.sardicus.dietic.dto.RegisterDto;
import com.sardicus.dietic.entity.Dietitian;
import com.sardicus.dietic.entity.Patient;
import com.sardicus.dietic.entity.Role;
import com.sardicus.dietic.entity.User;
import com.sardicus.dietic.exception.APIException;
import com.sardicus.dietic.repo.DietitianRepo;
import com.sardicus.dietic.repo.PatientRepo;
import com.sardicus.dietic.repo.RoleRepo;
import com.sardicus.dietic.repo.UserRepo;
import com.sardicus.dietic.response.JWTAuthResponse;
import com.sardicus.dietic.security.JwtTokenProvider;
import com.sardicus.dietic.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepo userRepository;
    private final DietitianRepo dietitianRepo;
    private final PatientRepo patientRepo;
    private final RoleRepo roleRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider tokenProvider;
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto loginDto ){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JWTAuthResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterDto registerDto){
        if(userRepository.existsByEmail(registerDto.getEmail())){
            throw new APIException(HttpStatus.BAD_REQUEST, "Email is already exists!.");
        }

        User user = new User();
        user.setName(registerDto.getName());
        user.setSurname(registerDto.getSurname());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        if (registerDto.getRoleName().equals("ROLE_DIETITIAN")) {
            Dietitian dietitian = new Dietitian();
            dietitian.setName(registerDto.getName());
            dietitian.setSurname(registerDto.getSurname());
            dietitian.setEmail(registerDto.getEmail());
            dietitianRepo.save(dietitian);

            Role roles = roleRepository.findByName("ROLE_DIETITIAN").get();
            user.setRoles(Collections.singleton(roles));
        }
        else if (registerDto.getRoleName().equals("ROLE_PATIENT")) {
            Patient patient = new Patient();
            patient.setName(registerDto.getName());
            patient.setSurname(registerDto.getSurname());
            patient.setDietitian(dietitianRepo.getReferenceById(registerDto.getDietitianId()));
            patientRepo.save(patient);

            Role roles = roleRepository.findByName("ROLE_PATIENT").get();
            user.setRoles(Collections.singleton(roles));
        }
        userRepository.save(user);

        return new ResponseEntity<>("authService.register(registerDto)", HttpStatus.OK);
    }
}
