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
        var userOpt =  userRepository.findBySid(simpleUserDto.getSid());
        if(userOpt.isPresent()) {
            return userOpt.get();
        }
        var user = new User(simpleUserDto);
        userRepository.save(user);
        return user;
    }

    @Override
    public User getUserById(Long id) throws UsernameNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User id not found"));
    }

    @Override
    public User createUser() {
        return null;
    }
}
