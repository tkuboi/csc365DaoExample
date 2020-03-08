package edu.calpoly.csc365.examples.cli.dao;

import edu.calpoly.csc365.examples.cli.entity.Payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class PaymentDaoImpl implements Dao<Payment> {
  private Connection conn;

  public PaymentDaoImpl(Connection conn) {
    this.conn = conn;
  }

  @Override
  public Payment getById(int id) {
    Payment payment = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    try {
      preparedStatement = this.conn.prepareStatement("SELECT * FROM Payment WHERE id=?");
      preparedStatement.setInt(1, id);
      resultSet = preparedStatement.executeQuery();
      Set<Payment> payments = unpackResultSet(resultSet);
      payment = (Payment)payments.toArray()[0];
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
    return payment;
  }

  @Override
  public Set<Payment> getAll() {
    Set<Payment> payments = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    try {
      preparedStatement = this.conn.prepareStatement("SELECT * FROM Payment");
      resultSet = preparedStatement.executeQuery();
      payments = unpackResultSet(resultSet);
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
    return payments;
  }

  @Override
  public Boolean insert(Payment obj) {
    return null;
  }

  @Override
  public Boolean update(Payment obj) {
    try {
      PreparedStatement preparedStatement = this.conn.prepareStatement(
        "UPDATE Payment SET card_number=?, date=?, amount=? WHERE id=?");
      preparedStatement.setInt(1, obj.getCardNumber());
      preparedStatement.setDate(2, obj.getDate());
      preparedStatement.setDouble(3, obj.getAmount());
      preparedStatement.setInt(4, obj.getId());
      preparedStatement.execute();
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  @Override
  public Boolean delete(Payment obj) {
    return null;
  }

  private Set<Payment> unpackResultSet(ResultSet rs) throws SQLException {
    Set<Payment> payments = new HashSet<Payment>();

    while(rs.next()) {
      Payment payment = new Payment(
        rs.getInt("id"),
        rs.getInt("card_number"),
        rs.getDate("date"),
        rs.getDouble("amount"));
      payments.add(payment);
    }
    return payments;
  }

  @Override
  protected void finalize() throws Throwable {
    super.finalize();
  }
}
