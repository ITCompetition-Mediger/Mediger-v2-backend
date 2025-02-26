package net.mediger.user.api;

import java.security.Principal;
import lombok.RequiredArgsConstructor;
import net.mediger.global.exception.CustomException;
import net.mediger.global.exception.ErrorCode;
import net.mediger.global.exception.response.ApiResponse;
import net.mediger.user.api.docs.MemberApiDocs;
import net.mediger.user.api.dto.RequestBusinessDetails;
import net.mediger.user.api.dto.RequestDetails;
import net.mediger.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/member")
@RequiredArgsConstructor
public class MemberController implements MemberApiDocs {

    private final UserService userService;

    @Override
    @PatchMapping
    public ApiResponse<Void> update(Principal principal, Long id,
                                    @RequestBody RequestDetails requestDetails) {
        checkOwner(principal, id);
        userService.updateDetails(id, requestDetails);

        return ApiResponse.success(HttpStatus.NO_CONTENT);
    }

    @Override
    @PatchMapping("business")
    public ApiResponse<Void> updateBusiness(Principal principal, Long id,
                                            @RequestBody RequestBusinessDetails requestDetails) {
        checkOwner(principal, id);
        userService.updateBusinessDetails(id, requestDetails);

        return ApiResponse.success(HttpStatus.NO_CONTENT);
    }

    private void checkOwner(Principal principal, Long id) {
        if (!principal.getName().equals(String.valueOf(id))) {
            throw new CustomException(ErrorCode.NOT_MATCH_USER);
        }
    }
}
