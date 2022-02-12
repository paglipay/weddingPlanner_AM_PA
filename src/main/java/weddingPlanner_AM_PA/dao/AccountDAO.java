package weddingPlanner_AM_PA.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

import weddingPlanner_AM_PA.models.Account;

import weddingPlanner_AM_PA.util.collections.List;
import weddingPlanner_AM_PA.util.datasource.ConnectionFactory;

public class AccountDAO implements CrudDAO<Account> {
	public List<Account> findAccountByCreatorId(String id) {
		return null;
	}

	@Override
	public Account create(Account account) {
		try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

			account.setId(UUID.randomUUID().toString());

			String sql = "insert into accounts (id, description, amount, user_id) values (?, ?, ?, ?)";

			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, account.getId());
			ps.setString(2, account.getDescription());
			ps.setString(3, account.getAmount());
			ps.setString(4, account.getUser().getId());

			int rowsInserted = ps.executeUpdate();

			if (rowsInserted != 0) {
				return account;
			}

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public List<Account> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Account findById(String id) {

		try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
			String sql = "select * from accounts where id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Account account = new Account();
				account.setId(rs.getString("id"));
				account.setDescription(rs.getString("description"));
				account.setAmount(rs.getString("amount"));
				return account;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean update(Account account) {
		try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

			String sql = "update accounts SET description = ?, amount = ? where id = ?";

			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, account.getDescription());
			ps.setString(2, account.getAmount());
			ps.setString(3, account.getId());

			int rowsInserted = ps.executeUpdate();

			if (rowsInserted != 0) {
				return true;
			}

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return false;		
	}

	@Override
	public boolean delete(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	public ArrayList<Account> findByUserId(String userId) {

		ArrayList<Account> results = new ArrayList<>();
		try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
			
			String sql = "select * from accounts where user_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, userId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Account account = new Account();
				account.setId(rs.getString("id"));
				account.setDescription(rs.getString("description"));
				account.setAmount(rs.getString("amount"));

				results.add(account);
			}
			return results;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
