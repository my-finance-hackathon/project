package my.finance.hackathon.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;
    private Double balance;
    private Double credit;
    private Boolean overdraft;
    private LocalDateTime timeStamp;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;



}
