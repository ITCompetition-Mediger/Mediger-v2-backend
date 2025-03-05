package net.mediger.user.api;

import java.security.Principal;
import lombok.RequiredArgsConstructor;
import net.mediger.global.exception.response.ApiResponse;
import net.mediger.user.api.docs.UserApiDocs;
import net.mediger.user.api.dto.RequestBusinessDetails;
import net.mediger.user.api.dto.RequestDetails;
import net.mediger.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController implements UserApiDocs {

    private final UserService userService;

    @Override
    @PatchMapping
    @PreAuthorize("hasRole('MEMBER')")
    public ApiResponse<Void> update(Principal principal, @RequestBody RequestDetails requestDetails) {
        Long id = Long.parseLong(principal.getName());
        userService.updateDetails(id, requestDetails);

        return ApiResponse.success(HttpStatus.NO_CONTENT);
    }

    @Override
    @PatchMapping("business")
    @PreAuthorize("hasRole('BUSINESS')")
    public ApiResponse<Void> updateBusiness(Principal principal, @RequestBody RequestBusinessDetails requestDetails) {
        Long id = Long.parseLong(principal.getName());
        userService.updateBusinessDetails(id, requestDetails);

        return ApiResponse.success(HttpStatus.NO_CONTENT);
    }
}
