package net.ion.sst.api_sample.security;

import io.jsonwebtoken.ExpiredJwtException;
import net.ion.sst.api_sample.config.JwtTokenUtil;
import net.ion.sst.api_sample.controller.api.ApiResult;
import net.ion.sst.api_sample.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter
{
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    UserDetailServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null)
        {
            String tokenHeader = httpServletRequest.getHeader("Authorization");

            if (tokenHeader != null && tokenHeader.startsWith("Bearer "))
            {
                String username = null;
                String jwtToken = tokenHeader.substring(7);
                try
                {
                    username = jwtTokenUtil.getUsernameFromToken(jwtToken);
                }
                catch (IllegalArgumentException e)
                {
                    logger.error("Unable to get JWT Token");
                    sendResponse(httpServletResponse, HttpStatus.BAD_REQUEST, new ApiResult(997, "형식을 알 수 없는 토큰입니다."));
                    return;
                }
                catch (ExpiredJwtException e)
                {
                    logger.error("JWT Token has expired");
                    sendResponse(httpServletResponse, HttpStatus.BAD_REQUEST, new ApiResult(998, "토큰이 만료되었습니다."));
                    return;
                }
                catch (Exception e)
                {
                    logger.error("Unable to get JWT Token " + e.getLocalizedMessage());
                    sendResponse(httpServletResponse, HttpStatus.BAD_REQUEST, new ApiResult(997, "형식을 알 수 없는 토큰입니다."));
                    return;
                }

                // 토근으로부터 얻은 username 을 이용해서 인증서를 만들어 보안 컨텍스트에 넣는다.
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if (jwtTokenUtil.validateToken(jwtToken, userDetails))
                {
                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    token.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                    SecurityContextHolder.getContext().setAuthentication(token);
                }
                else
                {
                    logger.error("유효하지 않은 토큰입니다.");
                    sendResponse(httpServletResponse, HttpStatus.BAD_REQUEST, new ApiResult(996, "유효하지 않은 토큰입니다."));
                    return;
                }
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }


    private void sendResponse(HttpServletResponse response, HttpStatus status, ApiResult apiResult)
    {
        try
        {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.setStatus(status.value());
            response.getWriter().write(StringUtil.convertObjectToJson(apiResult));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
