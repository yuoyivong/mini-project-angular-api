package com.example.springminiproject.service.serviceImp;

import com.example.springminiproject.exception.AlreadyExistsException;
import com.example.springminiproject.exception.BlankFieldException;
import com.example.springminiproject.exception.GlobalExceptionHandler;
import com.example.springminiproject.exception.RegexPatternException;
import com.example.springminiproject.model.User;
import com.example.springminiproject.repository.UserRepository;
import com.example.springminiproject.request.UserRequest;
import com.example.springminiproject.response.dto.UserDTO;
import com.example.springminiproject.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImp(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO createNewUser(UserRequest userRequest) {
        if(userRequest.getEmail().isEmpty()) {
            throw new BlankFieldException("Email is required.");
        }

        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setAddress(userRequest.getAddress());
        user.setEmail(userRequest.getEmail());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setCreatedAt(LocalDateTime.now());

        return userRepository.save(user).userDTOResponse();
    }

    @Override
    public List<UserDTO> getAllUsers(int pageNo, int pageSize, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<User> userPage = userRepository.findAll(pageable);

        return userPage.stream().map(User::userDTOResponse).collect(Collectors.toList());

    }

    @Override
    public UserDTO getUserByUserId(Long id) {

        return userRepository.findById(id).map(User::userDTOResponse).orElseThrow();

    }

    @Override
    public void deleteUserByUserId(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void updateUserById(Long id, UserRequest userRequest) throws Exception {

        updateUserValidation(userRequest);

        try {
            userRepository.updateUserByUserId(id,
                    userRequest.getUsername(),
                    userRequest.getEmail(),
                    userRequest.getAddress(),
                    userRequest.getPhoneNumber(),
                    passwordEncoder.encode(userRequest.getPassword()),
                    String.valueOf(userRequest.getRole()));
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }


    }

    @Override
    public UserDTO findUserByEmail(String email) {
        return userRepository.findUserByEmail(email).map(User::userDTOResponse).orElseThrow();
    }

    private void updateUserValidation(UserRequest userRequest) {

//        email field checked
        if(userRequest.getEmail().isBlank()) {
            throw new BlankFieldException("Email is required.");
        }

        if(!userRequest.getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$")) {
            throw new RegexPatternException("Invalid email address.");
        }

//        phone number field checked
        if(userRequest.getPhoneNumber() != null && !userRequest.getPhoneNumber().matches("^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}$")) {
            throw new RegexPatternException("Phone number should follow this format: 012-333-4321 or 012 333 4321.");
        }

//        password checked
        if(userRequest.getPassword().isBlank()) {
            throw new BlankFieldException("Password is required.");
        }

        if(!userRequest.getPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
            throw new RegexPatternException("Minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character.");
        }

//        role checked - pending
//        if(userRequest.getRole() == null) {
//            throw new BlankFieldException("Role is required.");
//        }
//
//        if(!userRequest.getRole().equals(Role.AUTHOR)
//                && !userRequest.getRole().equals(Role.READER)) {
//            throw new InvalidFieldException("You can choose to be a READER or an AUTHOR.");
//        }
//        try {
//            Role role = Role.valueOf(userRequest.getRole().toString().toUpperCase());
//            System.out.println("Role :  " + role);
//        } catch (InvalidFieldException e) {
//            throw new InvalidFieldException("You can choose to be a READER or an AUTHOR.");
//        }

    }
}
