// Bearer이면 추출
token = token.replace("Bearer ", "");

// AccessToken 검증하기: 올바른 토큰이면
if (jwtUtil.isValid(token)) {

    // JWT 토큰에서 유저 정보 조회: UID와 소셜 로그인 타입 가져오기
    String uid = jwtUtil.getUid(token);
    SocialType socialType = jwtUtil.getSocialType(token);

    // 인증 객체 생성: 로그인 타입과 UID로 찾아온 뒤, 인증 객체 생성
    UserDetails member =
            customUserDetailsService.loadUserByUidAndSocialType(socialType, uid);

    Authentication auth = new UsernamePasswordAuthenticationToken(
            member,
            null,
            member.getAuthorities()
    );

    // 인증 완료 후 SecurityContextHolder에 넣기
    SecurityContextHolder.getContext().setAuthentication(auth);
}

filterChain.doFilter(request, response);