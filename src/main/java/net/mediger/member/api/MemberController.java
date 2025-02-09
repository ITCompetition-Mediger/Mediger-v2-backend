package net.mediger.member.api;

import java.security.Principal;
import lombok.RequiredArgsConstructor;
import net.mediger.global.exception.CustomException;
import net.mediger.global.exception.ErrorCode;
import net.mediger.global.exception.response.ApiResponse;
import net.mediger.member.api.docs.MemberApiDocs;
import net.mediger.member.api.dto.RequestBusinessDetails;
import net.mediger.member.api.dto.RequestDetails;
import net.mediger.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/member")
@RequiredArgsConstructor
public class MemberController implements MemberApiDocs {

    private final MemberService memberService;

    @Override
    @PatchMapping("/{id}")
    public ApiResponse<Void> update(Principal principal, @PathVariable Long id,
                                    @RequestBody RequestDetails requestDetails) {
        checkOwner(principal, id);
        memberService.updateDetails(id, requestDetails);

        return ApiResponse.success(HttpStatus.NO_CONTENT);
    }

    @Override
    @PatchMapping("business/{id}")
    public ApiResponse<Void> updateBusiness(Principal principal, @PathVariable Long id,
                                            @RequestBody RequestBusinessDetails requestDetails) {
        checkOwner(principal, id);
        memberService.updateBusinessDetails(id, requestDetails);

        return ApiResponse.success(HttpStatus.NO_CONTENT);
    }

    private void checkOwner(Principal principal, Long id) {
        if (!principal.getName().equals(String.valueOf(id))) {
            throw new CustomException(ErrorCode.NOT_MATCH_USER);
        }
    }
}
