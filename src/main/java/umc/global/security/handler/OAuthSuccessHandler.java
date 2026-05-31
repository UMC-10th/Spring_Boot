package umc.global.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import umc.global.apiPayload.ApiResponse;
import umc.global.apiPayload.code.status.GeneralSuccessCode;
import umc.global.security.entity.AuthUser;
import umc.global.security.entity.OAuthUser;
import umc.global.security.util.JwtUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuthSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        ObjectMapper objectMapper = new ObjectMapper();

        response.setContentType("application/json; charset=UTF-8");
        response.setStatus(GeneralSuccessCode.OK.getStatus().value());

        OAuthUser oAuthUser = (OAuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String accessToken = jwtUtil.createAccessToken(new AuthUser(oAuthUser.getUser()));

        Map<String, String> result = new HashMap<>();
        result.put("accessToken", accessToken);

        ApiResponse<Map<String, String>> responseBody = ApiResponse.onSuccess(GeneralSuccessCode.OK, result);        objectMapper.writeValue(response.getOutputStream(), responseBody);
    }
}