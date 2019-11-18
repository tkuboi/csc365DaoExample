package edu.calpoly.csc365.examples.cli.dao;

import edu.calpoly.csc365.examples.cli.entity.Vendor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class VendorDaoImpl implements Dao<Vendor> {
  private Connection conn;

  public VendorDaoImpl(Connection conn) {
    this.conn = conn;
  }

  @Override
  public Vendor getById(int id) {
    Vendor vendor = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    try {
      preparedStatement = this.conn.prepareStatement("SELECT * FROM Vendor WHERE id=?");
      preparedStatement.setInt(1, id);
      resultSet = preparedStatement.executeQuery();
      Set<Vendor> vendors = unpackResultSet(resultSet);
      vendor = (Vendor)vendors.toArray()[0];
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
    return vendor;
  }

  @Override
  public Set<Vendor> getAll() {
    Set<Vendor> vendors = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    try {
      preparedStatement = this.conn.prepareStatement("SELECT * FROM Vendor");
      resultSet = preparedStatement.executeQuery();
      vendors = unpackResultSet(resultSet);
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
    return vendors;
  }

  @Override
  public Boolean insert(Vendor obj) {
    return null;
  }

  @Override
  public Boolean update(Vendor obj) {
    try {
      PreparedStatement preparedStatement = this.conn.prepareStatement(
        "UPDATE Vendor SET name=?, address=? WHERE id=?");
      preparedStatement.setString(1, obj.getName());
      preparedStatement.setString(2, obj.getAddress());
      preparedStatement.setInt(3, obj.getId());
      preparedStatement.execute();
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  @Override
  public Boolean delete(Vendor obj) {
    return null;
  }

  private Set<Vendor> unpackResultSet(ResultSet rs) throws SQLException {
    Set<Vendor> vendors = new HashSet<Vendor>();

    while(rs.next()) {
      Vendor vendor = new Vendor(
        rs.getInt("id"),
        rs.getString("name"),
        rs.getString("address"));
      vendors.add(vendor);
    }
    return vendors;
  }

  @Override
  protected void finalize() throws Throwable {
    super.finalize();
  }
}
