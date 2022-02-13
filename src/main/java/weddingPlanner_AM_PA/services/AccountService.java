package weddingPlanner_AM_PA.services;

import java.util.ArrayList;

import weddingPlanner_AM_PA.daos.AccountDAO;
import weddingPlanner_AM_PA.daos.UserDAO;
import weddingPlanner_AM_PA.models.Account;
import weddingPlanner_AM_PA.models.User;
import weddingPlanner_AM_PA.util.collections.LinkedList;
import weddingPlanner_AM_PA.util.logging.Logger;
import weddingPlanner_AM_PM.exceptions.InvalidRequestException;
import weddingPlanner_AM_PM.exceptions.ResourcePersistenceException;

public class AccountService {
	private final AccountDAO accountDAO;
	private final UserService userService;
	private Account sessionAccount;
	private final Logger logger;

	public void setSessionAccount(Account sessionAccount) {
		this.sessionAccount = sessionAccount;
	}

	public AccountService(AccountDAO accountDAO, UserService userService) {
		this.accountDAO = accountDAO;
		this.userService = userService;
		this.sessionAccount = null;

		logger = Logger.getLogger(true);
		logger.log("AccountService is initiliazing.....");
	}

	public Account getSessionAccount() {
		return sessionAccount;
	}

	public void createAccount(Account newAccount) {
		if (!isAccountValid(newAccount)) {
			throw new InvalidRequestException("The Account was provided invalid information");
		}

		newAccount.setUser(userService.getSessionUser());
		Account createdAccount = accountDAO.create(newAccount);

		if (createdAccount == null) {
			throw new ResourcePersistenceException("The Account could not be persisted");
		} else {
			setSessionAccount(createdAccount);
		}
	}

	public void updateAccount(Account newAccount) {
		if (!isAccountValid(newAccount)) {
			throw new InvalidRequestException("The Account was provided invalid information");
		}

		newAccount.setUser(userService.getSessionUser());
		boolean createdAccount = accountDAO.update(newAccount);

		if (!createdAccount) {
			throw new ResourcePersistenceException("The Account could not be persisted");
		} else {
			setSessionAccount(newAccount);
		}		
		
	}

	public Account getAccountById(String id) {

		Account userAccount = accountDAO.findById(id);

		sessionAccount = userAccount;
		return sessionAccount;
	}

	public Account getAccountByIdNoSess(String id) {

		Account userAccount = accountDAO.findById(id);

		return userAccount;
	}

	public ArrayList<Account> getAccounts() {

		ArrayList<Account> userAccounts = accountDAO.findByUserId(userService.getSessionUser().getId());

		return userAccounts;
	}

	private boolean isAccountValid(Account newAccount) {
//		System.out.println("newAccount.getAmount(): " + newAccount.getAmount());
		logger.log("newAccount.getAmount(): " + newAccount.getAmount());
		if (newAccount == null)
			return false;
		if (newAccount.getDescription() == null || newAccount.getDescription().trim().equals(""))
			return false;
		if(newAccount.getAmount() == null || newAccount.getAmount().trim().equals("") || 
				Double.compare(Double.parseDouble(newAccount.getAmount()), 20000000.00) > 0 || 
				Double.compare(Double.parseDouble(newAccount.getAmount()), 0.00) < 0 ) return false;
		return newAccount.getAmount() != null || !newAccount.getAmount().trim().equals("");
	}
}
