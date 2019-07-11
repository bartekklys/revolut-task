package pl.bartekk.revoluttask.model;

import lombok.Getter;
import pl.bartekk.revoluttask.exception.NotEnoughFundsException;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    @Getter
    private String accountNumber;

    @Column
    @Getter
    private BigDecimal balance;

    public Account() {
        this.accountNumber = UUID.randomUUID().toString();
        this.balance = BigDecimal.ZERO;
    }

    public void updateBalance(BigDecimal amount) throws NotEnoughFundsException {
        // withdraw if amount is negative
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            if (hasSufficientFunds(amount.negate())) {
                balance = balance.add(amount);
            } else {
                throw new NotEnoughFundsException("This account has not enough funds to perform this operation");
            }
        } else {
            // deposit otherwise
            balance = balance.add(amount);
        }
    }

    private boolean hasSufficientFunds(BigDecimal amount) {
        return balance.compareTo(amount) > 0;
    }
}
