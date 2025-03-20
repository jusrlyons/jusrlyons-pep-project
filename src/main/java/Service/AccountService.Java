package Service;

import Model.Account;
import DAO.AccountDAO;

import java.util.List;

public class AccountService {

    private AccountDAO accountDAO;

    public AccountService() {
        this.accountDAO = new AccountDAO(); // Assuming AccountDAO handles DB interactions
    }

    // Create a new account
    public Account createAccount(String username, String password) {
        // You can add business logic to validate or manipulate data before saving
        Account newAccount = new Account(username, password);
        return accountDAO.createAccount(newAccount);
    }

    // Get account by ID
    public Account getAccountById(int accountId) {
        return accountDAO.getAccountById(accountId);
    }

    // Get all accounts
    public List<Account> getAllAccounts() {
        return accountDAO.getAllAccounts();
    }

    // Update account details
    public Account updateAccount(int accountId, String newUsername, String newPassword) {
        Account updatedAccount = new Account(accountId, newUsername, newPassword);
        return accountDAO.updateAccount(updatedAccount);
    }

    // Delete account by ID
    public boolean deleteAccount(int accountId) {
        return accountDAO.deleteAccount(accountId);
    }
}