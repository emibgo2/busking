package com.example.demo.config.auth;


import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PrincipalDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // 스프링이 로그인 요청을 가로챌 때, loginID, password 변수 2개를 가로채는데
    // password 부분 처리는 알아서 함.
    // username이 DB에 잇는지만 확인해서 return
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User principal = userRepository.findByLoginEmail(username)
                .orElseThrow(()->{
                    return new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다.: " + username);});
        System.out.println("principal: "+principal);
        return new PrincipalDetail(principal); // 시큐리티의 세션에 유저 정보가 저장이됨.
    }
}
