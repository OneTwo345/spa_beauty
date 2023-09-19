package com.example.spa_case.service.UserService;


import com.example.spa_case.model.Customer;
import com.example.spa_case.model.User;
import com.example.spa_case.repository.UserRepository;
import com.example.spa_case.service.UserService.request.UserSaveRequest;
import com.example.spa_case.service.UserService.response.UserDetailResponse;
import com.example.spa_case.service.UserService.response.UserListResponse;
import com.example.spa_case.util.AppUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public void create(UserSaveRequest request) {
        var user = AppUtil.mapper.map(request, User.class);
        user = userRepository.save(user);
        User finalUser = user;
    }
    public List<User> getUserList() {
        return userRepository.findAll();
    }

//    public UserDetailResponse findById(Long id){
//        var user = userRepository.findById(id).orElse(new User());
//
//        var result = AppUtil.mapper.map(user, UserDetailResponse.class);
////        result.setTypeId(room.getType().getId());
////        result.setCategoryIds(room
////                .getRoomCategories()
////                .stream().map(roomCategory -> roomCategory.getCategory().getId())
////                .collect(Collectors.toList()));
//        return result;
//    }
//
//    public Page<UserListResponse> getUser(Pageable pageable, String search){
//        search = "%" + search + "%";
//        return userRepository.findUsersBySearch(search ,pageable).map(e -> {
//            var result = AppUtil.mapper.map(e, UserListResponse.class);
////            result.setType(e.getType().getName());
////            result.setCategories(e.getRoomCategories()
////                    .stream().map(c -> c.getCategory().getName())
////                    .collect(Collectors.joining(", ")));
//            return result;
//        });
//    }


    public void update(UserSaveRequest request, Long id) {
        var userDb = userRepository.findById(id).orElse(new User());
        AppUtil.mapper.map(request, userDb);
        userRepository.save(userDb);
    }
//
//    public Boolean delete(Long id) {
//        userRepository.deleteById(id);
//        return true;
//    }
}
