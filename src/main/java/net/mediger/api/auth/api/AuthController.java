package net.mediger.api.auth.api;

import lombok.RequiredArgsConstructor;
import net.mediger.api.auth.api.dto.RequestBusinessDetails;
import net.mediger.api.auth.api.dto.RequestBusinessJoin;
import net.mediger.api.auth.api.dto.RequestDetails;
import net.mediger.api.auth.api.dto.RequestJoin;
import net.mediger.api.auth.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("check/{account}")
    public ResponseEntity<Boolean> checkAccount(@PathVariable String account) {
        return ResponseEntity.ok(authService.checkedAccount(account));
    }

    @PostMapping("join")
    public ResponseEntity<Void> join(@RequestBody RequestJoin requestJoin) {
        authService.join(requestJoin);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("join")
    public ResponseEntity<Void> update(@AuthenticationPrincipal Long id,
                                       @RequestBody RequestDetails requestDetails) {
        authService.updateDetails(id, requestDetails);

        return ResponseEntity.ok().build();
    }

    @PostMapping("join/business")
    public ResponseEntity<Void> joinBusiness(@RequestBody RequestBusinessJoin requestBusinessJoin) {
        authService.joinBusiness(requestBusinessJoin);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("join/business")
    public ResponseEntity<Void> updateBusiness(@AuthenticationPrincipal Long id,
                                               @RequestBody RequestBusinessDetails requestDetails) {
        authService.updateBusinessDetails(id, requestDetails);

        return ResponseEntity.ok().build();
    }
}
