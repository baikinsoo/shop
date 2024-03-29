package com.shop.service;

import com.shop.entity.Member;
import com.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
//하나의 트랜잭션 안에서 동작하게되면 에러 발생 시 롤백이 가능해진다. 또한 데이터 일관성을 유지할 수 있다.
@RequiredArgsConstructor
//final이나 @NonNull이 붙은 필드에 생성자를 생성해준다.
public class MemberService implements UserDetailsService {
    //MemberService가 UserDetailsService를 구현한다.

    private final MemberRepository memberRepository;
    //@RequiredArgsConstructor 빈을 주입하는 방식, 빈에 생성자가 1개이고 생성자의 파라미터 타입이 빈으로
    //등록이 가능하다면 @Autowired 없이 의존성 주입이 가능하다.

    public Member saveMember(Member member) {
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member) {
        //이미 가입된 회원의 경우 IllegalStateException 예외를 발생시킨다.
        Member findMember = memberRepository.findByEmail(member.getEmail());
        if (findMember != null) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //UserDetailsService 인터페이스의 loadUserByUsername() 메소드를 오버라이딩한다.
        Member member = memberRepository.findByEmail(email);
        //로그인할 유저의 email을 파라미터로 전달받는다.

        if (member == null) {
            throw new UsernameNotFoundException(email);
        }

        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
        //builder 패턴을 이용해 User 객체에 회원의 이메일, 패스워드, 비밀번호, role을 파라미터로 넘긴다.
    }
}
