package net.ion.sst.api_sample.controller;

import net.ion.sst.api_sample.config.JwtTokenUtil;
import net.ion.sst.api_sample.controller.api.ApiResult;
import net.ion.sst.api_sample.security.UserDetailServiceImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class DefaultController
{
    UserDetailServiceImpl userDetailsService;
    AuthenticationManager authenticationManager;
    JwtTokenUtil jwtTokenUtil;

    public DefaultController(UserDetailServiceImpl userDetailService,
                             AuthenticationManager authenticationManager,
                             JwtTokenUtil jwtTokenUtil)
    {
        this.userDetailsService = userDetailService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @GetMapping("/")
    public String helloWorld()
    {
        return "Hello World";
    }

    @PostMapping("/signIn")
    public ApiResult signIn(HttpServletRequest request,
                            HttpServletResponse response,
                            @RequestParam("username") String username,
                            @RequestParam("password") String password)
    {
        UserDetails user = userDetailsService.loadUserByUsername(username);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                user, password, user.getAuthorities());

        authenticationManager.authenticate(token);
        token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(token);

        final String jwtToken = jwtTokenUtil.generateToken(user);

        Cookie cookie = new Cookie("token", jwtToken);
        cookie.setMaxAge(30 * 24 * 60 * 60); // 초단위(30일)
        cookie.setPath("/");
        response.addCookie(cookie);

        return new ApiResult(0, "success", jwtToken);
    }

    @GetMapping("/signOut")
    public ApiResult signOut(HttpServletRequest request,
                             HttpServletResponse response) throws Exception
    {
        request.logout();
        SecurityContextHolder.clearContext();

        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        return new ApiResult(0, "Success");
    }
}
