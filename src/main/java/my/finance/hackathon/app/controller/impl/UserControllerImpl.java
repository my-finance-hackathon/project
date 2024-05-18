package my.finance.hackathon.app.controller.impl;

import lombok.RequiredArgsConstructor;
import my.finance.hackathon.app.controller.UserController;
import my.finance.hackathon.app.service.UserService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class UserControllerImpl implements UserController {

    public final UserService userService;

    @Secured("ROLE_ADMIN")
    @PostMapping("/{sid}/enable")
    public String enableUserHandler(@PathVariable String sid) {

        return "ok";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/{sid}/disable")
    public String disableUserHandler(@PathVariable String sid) {
        return "ok";
    }
}
