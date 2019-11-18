package edu.calpoly.csc365.examples.cli.dao;

import edu.calpoly.csc365.examples.cli.entity.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class TransactionDaoImpl implements Dao<Transaction> {
  private Connection conn;

  public TransactionDaoImpl(Connection conn) {
    this.conn = conn;
  }

  @Override
  public Transaction getById(int id) {
    Transaction transaction = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    try {
      preparedStatement = this.conn.prepareStatement("SELECT * FROM Transaction WHERE id=?");
      preparedStatement.setInt(1, id);
      resultSet = preparedStatement.executeQuery();
      Set<Transaction> transactions = unpackResultSet(resultSet);
      transaction = (Transaction)transactions.toArray()[0];
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if (resultSet != null)
          resultSet.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
      try {
        if (preparedStatement != null)
          preparedStatement.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return transaction;
  }

  @Override
  public Set<Transaction> getAll() {
    Set<Transaction> transactions = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    try {
      preparedStatement = this.conn.prepareStatement("SELECT * FROM Transaction");
      resultSet = preparedStatement.executeQuery();
      transactions = unpackResultSet(resultSet);
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if (resultSet != null)
          resultSet.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
      try {
        if (preparedStatement != null)
          preparedStatement.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return transactions;
  }

  @Override
  public Boolean insert(Transaction obj) {
    return null;
  }

  @Override
  public Boolean update(Transaction obj) {
    try {
      PreparedStatement preparedStatement = this.conn.prepareStatement(
        "UPDATE Transaction SET customer_id=?, acrd_number=?, date=?, amount=? WHERE id=?");
      preparedStatement.setInt(1, obj.getCustomerId());
      preparedStatement.setInt(2, obj.getCardNumber());
      preparedStatement.setDate(3, obj.getDate());
      preparedStatement.setDouble(4, obj.getAmount());
      preparedStatement.setInt(5, obj.getId());
      preparedStatement.execute();
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  @Override
  public Boolean delete(Transaction obj) {
    return null;
  }

  private Set<Transaction> unpackResultSet(ResultSet rs) throws SQLException {
    Set<Transaction> transactions = new HashSet<Transaction>();

    while(rs.next()) {
      Transaction transaction = new Transaction(
        rs.getInt("id"),
        rs.getInt("customer_id"),
        rs.getInt("card_number"),
        rs.getDate("date"),
        rs.getDouble("amount"));
      transactions.add(transaction);
    }
    return transactions;
  }

  @Override
  protected void finalize() throws Throwable {
    super.finalize();
  }
}
