package my.finance.hackathon.app.service;

import lombok.RequiredArgsConstructor;
import my.finance.hackathon.app.exceptions.OperationException;
import my.finance.hackathon.app.model.Account;
import my.finance.hackathon.app.model.User;
import my.finance.hackathon.app.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor
class AccountServiceTest {

    private final AccountService accountService;

    @MockBean
    private AccountRepository accountRepository;

    @Test
    void save() {
        accountService.save(getAccount());
        Mockito.verify(accountRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    void getByUserId() {
        Account firstAccount = getAccount();
        firstAccount.setName("first");
        Account secondAccount = getAccount();
        secondAccount.setName("second");

        Mockito.when(accountRepository.findByUserId(Mockito.any())).thenReturn(List.of(firstAccount, secondAccount));

        List<Account> accounts = accountService.getByUserId(1L);
        assertEquals(2, accounts.size());
        assertTrue(accounts.contains(firstAccount));
        assertTrue(accounts.contains(secondAccount));
    }

    @Test
    void createAccountForUser() {
        User user = getUser();
        Account account = accountService.createAccountForUser(user);
        assertEquals(account.getUser(), user);
        assertEquals(0, account.getBalance());
        assertEquals(0, account.getCredit());
    }

    @Test
    void getById() {
        Mockito.when(accountRepository.findById(Mockito.any())).thenReturn(Optional.of(getAccount()));
        var account = accountService.getById(1L);
        assertEquals("account", account.getName());
        assertEquals(account.getBalance(), 0);
        assertEquals(account.getCredit(), 0);
    }

    @Test
    void plus() {
        Account account = getAccount();
        Mockito.when(accountRepository.findById(Mockito.any())).thenReturn(Optional.of(account));
        accountService.plus(1L, 120D);
        assertEquals(account.getBalance(), 120D);
    }

    @Test
    void minus() {
        Account account = getAccount();
        account.plus(120D);
        Mockito.when(accountRepository.findById(Mockito.any())).thenReturn(Optional.of(account));
        accountService.minus(1L, 120D);
        assertEquals(account.getBalance(), 0.0);
    }

    @Test
    void minusException() {
        Account account = getAccount();
        account.plus(120D);
        Mockito.when(accountRepository.findById(Mockito.any())).thenReturn(Optional.of(account));
        assertThrows(OperationException.class, () -> accountService.minus(1L, 120D));
    }

    Account getAccount() {
        return new Account(getUser());
    }

    User getUser() {
        User user = new User();
        user.setUsername("userName");
        user.setEmail("userEmail@mail.ru");
        user.enable();
        return user;
    }

}