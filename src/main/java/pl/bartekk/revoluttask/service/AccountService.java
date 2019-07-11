package pl.bartekk.revoluttask.service;

import lombok.extern.slf4j.Slf4j;
import pl.bartekk.revoluttask.exception.NotEnoughFundsException;
import pl.bartekk.revoluttask.exception.UserNotFoundException;
import pl.bartekk.revoluttask.model.Account;
import pl.bartekk.revoluttask.model.User;
import pl.bartekk.revoluttask.repository.UserDao;

import java.math.BigDecimal;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class AccountService {

    private UserDao userDao = UserDao.getInstance();
    private static AccountService instance;
    private Lock transferLock = new ReentrantLock(); // to avoid corruption of shared data by multiple threads

    private AccountService() {
    }

    public static AccountService getInstance() {
        return instance == null ? instance = new AccountService() : instance;
    }

    public boolean updateBalance(String name, BigDecimal amount) throws NotEnoughFundsException {
        return userDao.updateBalance(name, amount);
    }

    public void transferMoney(String from, String to, BigDecimal amount) throws NotEnoughFundsException, UserNotFoundException {
        transferLock.lock();
        try {
            User fromUser = userDao.getUser(from);
            Account fromAccount = fromUser.getAccount();
            if (fromAccount.getBalance().compareTo(amount) >= 0) {
                userDao.transferMoney(fromUser, to, amount);
            } else {
                log.error(String.format("Account %s has not enough funds to perform this operation.", from));
                throw new NotEnoughFundsException("This account has not enough funds to perform this operation");
            }
        } catch (UserNotFoundException e) {
            log.error("User not found.");
        }
        finally {
            transferLock.unlock();
        }
    }
}
