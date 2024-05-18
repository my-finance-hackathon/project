package my.finance.hackathon.app.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import my.finance.hackathon.app.dto.SimpleUserDto;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "user_entity")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String sid;
    @OneToMany(mappedBy = "user")
    private List<Account> account;

    private LocalDateTime timeStamp;

    public User(SimpleUserDto simpleUserDto) {
        username = simpleUserDto.getUserName();
        email = simpleUserDto.getEmail();
        sid = simpleUserDto.getSid();
    }

}
