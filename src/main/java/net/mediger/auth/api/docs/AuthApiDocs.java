package net.mediger.auth.api.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.mediger.auth.api.dto.RequestBusinessJoin;
import net.mediger.auth.api.dto.RequestJoin;
import net.mediger.auth.api.dto.RequestLogin;
import net.mediger.auth.api.dto.RequestVerify;
import net.mediger.auth.jwt.ResponseToken;
import net.mediger.global.exception.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Auth Controller", description = "회원가입/로그인 API")
public interface AuthApiDocs {

    @Operation(summary = "아이디 중복 확인")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "아이디 중복 X"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "아이디 중복 O")}
    )
    ApiResponse<Boolean> checkAccount(String account);


    @Operation(summary = "휴대폰 인증")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "인증번호 전송 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "인증번호 전송 실패")}
    )
    ApiResponse<Void> certification(@RequestParam String phone);

    @Operation(summary = "휴대폰 인증 확인")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "인증 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "인증 실패")}
    )
    ApiResponse<Boolean> verify(@RequestBody RequestVerify requestVerify);

    @Operation(summary = "일반 회원 가입")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "회원가입 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "입력값 오류")
    })
    ResponseEntity<ApiResponse<Void>> join(RequestJoin requestJoin);

    @Operation(summary = "사업자 회원 가입")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "회원가입 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "입력값 오류")
    })
    ResponseEntity<ApiResponse<Void>> joinBusiness(RequestBusinessJoin requestBusinessJoin);

    @Operation(summary = "일반 회원 로그인")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "로그인 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "로그인 실패")}
    )
    ApiResponse<ResponseToken> login(RequestLogin requestLogin);

    @Operation(summary = "사업자 회원 로그인")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "로그인 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "로그인 실패")}
    )
    ApiResponse<ResponseToken> loginBusiness(RequestLogin requestLogin);
}
