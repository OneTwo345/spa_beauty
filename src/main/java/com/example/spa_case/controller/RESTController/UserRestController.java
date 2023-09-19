package com.example.spa_case.controller.RESTController;


import com.example.spa_case.service.userService.UserService;
import com.example.spa_case.service.userService.response.UserListResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("api/users")
@AllArgsConstructor
public class UserRestController {
    private final UserService userService;
    @GetMapping
    public ResponseEntity<Page<UserListResponse>> list(@PageableDefault(size = 5) Pageable pageable,
                                                       @RequestParam(defaultValue = "") String search

    ) {
        return new ResponseEntity<>(userService.getAll(search,pageable), HttpStatus.OK);
    }
    @DeleteMapping("/{Id}")
    public ResponseEntity<String> deleteRoom(@PathVariable Long Id) {
        userService.deleteById(Id);
        return ResponseEntity.ok("Room deleted successfully");
    }
}