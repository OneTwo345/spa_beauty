package com.example.spa_case.controller.RESTController;



import com.example.spa_case.model.enums.ELock;
import com.example.spa_case.service.productService.response.ProductListResponse;
import com.example.spa_case.service.userService.UserService;
import com.example.spa_case.service.userService.request.UserEditRequest;
import com.example.spa_case.service.userService.request.UserRegisterRequest;
import com.example.spa_case.service.userService.request.UserSaveRequest;
import com.example.spa_case.service.userService.response.UserEditResponse;
import com.example.spa_case.service.userService.response.UserListResponse;
import com.example.spa_case.service.userService.response.UserRegisterResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserRestController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<Page<UserListResponse>> list(@PageableDefault(size = 5) Pageable pageable,
                                                       @RequestParam(defaultValue = "") String search
    ) {
        return new ResponseEntity<>(userService.getAll(search, pageable), HttpStatus.OK);
    }

    @DeleteMapping("/{Id}")
    public ResponseEntity<String> deleteRoom(@PathVariable Long Id) {
        userService.deleteById(Id);
        return ResponseEntity.ok("User deleted successfully");
    }

    @PostMapping
    public void create(@RequestBody UserSaveRequest request) {
        userService.create(request);
    }

    @GetMapping("/{id}/{eLock}")
    public ResponseEntity<String> change(@PathVariable Long id, @PathVariable ELock eLock) {
        userService.changeLockStatus(id, eLock); // Gọi phương thức để thay đổi trạng thái
        return ResponseEntity.ok("Status changed successfully");
    }

    //    @PutMapping("{id}")
//    public ResponseEntity<Void> update(@RequestBody @Valid UserSaveRequest request, @PathVariable Long id) {
//        userService.update(request, id);
//        return ResponseEntity.noContent().build();
//    }
    @GetMapping("/{id}")
    public ResponseEntity<UserEditResponse> findById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.findByIdUser(id), HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody @Valid UserEditRequest request, @PathVariable Long id) throws Exception {

        userService.update(request,id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<UserRegisterResponse>> getAllUserRegister() {
        List<UserRegisterResponse> userRegisterResponse = userService.getAllUserRegister();
        return ResponseEntity.ok(userRegisterResponse);
    }

    @PostMapping("/list")
    public void createRegister(@RequestBody UserRegisterRequest request) {
        userService.createRegister(request);
    }
}