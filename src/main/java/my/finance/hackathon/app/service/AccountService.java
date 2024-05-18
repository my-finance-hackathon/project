package my.finance.hackathon.app.service;

import my.finance.hackathon.app.model.Account;
import my.finance.hackathon.app.model.User;

import java.util.List;

public interface AccountService {
    void save(Account account);
    List<Account> getByUserId(Long userId);

    Account createAccountForUser(User user);

    Account getById(Long id);

    void plus(Long accountId, Double sum);

    void minus(Long accountId, Double sum);

}
