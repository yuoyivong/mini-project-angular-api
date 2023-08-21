package com.example.library_management.service.ServiceImp;

import com.example.library_management.model.dto.UserDTO;
import com.example.library_management.model.entity.User;
import com.example.library_management.repository.UserRepository;
import com.example.library_management.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;

@Service
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    User user = new User();
    UserDTO userDTO = new UserDTO();

    public UserServiceImp(UserRepository userRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.modelMapper = mapper;
    }

    @Override
    public UserDTO getUserById(Integer user_id) {
//        user = userRepository.findUserById(user_id);
//        var optUser = userRepository.findById(user_id);
//
//        if (optUser.isEmpty()){
//            throw new NotFoundException("cannot find user");
//        }

        user = userRepository.findById(user_id)
                .orElseThrow( ()->  new NotFoundException("cannot find user"));
        userDTO = modelMapper.map(user, UserDTO.class);
        return userDTO;
    }

    public void test(){
        userRepository.save(new User());

        List<User> users = userRepository.findAll();

        Page<User> users2 = userRepository.findAll(PageRequest.of(1,5));

        // name: "asd", email: "asd@gmail.com"
        User find = User.builder()
                .username("asd")
                .email("asd@gmail.com")
                .build();
        Example<User> example = Example.of(find);
        List<User> users3 = userRepository.findAll(example);

    }

}
