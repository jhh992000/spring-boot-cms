package me.hhjeong.springbootcms.account.ui;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    public static final String NO_AUTHENTICATION_IN_SECURITY_CONTEXT_FOUND = "no authentication in security context found";

    @GetMapping("/myinfo")
    public String myinfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            return NO_AUTHENTICATION_IN_SECURITY_CONTEXT_FOUND;
        }

        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
            return springSecurityUser.getUsername();
        }

        if (authentication.getPrincipal() instanceof String) {
            return (String) authentication.getPrincipal();
        }
        return NO_AUTHENTICATION_IN_SECURITY_CONTEXT_FOUND;
    }

    @GetMapping("/user-feature")
    public String user_feature() {
        return "this is user feature.";
    }

    @GetMapping("/admin-feature")
    public String admin_feature() {
        return "this is admin feature.";
    }

}
