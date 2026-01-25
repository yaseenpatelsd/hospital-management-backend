package hospital_management_sytem.Jwt;

import hospital_management_sytem.Entity.UserEntity;
import hospital_management_sytem.Exeption.JwtExceptions;
import hospital_management_sytem.Service.UserService.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserService userService;

    public JwtFilter(JwtUtil jwtUtil, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    public final List<String> FREE_API=List.of(

            //Patiemt register flow
            "/patient/register",
            "/patient/account/verification",
            "/patient/otp/request/for/account/verification",
            "/patient/login",
            "/patient/request/otp",
            "/patient/password/reset",


            "/doctor/register",
            "/doctor/account/verification",
            "/doctor/login",
            "/doctor/request/otp",
            "/doctor/password/reset",
            "/doctor/findAll",

            "/admin/register",
            "/admin/login",

            "/staff/register",
            "/staff/account/verification",
            "/staff/login",
            "/staff/request/otp",
            "/staff/password/reset",


            "/swagger-ui/**",
            "/swagger-ui.html",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/webjars/**"

    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();

        for (String prefix :FREE_API) {
            if (path.startsWith(prefix)) {
                filterChain.doFilter(request, response);
                return;
            }
        }


        String authHead=request.getHeader("Authorization");
        String username=null;
        String token=null;


        if (authHead !=null && authHead.startsWith("Bearer ")){
          token=authHead.substring(7).trim();

          try {
              username=jwtUtil.extractUsernameFromToken(token);
              System.out.println("BILL API HIT");
          }catch (JwtExceptions e){
              response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
              response.getWriter().write(e.getMessage());
              return;
          }
        }

        if (username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails user=userService.loadUserByUsername(username);

            if (jwtUtil.isTokenvalid(token)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}
