package ro.siit.SpringBootCAE.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ro.siit.SpringBootCAE.models.CustomUserDetails;
import ro.siit.SpringBootCAE.models.Team;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws  IOException {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        String redirectURL = request.getContextPath();

        if (userDetails.getUseRole().equals(Team.CAELEAD)){
            redirectURL = "/caeLead/";
        } else {
            redirectURL = "/user/";

        }
        response.sendRedirect(redirectURL);
    }
}
