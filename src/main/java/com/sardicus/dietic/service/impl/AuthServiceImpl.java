package com.sardicus.dietic.service.impl;

import com.google.firebase.auth.FirebaseAuthException;
import com.sardicus.dietic.dto.*;
import com.sardicus.dietic.entity.*;
import com.sardicus.dietic.exception.APIException;
import com.sardicus.dietic.repo.*;
import com.sardicus.dietic.response.JWTAuthResponse;
import com.sardicus.dietic.security.JwtTokenProvider;
import com.sardicus.dietic.service.AuthService;
import com.sardicus.dietic.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.ExecutionException;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final MessageService messageService;
    private final UserRepo userRepository;
    private final DietitianRepo dietitianRepo;
    private final PatientRepo patientRepo;
    private final RoleRepo roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final WeightRepo weightRepo;



    @Override
    public JWTAuthResponse login(LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        String role = userRepository.findByEmail(loginDto.getEmail()).get().getRoles().stream().toList().get(0).getName();
        String name = userRepository.findByEmail(loginDto.getEmail()).get().getName();
        String surname = userRepository.findByEmail(loginDto.getEmail()).get().getSurname();


        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        jwtAuthResponse.setName(name);
        jwtAuthResponse.setSurname(surname);
        jwtAuthResponse.setRoleName(role);
        jwtAuthResponse.setEmail(loginDto.getEmail());
        if (role.equals("ROLE_DIETITIAN")) jwtAuthResponse.setId(dietitianRepo.findByEmail(loginDto.getEmail()).get().getDietitian_id());
        else if(role.equals("ROLE_PATIENT")) {
            jwtAuthResponse.setId(patientRepo.findByEmail(loginDto.getEmail()).get().getPatient_id());
            jwtAuthResponse.setDietitianId(patientRepo.findByEmail(loginDto.getEmail()).get().getDietitian().getDietitian_id());
        }
        return  jwtAuthResponse;
    }



    public JWTAuthResponse register(RegisterDto registerDto) throws ExecutionException, InterruptedException, FirebaseAuthException {
        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();

        if(userRepository.existsByEmail(registerDto.getEmail())){
            throw new APIException(HttpStatus.BAD_REQUEST, "Email already exists!.");
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

            jwtAuthResponse.setId(dietitian.getDietitian_id());
        }
        else if (registerDto.getRoleName().equals("ROLE_PATIENT")) {
            Patient patient = new Patient();
            patient.setName(registerDto.getName());
            patient.setSurname(registerDto.getSurname());
            patient.setDietitian(dietitianRepo.getReferenceById(registerDto.getDietitianId()));
            patient.setEmail(registerDto.getEmail());

            Optional.ofNullable(registerDto.getAge()).ifPresent(patient::setAge);
            Optional.ofNullable(registerDto.getHeight()).ifPresent(patient::setHeight);
            Weight weightData = new Weight();
            Optional.ofNullable(registerDto.getWeight())
                    .ifPresent(weight -> {
                        patient.setWeight(weight);
                        weightData.setWeight(patient.getWeight());
                        weightData.setDate(LocalDate.now());
                    });

            Optional.ofNullable(registerDto.getBodyFat()).ifPresent(patient::setBodyFat);
            Optional.ofNullable(registerDto.getAbout()).ifPresent(patient::setAbout);
            patientRepo.save(patient);
            if (weightData.getWeight() != null){
                weightData.setPatient(patient);
                weightRepo.save(weightData);
            }
            Role roles = roleRepository.findByName("ROLE_PATIENT").get();
            user.setRoles(Collections.singleton(roles));

            RoomDto roomDto = new RoomDto();
            ArrayList<String> users = new ArrayList<>();
            users.add(patient.getEmail());
            users.add(patient.getDietitian().getEmail());
            roomDto.setUsers(users);

            String message = "Hello, How can i help you?";
            roomDto.setLast_message(message);


            MessageDto messageDto = new MessageDto();
            messageDto.setMessage(message);
            messageDto.setSent_by(patient.getDietitian().getEmail());



            messageService.saveToRooms(roomDto,messageDto);

            jwtAuthResponse.setId(patient.getPatient_id());
            jwtAuthResponse.setDietitianId(patient.getDietitian().getDietitian_id());
        }
        userRepository.save(user);

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                registerDto.getEmail(), registerDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        LoginDto firebaseRegister = new LoginDto();
        firebaseRegister.setEmail(registerDto.getEmail());
        firebaseRegister.setPassword(registerDto.getPassword());
        firebaseRegister.setName(registerDto.getName() + " " + registerDto.getSurname());
        messageService.registerInFirebase(firebaseRegister);

        FirestoreDto firestoreDto = new FirestoreDto();
        firestoreDto.setEmail(registerDto.getEmail());
        firestoreDto.setPassword(registerDto.getPassword());
        firestoreDto.setName(registerDto.getName() + " " + registerDto.getSurname());

        jwtAuthResponse.setFirebaseResponse(messageService.saveToUsers(firestoreDto));
        jwtAuthResponse.setAccessToken(token);
        jwtAuthResponse.setName(registerDto.getName());
        jwtAuthResponse.setSurname(registerDto.getSurname());
        jwtAuthResponse.setRoleName(registerDto.getRoleName());
        jwtAuthResponse.setEmail(registerDto.getEmail());


        return jwtAuthResponse;
    }



}