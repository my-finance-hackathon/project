package my.finance.hackathon.app.controller.impl;

import lombok.RequiredArgsConstructor;
import my.finance.hackathon.app.dto.SimpleUserDto;
import my.finance.hackathon.app.model.Account;
import my.finance.hackathon.app.model.User;
import my.finance.hackathon.app.service.AccountService;
import my.finance.hackathon.app.service.UserService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/account")
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class AccountController {

    private final UserService userService;

    private final AccountService accountService;

    @GetMapping("/")
    @Secured("ROLE_USER")
    public SimpleUserDto requestTest(JwtAuthenticationToken principal) {
        var jwtUser = new SimpleUserDto(principal);
        User currentUser = userService.getUserByUserOrCreate(jwtUser);
        List<Account> userAccounts = accountService.getByUserId(currentUser.getId());

        jwtUser.setAccounts(userAccounts);
        return jwtUser;
    }


    @GetMapping("/unsafetest")
    public String requestUnsafeTest() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        System.out.println(securityContext.getAuthentication().getName());
        System.out.println(securityContext.getAuthentication().getPrincipal());

        return "ok";
    }


}
