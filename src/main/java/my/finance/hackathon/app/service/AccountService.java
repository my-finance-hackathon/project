package my.finance.hackathon.app.service;

import my.finance.hackathon.app.model.Account;

import java.util.List;

public interface AccountService {
    void save(Account account);
    List<Account> getByUserId(Long userId);

}
