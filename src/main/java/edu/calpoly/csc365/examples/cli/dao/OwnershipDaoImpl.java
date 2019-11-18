package edu.calpoly.csc365.examples.cli.dao;

import edu.calpoly.csc365.examples.cli.entity.Ownership;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class OwnershipDaoImpl implements Dao<Ownership> {
  private Connection conn;

  public OwnershipDaoImpl(Connection conn) {
    this.conn = conn;
  }

  @Override
  public Ownership getById(int id) {
    Ownership ownership = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    try {
      preparedStatement = this.conn.prepareStatement("SELECT * FROM Ownership WHERE owner_id=?");
      preparedStatement.setInt(1, id);
      resultSet = preparedStatement.executeQuery();
      Set<Ownership> ownerships = unpackResultSet(resultSet);
      ownership = (Ownership)ownerships.toArray()[0];
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
    return ownership;
  }

  @Override
  public Set<Ownership> getAll() {
    Set<Ownership> ownerships = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    try {
      preparedStatement = this.conn.prepareStatement("SELECT * FROM Ownership");
      resultSet = preparedStatement.executeQuery();
      ownerships = unpackResultSet(resultSet);
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
    return ownerships;
  }

  @Override
  public Boolean insert(Ownership obj) {
    return null;
  }

  @Override
  public Boolean update(Ownership obj) {
    try {
      PreparedStatement preparedStatement = this.conn.prepareStatement(
        "UPDATE Ownership SET owner_id=?, card_number=?, current=? WHERE owner_id=? and card_number=?");
      preparedStatement.setInt(1, obj.getOwnerId());
      preparedStatement.setInt(2, obj.getCardNumber());
      preparedStatement.setBoolean(3, obj.getCurrent());
      preparedStatement.setInt(4, obj.getOwnerId());
      preparedStatement.setInt(5, obj.getCardNumber());
      preparedStatement.execute();
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  @Override
  public Boolean delete(Ownership obj) {
    return null;
  }

  private Set<Ownership> unpackResultSet(ResultSet rs) throws SQLException {
    Set<Ownership> ownerships = new HashSet<Ownership>();

    while(rs.next()) {
      Ownership ownership = new Ownership(
        rs.getInt("owner_id"),
        rs.getInt("card_number"),
        (rs.getInt("current") == 1));
      ownerships.add(ownership);
    }
    return ownerships;
  }

  @Override
  protected void finalize() throws Throwable {
    super.finalize();
  }
}
