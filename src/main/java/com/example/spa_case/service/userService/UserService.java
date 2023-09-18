package com.example.spa_case.service.userService;

import com.example.spa_case.controller.exception.ResourceNotFoundException;
import com.example.spa_case.model.User;
import com.example.spa_case.repository.UserRepository;
import com.example.spa_case.service.userService.response.UserListResponse;
import com.example.spa_case.util.AppMessage;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    public Page<UserListResponse> getAll(String search , Pageable pageable) {
        search = "%"+search+"%";
        return userRepository.searchAllUser(search, pageable)
                .map(book -> UserListResponse.builder()
                        .id(book.getId())
                        .name(book.getName())
                        .email(book.getEmail())
                        .phone(book.getPhone())
                        .dob(book.getDob())
                        .statusCustomer(String.valueOf(book.getStatusCustomer()))
                        .build());
    }
    public User findById(Long id){
        //de tai su dung
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException
                        (String.format(AppMessage.ID_NOT_FOUND, "User", id)));
    }
    public void deleteById(Long id){
        User user = findById(id);
        userRepository.delete(user);
    }
}