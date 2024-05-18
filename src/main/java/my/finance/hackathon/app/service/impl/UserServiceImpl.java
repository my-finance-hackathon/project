package my.finance.hackathon.app.service.impl;

import lombok.RequiredArgsConstructor;
import my.finance.hackathon.app.dto.SimpleUserDto;
import my.finance.hackathon.app.model.User;
import my.finance.hackathon.app.repository.UserRepository;
import my.finance.hackathon.app.service.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void save(User user) {
        user.setTimeStamp(LocalDateTime.now());
        userRepository.save(user);
    }

    @Override
    public User getUserByUserOrCreate(SimpleUserDto simpleUserDto) {
        var userOpt = userRepository.findBySid(simpleUserDto.getSid());
        return userOpt.orElseGet(() -> createUser(simpleUserDto));
    }

    @Override
    public User getUserById(Long id) throws UsernameNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User id not found"));
    }

    @Override
    public User createUser(SimpleUserDto userDto) {
        var user = new User(userDto);
        userRepository.save(user);
        return user;
    }

    @Override
    public void enableUser(String sid) {
        var user = getUserBySid(sid);
        user.enable();
        save(user);
    }

    @Override
    public void disableUser(String sid) {
        var user = getUserBySid(sid);
        user.disable();
        save(user);
    }

    @Override
    public User getUserBySid(String sid) {
        return userRepository.findBySid(sid).orElseThrow(() -> new UsernameNotFoundException("User sid not found"));
    }
}
