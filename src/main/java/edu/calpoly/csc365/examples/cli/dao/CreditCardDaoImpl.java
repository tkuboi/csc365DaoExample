package edu.calpoly.csc365.examples.cli.dao;

import edu.calpoly.csc365.examples.cli.entity.CreditCard;
import edu.calpoly.csc365.examples.cli.entity.CreditCard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class CreditCardDaoImpl implements Dao<CreditCard> {
  private Connection conn;

  public CreditCardDaoImpl(Connection conn) {
    this.conn = conn;
  }

  @Override
  public CreditCard getById(int id) {
    CreditCard creditCard = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    try {
      preparedStatement = this.conn.prepareStatement("SELECT * FROM CreditCard WHERE card_number=?");
      preparedStatement.setInt(1, id);
      resultSet = preparedStatement.executeQuery();
      Set<CreditCard> creditCards = unpackResultSet(resultSet);
      creditCard = (CreditCard) creditCards.toArray()[0];
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
    return creditCard;
  }

  @Override
  public Set<CreditCard> getAll() {
    Set<CreditCard> creditCards = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    try {
      preparedStatement = this.conn.prepareStatement("SELECT * FROM CreditCard");
      resultSet = preparedStatement.executeQuery();
      creditCards = unpackResultSet(resultSet);
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
    return creditCards;
  }

  @Override
  public Boolean insert(CreditCard obj) {
    return null;
  }

  @Override
  public Boolean update(CreditCard obj) {
    try {
      PreparedStatement preparedStatement = this.conn.prepareStatement(
        "UPDATE CreditCard SET type=?, limit=?, balance=?, active=? WHERE card_number=?");
      preparedStatement.setString(1, obj.getType());
      preparedStatement.setDouble(2, obj.getLimit());
      preparedStatement.setDouble(3, obj.getBalance());
      preparedStatement.setInt(4, obj.getActive() ? 1 : 0);
      preparedStatement.setInt(5, obj.getCardNumber());
      preparedStatement.execute();
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  @Override
  public Boolean delete(CreditCard obj) {
    return null;
  }

  private Set<CreditCard> unpackResultSet(ResultSet rs) throws SQLException {
    Set<CreditCard> creditCards = new HashSet<CreditCard>();

    while(rs.next()) {
      CreditCard creditCard = new CreditCard(
        rs.getInt("card_number"),
        rs.getString("type"),
        rs.getDouble("limit"),
        rs.getDouble("balance"),
        (rs.getInt("active") == 1));
      creditCards.add(creditCard);
    }
    return creditCards;
  }

  @Override
  protected void finalize() throws Throwable {
    super.finalize();
  }
}
