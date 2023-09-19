package com.example.spa_case.service.userService;

import com.example.spa_case.exception.ResourceNotFoundException;
import com.example.spa_case.model.User;
import com.example.spa_case.model.enums.ELock;
import com.example.spa_case.model.enums.ERole;
import com.example.spa_case.model.enums.EStatusCustomer;
import com.example.spa_case.repository.FileRepository;
import com.example.spa_case.repository.UserRepository;
import com.example.spa_case.service.userService.request.UserEditRequest;
import com.example.spa_case.service.userService.request.UserSaveRequest;
import com.example.spa_case.service.userService.response.UserEditResponse;
import com.example.spa_case.service.userService.response.UserListResponse;
import com.example.spa_case.util.AppMessage;
import com.example.spa_case.util.AppUtil;
import com.example.spa_case.service.dto.request.SelectOptionRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final FileRepository fileRepository;

    public Page<UserListResponse> getAll(String search , Pageable pageable) {
        search = "%"+search+"%";
        return userRepository.searchAllUser(search, pageable)
                .map(user -> UserListResponse.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .email(user.getEmail())
                        .phone(user.getPhone())
                        .dob(user.getDob())
                        .statusCustomer(String.valueOf(user.getStatusCustomer()))
                        .eLock(String.valueOf(user.getELock()))
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
    public void create(UserSaveRequest request){
        var user = AppUtil.mapper.map(request, User.class);
        user.setRole(ERole.ROLE_USER);
//        user.setStatusCustomer(EStatusCustomer.SILVER);
        user.setELock(ELock.UNLOCK);
//        user.setAvatar(file);
        userRepository.save(user);
    }
    public UserEditResponse findByIdUser(Long id){
        var user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException
                        (String.format(AppMessage.ID_NOT_FOUND, "User", id)));
        var result = AppUtil.mapper.map(user, UserEditResponse.class);
        result.setStatusCustomer(user.getStatusCustomer());
        result.setERole(user.getRole());
        result.setELock(user.getELock());
        return result;
    }
    public void changeLockStatus(Long id, ELock eLock){
        var user = findById(id);
        user.setELock(eLock);
        userRepository.save(user);
    }
    @Transactional
    public void update(UserEditRequest request, Long id) throws Exception {
        var userInDb = findById(id);
        if(!Objects.equals(userInDb.getPassWord(), request.getOldPassword())){
            throw new Exception("Incorrect Password");
        }//        user.setELock(ELock.valueOf(request.getELock()));
        userInDb.setStatusCustomer(EStatusCustomer.valueOf(request.getStatusCustomer()));
        AppUtil.mapper.map(request, userInDb);
        request.setId(id.toString());
        userRepository.save(userInDb);

//        saveCategoryAndActor(request, user);

    }
//    public void saveCategoryAndActor(BookSaveRequest request, Book book){
//        var bookAuthors = new ArrayList<BookAuthor>();
//        for (String idAuthor : request.getIdAuthors()) {
//            Author author = new Author(Long.valueOf(idAuthor));
//            bookAuthors.add(new BookAuthor(book, author));
//        }
//        bookAuthorRepository.saveAll(bookAuthors);
//    }
}

