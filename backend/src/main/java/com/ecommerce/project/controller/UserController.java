package com.ecommerce.project.controller;

import com.ecommerce.project.model.User;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.UserDTO;
import com.ecommerce.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("/public/user/{userId}/image")
    public ResponseEntity<UserDTO> updateUserImage(@PathVariable Long userId
            , @RequestParam("image") MultipartFile image) throws IOException {
        UserDTO userDTO = userService.updateUserProfile(userId,image);
        return new ResponseEntity<UserDTO>(userDTO,HttpStatus.OK);

    }

}
