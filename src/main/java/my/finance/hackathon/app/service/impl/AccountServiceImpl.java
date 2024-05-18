package my.finance.hackathon.app.service.impl;

import lombok.RequiredArgsConstructor;
import my.finance.hackathon.app.model.Account;
import my.finance.hackathon.app.repository.AccountRepository;
import my.finance.hackathon.app.service.AccountService;
import org.springframework.stereotype.Service;

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
}
