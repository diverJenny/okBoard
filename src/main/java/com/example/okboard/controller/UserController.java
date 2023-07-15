package com.example.okboard.controller;

import com.example.okboard.dto.UserDTO;
import com.example.okboard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    // 전체 사용자 조회
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDTO>> getUsers() throws Exception {
        List<UserDTO> dtoList = userService.getUsers();
        return ResponseEntity.ok(dtoList);
    }

    // 사용자 조회
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUsers(@PathVariable("id") Long id) throws Exception{
        UserDTO user = userService.findUserById(id);
        return ResponseEntity.ok(user);
    }


    // 사용자 수정
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable("id") Long id, @RequestBody UserDTO userDto) throws Exception {
        UserDTO existingUser = userService.findUserById(id);

        existingUser.setName(userDto.getName());
        existingUser.setPassword(userDto.getPassword());
        existingUser.setUpdatedAt(LocalDateTime.now());

        userService.updateUser(existingUser);

        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    //사용자 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) throws Exception {
        userService.deleteUser(id);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }
}