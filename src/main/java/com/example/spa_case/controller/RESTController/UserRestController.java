package com.example.spa_case.RESTController;


import com.example.spa_case.model.Customer;
import com.example.spa_case.model.User;
import com.example.spa_case.service.UserService.UserService;
import com.example.spa_case.service.UserService.request.UserSaveRequest;
import com.example.spa_case.service.UserService.response.UserDetailResponse;
import com.example.spa_case.service.UserService.response.UserListResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
@AllArgsConstructor
public class UserRestController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getUserList() {
        List<User> userList = userService.getUserList();
        return ResponseEntity.ok(userList);
    }

    @PostMapping
    public void create(@RequestBody UserSaveRequest request){
        userService.create(request);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<UserDetailResponse> findById(@PathVariable Long id){
//        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
//    }
//    @GetMapping
//    public ResponseEntity<Page<UserListResponse>> getUser(@RequestParam(defaultValue = "") String search) {
//        return new ResponseEntity<>(userService.getUser(search), HttpStatus.OK);
//    }
    @PutMapping("{id}")
    public ResponseEntity<?> updateUser(@RequestBody UserSaveRequest request, @PathVariable Long id){
        userService.update(request,id);
        return ResponseEntity.ok().build();
    }
}
