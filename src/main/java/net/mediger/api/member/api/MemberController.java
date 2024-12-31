package net.mediger.api.member.api;

import lombok.RequiredArgsConstructor;
import net.mediger.api.member.api.dto.RequestJoin;
import net.mediger.api.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("join")
    public ResponseEntity<Void> join(@RequestBody RequestJoin requestJoin) {
        memberService.join(requestJoin);
        return ResponseEntity.ok().build();
    }

    // 회원가입할 때 필요한 기능 -> 아이디 중복체크
}
