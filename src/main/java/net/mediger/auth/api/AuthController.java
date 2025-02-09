package net.mediger.auth.api;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import net.mediger.auth.api.docs.AuthApiDocs;
import net.mediger.auth.api.dto.RequestBusinessJoin;
import net.mediger.auth.api.dto.RequestJoin;
import net.mediger.auth.api.dto.RequestLogin;
import net.mediger.auth.api.dto.RequestVerify;
import net.mediger.auth.jwt.ResponseToken;
import net.mediger.auth.service.AuthService;
import net.mediger.global.exception.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
public class AuthController implements AuthApiDocs {

    private final AuthService authService;

    @Override
    @PostMapping("check")
    public ApiResponse<Boolean> checkAccount(@RequestParam String account) {
        return ApiResponse.success(authService.isCheckedAccount(account));
    }

    @Override
    @PostMapping("certification")
    public ApiResponse<Void> certification(@RequestParam String phone) {
        authService.certification(phone);

        return ApiResponse.success(HttpStatus.NO_CONTENT);
    }

    @Override
    @PostMapping("certification/business")
    public ApiResponse<Void> certificationBusiness(@RequestParam String email) {
        authService.certificationBusiness(email);

        return ApiResponse.success(HttpStatus.NO_CONTENT);
    }

    @Override
    @PostMapping("verify")
    public ApiResponse<Boolean> verify(@RequestBody RequestVerify requestVerify) {
        return ApiResponse.success(authService.verify(requestVerify.identifier(), requestVerify.code()));
    }

    @Override
    @PostMapping("join")
    public ResponseEntity<ApiResponse<Void>> join(@RequestBody RequestJoin requestJoin) {
        Long memberId = authService.join(requestJoin);

        URI location = URI.create("/api/member/" + memberId);

        return ResponseEntity.created(location)
                .body(ApiResponse.success(HttpStatus.CREATED));
    }

    @Override
    @PostMapping("join/business")
    public ResponseEntity<ApiResponse<Void>> joinBusiness(@RequestBody RequestBusinessJoin requestBusinessJoin) {
        Long businessId = authService.joinBusiness(requestBusinessJoin);

        URI location = URI.create("/api/member/business/" + businessId);

        return ResponseEntity.created(location)
                .body(ApiResponse.success(HttpStatus.CREATED));
    }

    @Override
    @PostMapping("login")
    public ApiResponse<ResponseToken> login(@RequestBody RequestLogin requestLogin) {
        return ApiResponse.success(authService.login(requestLogin));
    }

    @Override
    @PostMapping("login/business")
    public ApiResponse<ResponseToken> loginBusiness(@RequestBody RequestLogin requestLogin) {
        return ApiResponse.success(authService.loginBusiness(requestLogin));
    }
}
