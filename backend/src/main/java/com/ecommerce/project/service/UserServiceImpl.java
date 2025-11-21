package com.ecommerce.project.service;

import com.ecommerce.project.exception.ResourceNotFoundException;
import com.ecommerce.project.model.User;
import com.ecommerce.project.payload.UserDTO;
import com.ecommerce.project.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDTO updateUserProfile(Long userId, MultipartFile image) throws IOException {
        User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","userId",userId));
        String fileName = fileService.uploadImage(path,image);
        user.setImage(fileName);
        userRepository.save(user);
        return modelMapper.map(user,UserDTO.class);
    }
}
