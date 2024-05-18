package my.finance.hackathon.app.controller;

import org.springframework.web.bind.annotation.PathVariable;

public interface UserController {
    String enableUserHandler(String sid);
    String disableUserHandler(String sid);

}
