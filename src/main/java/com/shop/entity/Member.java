package com.shop.entity;

import com.shop.constant.Role;
import com.shop.dto.MemberFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Table(name="member")
@Getter @Setter
@ToString
public class Member {
    //회원 정보를 저장하는 Member 엔티티 생성

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Column(unique = true)
    //이메일은 유일해야 하기 때문에 unique 옵션을 true로 설정해준다.
    private String email;

    private String password;

    private String address;

    @Enumerated(EnumType.STRING)
    //Enum의 경우 순서로 저장하게 되면 Enum에 속한 데이터가 변경되면 순서가 바뀔 위험이 있기 때문에 String으로 처리한다.
    private Role role;

    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
        //Member 엔티티를 생성하는 메소드이다. Member 엔티티에 회원을 생성하는 메소드를 만들어서 관리하면 코드 변경시 한 곳만 수정하면 되는 이점이 있다.
        Member member = new Member();
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setAddress(memberFormDto.getAddress());
        String password = passwordEncoder.encode(memberFormDto.getPassword());
        //스프링 시큐리티 설정 클래스에 등록된 BCryptPasswordEncoder Bean을 파라미터로 넘겨서 비밀번호를 암호화 한다.
        member.setPassword(password);
        member.setRole(Role.ADMIN);
        return member;
    }
}
