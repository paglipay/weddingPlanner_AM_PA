package weddingPlanner_AM_PA.services;

import java.text.DecimalFormat;
import java.util.ArrayList;

import weddingPlanner_AM_PA.daos.TransactionDAO;
import weddingPlanner_AM_PA.models.Account;
import weddingPlanner_AM_PA.models.Transaction;
import weddingPlanner_AM_PA.util.logging.Logger;
import weddingPlanner_AM_PM.exceptions.InvalidRequestException;
import weddingPlanner_AM_PM.exceptions.ResourcePersistenceException;

public class TransactionService {

	private TransactionDAO transactionDAO;
	private final AccountService accountService;
	private final Logger logger;
	public TransactionService(TransactionDAO transactionDAO, AccountService accountService) {
		this.accountService = accountService;
		this.transactionDAO = transactionDAO;
		
		logger = Logger.getLogger(true);
		logger.log("TransactionService is initiliazing.....");
	}

	public ArrayList<Transaction> getTransactionsByAccount(String id) {
		ArrayList<Transaction> userAccounts = transactionDAO.findByAccountId(id);
		return userAccounts;
	}

	public void createTransaction(Transaction newTransaction) {
		if (!isTransactionValid(newTransaction)) {
			throw new InvalidRequestException("The Transaction was provided invalid information");
		}

		newTransaction.setAccount(accountService.getSessionAccount());
		Transaction createdTransaction = transactionDAO.create(newTransaction);

		if (createdTransaction == null) {
			throw new ResourcePersistenceException("The Transaction could not be persisted");
		}
		
		Account acSession = accountService.getSessionAccount();
		logger.log("newTransaction: " + newTransaction.getAmount());	
		DecimalFormat twoPlaces = new DecimalFormat("0.00");
		acSession.setAmount(String.valueOf(twoPlaces.format(Double.parseDouble(acSession.getAmount()) - newTransaction.getAmount())));		

		logger.log("acSession: " + acSession.getAmount());
		
		try {
			accountService.updateAccount(acSession);
		}catch(InvalidRequestException e) {
			System.out.println("InvalidRequestException: acSession" + acSession.getAmount() + e);
		}
		
	}

	public void receiveTransaction(Transaction newTransaction, Account sendToAccount) {
		if (!isTransactionValid(newTransaction)) {
			throw new InvalidRequestException("The Transaction was provided invalid information");
		}

		newTransaction.setAccount(sendToAccount);
		Transaction createdTransaction = transactionDAO.create(newTransaction);

		if (createdTransaction == null) {
			throw new ResourcePersistenceException("The Transaction could not be persisted");
		}
		
		Account acSession = sendToAccount;
		logger.log("newTransaction: " + newTransaction.getAmount());	
		DecimalFormat twoPlaces = new DecimalFormat("0.00");
		acSession.setAmount(String.valueOf(twoPlaces.format(Double.parseDouble(acSession.getAmount()) + newTransaction.getAmount())));		

		logger.log("acSession: " + acSession.getAmount());
		
		try {
			accountService.updateAccount(acSession);
		}catch(InvalidRequestException e) {
			System.out.println("InvalidRequestException: acSession" + acSession.getAmount() + e);
		}
		
	}
	
	private boolean isTransactionValid(Transaction newTransaction) {

		if (newTransaction == null)
			return false;
		if (newTransaction.getDescription() == null || newTransaction.getDescription().trim().equals(""))
			return false;
		if(newTransaction.getAmount() == null  || Double.valueOf(newTransaction.getAmount()) > 2000 || Double.valueOf(newTransaction.getAmount()) < -2000) return false;
		return newTransaction.getAmount() != null;
	}
}
