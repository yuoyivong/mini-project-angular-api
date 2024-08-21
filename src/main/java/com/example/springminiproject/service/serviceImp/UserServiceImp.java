package com.example.springminiproject.service.serviceImp;

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
    public void updateUserById(Long id, UserRequest userRequest) {

        userRepository.updateUserByUserId(id,
                userRequest.getUsername(),
                userRequest.getEmail(),
                userRequest.getAddress(),
                userRequest.getPhoneNumber(),
                passwordEncoder.encode(userRequest.getPassword()),
                String.valueOf(userRequest.getRole()));
    }

    @Override
    public UserDTO findUserByEmail(String email) {
        return userRepository.findUserByEmail(email).map(User::userDTOResponse).orElseThrow();
    }

}
