package my.finance.hackathon.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import my.finance.hackathon.app.exceptions.OperationException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
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
    private String name;
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    private Currency currency;

    private enum Currency {RUR}

    public Account(User user) {
        setUser(user);
        setBalance(0D);
        setCredit(0D);
        setOverdraft(false);
        setName("account");
        setAccountType(AccountType.ACCOUNT);
        setCurrency(Currency.RUR);
    }

    @Transactional
    public void plus(Double sum) {
        if (Double.compare(sum, 0.0) < 0) return;
        Double oldBalance = getBalance();
        setBalance(oldBalance + sum);
    }

    @Transactional
    public void minus(Double sum) {
        if (Double.compare(sum, 0.0) < 0) throw new OperationException("Balance is too small");
        Double oldBalance = getBalance();
        if (oldBalance - sum > 0) {
            minusBalance(sum);
        } else {
            minusCredit(sum);
        }
    }

    @Transactional
    public void minusBalance(Double sum) {
        Double oldBalance = getBalance();
        setBalance(oldBalance - sum);
    }

    @Transactional
    public void minusCredit(Double sum) {
        if (!getOverdraft() || Double.compare(sum, 0.0) < 0) throw new OperationException("Overdraft disable");
        Double oldCredit = getCredit();
        setCredit(oldCredit - sum);
    }

}
