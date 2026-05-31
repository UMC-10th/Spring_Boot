package umc.global.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import umc.domain.user.entity.mapping.SocialType;
import umc.global.apiPayload.ApiResponse;
import umc.global.apiPayload.code.status.GeneralErrorCode;
import umc.global.security.service.CustomUserDetailsService;
import umc.global.security.util.JwtUtil;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            String token = request.getHeader("Authorization");

            if (token == null || !token.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            token = token.replace("Bearer ", "");

            if (jwtUtil.isValid(token)) {
                String uid = jwtUtil.getUid(token);
                SocialType socialType = jwtUtil.getSocialType(token);

                UserDetails userDetails = customUserDetailsService.loadUserByUidAndSocialType(socialType, uid);

                Authentication auth = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            ObjectMapper mapper = new ObjectMapper();
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(GeneralErrorCode.UNAUTHORIZED.getStatus().value());

            ApiResponse<Void> errorResponse = ApiResponse.onFailure(GeneralErrorCode.UNAUTHORIZED.getCode(), GeneralErrorCode.UNAUTHORIZED.getMessage(), null);
            mapper.writeValue(response.getOutputStream(), errorResponse);
        }
    }
}