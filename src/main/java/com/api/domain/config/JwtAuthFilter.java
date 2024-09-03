package com.api.domain.config;

import com.api.exceptions.InvalidJwtTokenException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 특정 경로는 필터를 거치지 않도록 설정
        String requestPath = request.getRequestURI();
        if (requestPath.startsWith("/api/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        //헤더에서 JWT 토큰을 추출
        String token = jwtUtil.getTokenFromRequest(request);

        // 토큰이 유효하면 SecurityContext에 인증 정보를 저장
        try {
            if (token != null && jwtUtil.validateToken(token)) {
                SecurityContextHolder.getContext().setAuthentication(jwtUtil.getAuthentication(token));
            }
            filterChain.doFilter(request, response);
        }
        catch (InvalidJwtTokenException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, String.valueOf(e));
        }
    }


}
