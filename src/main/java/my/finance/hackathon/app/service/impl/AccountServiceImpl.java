package my.finance.hackathon.app.service.impl;

import lombok.RequiredArgsConstructor;
import my.finance.hackathon.app.exceptions.NotFoundException;
import my.finance.hackathon.app.model.Account;
import my.finance.hackathon.app.model.User;
import my.finance.hackathon.app.repository.AccountRepository;
import my.finance.hackathon.app.service.AccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public void save(Account account) {
        accountRepository.save(account);
    }

    @Override
    public List<Account> getByUserId(Long userId) {
        return accountRepository.findByUserId(userId);

    }

    @Override
    public Account createAccountForUser(User user) {
        Account account = new Account(user);
        save(account);
        return account;
    }

    @Override
    public Account getById(Long id) {
        return accountRepository.findById(id).orElseThrow(() -> new NotFoundException("Account not found"));
    }

    @Override
    @Transactional
    public void plus(Long accountId, Double sum) {
        Account account = getById(accountId);
        account.plus(sum);
        save(account);
    }

    @Override
    @Transactional
    public void minus(Long accountId, Double sum) {
        Account account = getById(accountId);
        account.minus(sum);
        save(account);
    }


}
