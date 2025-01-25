package net.mediger.api.member.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import net.mediger.api.member.api.dto.RequestBusinessDetails;
import net.mediger.api.member.api.dto.RequestJoin;
import net.mediger.api.member.api.dto.RequestMemberDetails;
import net.mediger.api.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Member", description = "멤버 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("check/{account}")
    public ResponseEntity<Boolean> checkAccount(@PathVariable String account) {
        return ResponseEntity.ok(memberService.checkedAccount(account));
    }

    @PostMapping("join")
    public ResponseEntity<Void> join(@RequestBody RequestJoin requestJoin) {
        memberService.join(requestJoin);
        return ResponseEntity.ok().build();
    }

    @PutMapping("join/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody RequestMemberDetails requestDetails) {
        memberService.updateDetails(id, requestDetails);
        return ResponseEntity.ok().build();
    }

    @PostMapping("join/business")
    public ResponseEntity<Void> joinBusiness(@RequestBody RequestJoin requestJoin) {
        memberService.joinBusiness(requestJoin);
        return ResponseEntity.ok().build();
    }

    @PutMapping("join/business/{id}")
    public ResponseEntity<Void> updateBusiness(@PathVariable Long id, @RequestBody RequestBusinessDetails requestDetails) {
        memberService.updateBusinessDetails(id, requestDetails);
        return ResponseEntity.ok().build();
    }
}
