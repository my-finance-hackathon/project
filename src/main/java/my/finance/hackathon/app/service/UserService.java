package my.finance.hackathon.app.service;

import my.finance.hackathon.app.dto.SimpleUserDto;
import my.finance.hackathon.app.model.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public interface UserService {

    void save(User user);

    User getUserByUserOrCreate(SimpleUserDto simpleUserDto);

    User getUserById(Long id) throws  UsernameNotFoundException;

    User createUser(SimpleUserDto simpleUserDto);

    void enableUser(String sid);

    void disableUser(String sid);

    User getUserBySid(String sid);


}
