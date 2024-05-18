package my.finance.hackathon.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import my.finance.hackathon.app.model.Account;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.List;

@Getter
public class SimpleUserDto {
    private final String userName;
    private final String email;
    @JsonIgnore
    private final String sid;
    private List<SimpleAccountDto> accounts;


    public SimpleUserDto(JwtAuthenticationToken principal) {
        userName = principal.getName();
        email = (String) principal.getTokenAttributes().get("email");
        sid = (String) principal.getTokenAttributes().get("sid");
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts.stream().map(
                (account) -> new SimpleAccountDto(account.getBalance(),
                        account.getCredit(),
                        account.getOverdraft(),
                        account.getAccountType())).toList();
    }

    public String toString() {
        return userName + " " + email + " " + sid;
    }
}
