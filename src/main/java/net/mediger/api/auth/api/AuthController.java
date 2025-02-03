package net.mediger.api.auth.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import net.mediger.api.auth.api.dto.RequestBusinessDetails;
import net.mediger.api.auth.api.dto.RequestBusinessJoin;
import net.mediger.api.auth.api.dto.RequestDetails;
import net.mediger.api.auth.api.dto.RequestJoin;
import net.mediger.api.auth.api.dto.RequestLogin;
import net.mediger.api.auth.jwt.ResponseToken;
import net.mediger.api.auth.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth Controller", description = "회원가입/로그인 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("check/{account}")
    public ResponseEntity<Boolean> checkAccount(@PathVariable String account) {
        return ResponseEntity.ok(authService.isCheckedAccount(account));
    }

    @PostMapping("join")
    public ResponseEntity<Void> join(@RequestBody RequestJoin requestJoin) {
        authService.join(requestJoin);

        return ResponseEntity.ok().build();
    }

    @PostMapping("join/business")
    public ResponseEntity<Void> joinBusiness(@RequestBody RequestBusinessJoin requestBusinessJoin) {
        authService.joinBusiness(requestBusinessJoin);

        return ResponseEntity.ok().build();
    }

    @PostMapping("login")
    public ResponseEntity<ResponseToken> login(@RequestBody RequestLogin requestLogin) {
        return ResponseEntity.ok(authService.login(requestLogin));
    }

    @PatchMapping("join/detail")
    public ResponseEntity<Void> update(@RequestBody RequestDetails requestDetails) {
        authService.updateDetails(requestDetails);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("join/business/detail")
    public ResponseEntity<Void> updateBusiness(@RequestBody RequestBusinessDetails requestDetails) {
        authService.updateBusinessDetails(requestDetails);

        return ResponseEntity.ok().build();
    }
}
