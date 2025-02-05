package net.mediger.member.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import net.mediger.member.api.dto.RequestBusinessDetails;
import net.mediger.member.api.dto.RequestDetails;
import net.mediger.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Member Controller", description = "회원 관련 API")
@RestController
@RequestMapping("api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(Principal principal, @PathVariable Long id,
                                       @RequestBody RequestDetails requestDetails) {
        checkOwner(principal, id);
        memberService.updateDetails(id, requestDetails);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("business/{id}")
    public ResponseEntity<Void> updateBusiness(Principal principal, @PathVariable Long id,
                                               @RequestBody RequestBusinessDetails requestDetails) {
        checkOwner(principal, id);
        memberService.updateBusinessDetails(id, requestDetails);

        return ResponseEntity.ok().build();
    }

    private void checkOwner(Principal principal, Long id) {
        if (!principal.getName().equals(String.valueOf(id))) {
            throw new IllegalArgumentException("본인의 정보만 수정할 수 있습니다.");
        }
    }
}
