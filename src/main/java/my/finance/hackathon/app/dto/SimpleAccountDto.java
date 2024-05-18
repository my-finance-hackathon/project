package my.finance.hackathon.app.dto;


import my.finance.hackathon.app.model.AccountType;

public class SimpleAccountDto {
    private final Double balance;
    private final Double credit;
    private final Boolean overdraft;
    private final String accountType;

    public SimpleAccountDto(Double balance, Double credit, Boolean overdraft, AccountType accountType) {
        this.balance = balance;
        this.credit = credit;
        this.overdraft = overdraft;
        this.accountType = accountType.toString();
    }
}
