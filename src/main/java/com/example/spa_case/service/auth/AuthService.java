package com.example.spa_case.service.auth;


import com.example.spa_case.model.User;
import com.example.spa_case.model.enums.ERole;
import com.example.spa_case.repository.UserRepository;
import com.example.spa_case.service.auth.request.RegisterRequest;
import com.example.spa_case.util.AppUtil;
import lombok.AllArgsConstructor;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public void register(RegisterRequest request){
        var user = AppUtil.mapper.map(request, User.class);
        user.setRole(ERole.ROLE_USER);
        user.setPassWord(passwordEncoder.encode(user.getPassWord()));
        userRepository.save(user);
    }

    public boolean checkNameOrPhoneOrEmail(RegisterRequest request, BindingResult result){
        boolean check = false;
        if(userRepository.existsByNameIgnoreCase(request.getName())){
            result.reject("username", null,
                    "There is already an account registered with the same username");
            check = true;
        }
        if(userRepository.existsByEmailIgnoreCase(request.getEmail())){
            result.reject("email", null,
                    "There is already an account registered with the same email");
            check = true;
        }
        if(userRepository.existsByPhone(request.getPhone())){
            result.reject("phoneNumber", null,
                    "There is already an account registered with the same phone number");
            check = true;
        }
        return check;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByNameIgnoreCaseOrEmailIgnoreCaseOrPhone(username,username,username)
                .orElseThrow(() -> new UsernameNotFoundException("User not Exist") );
        var role = new ArrayList<SimpleGrantedAuthority>();
        role.add(new SimpleGrantedAuthority(user.getRole().toString()));

        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassWord(), role);
    }
    // để làm 1. kiểm tra xem user có tồn tại trong hệ thông hay không và tìm bằng 3 field Username Email PhoneNumber
    // 2. Nếu có thì sẽ trả về User của .security.core.userdetails.User để nó lưu vô kho spring sercurity context holder
    // 3. nếu không thì trả ra message lỗi User not Exist
}