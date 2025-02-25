package net.mediger.user.api.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.security.Principal;
import net.mediger.global.exception.response.ApiResponse;
import net.mediger.user.api.dto.RequestBusinessDetails;
import net.mediger.user.api.dto.RequestDetails;

@Tag(name = "Member Controller", description = "회원 관련 API")
public interface MemberApiDocs {

    @Operation(summary = "회원 정보 업데이트")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "회원 정보 업데이트 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "회원 정보 업데이트 실패")}
    )
    ApiResponse<Void> update(Principal principal, Long id, RequestDetails requestDetails);

    @Operation(summary = "사업자 정보 업데이트")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "사업자 정보 업데이트 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "사업자 정보 업데이트 실패")}
    )
    ApiResponse<Void> updateBusiness(Principal principal, Long id, RequestBusinessDetails requestBusinessDetails);
}
