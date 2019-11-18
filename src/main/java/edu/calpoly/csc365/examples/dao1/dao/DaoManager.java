package edu.calpoly.csc365.examples.dao1.dao;

import edu.calpoly.csc365.examples.dao1.entity.Customer;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DaoManager {

  private String driver = null;
  private String url = null;
  private String user = null;
  private String pass = null;

  private static class DAOManagerHolder {
    public static final ThreadLocal<DaoManager> instance;

    static
    {
      ThreadLocal<DaoManager> manager;
      try
      {
        manager = new ThreadLocal<DaoManager>(){
          @Override
          protected DaoManager initialValue() {
            try
            {
              return new DaoManager();
            }
            catch(Exception e)
            {
              return null;
            }
          }
        };
      }
      catch(Exception e) {
        manager = null;
      }
      instance = manager;
    }
  }

  //Private
  private Connection conn;

  private DaoManager() {
  }

  public static DaoManager getInstance() {
    return DAOManagerHolder.instance.get();
  }

  public Connection getConnection() {
    return this.conn;
  }

  public void open() throws SQLException {
    try
    {
      if(this.conn == null || this.conn.isClosed())
        this.conn = ConnectionFactory.getConnection(this.driver, this.url, this.user, this.pass);
    }
    catch(SQLException e) { throw e; }
  }

  public void close() throws SQLException {
    try
    {
      if(this.conn != null && !this.conn.isClosed())
        this.conn.close();
    }
    catch(SQLException e) { throw e; }
  }

  public Object transaction(DaoCommand command){
    try{
      this.conn.setAutoCommit(false);
      Object returnValue = command.execute(this);
      this.conn.commit();
      return returnValue;
    } catch(Exception e){
      try {
        this.conn.rollback();
      } catch (SQLException ex) {
        ex.printStackTrace();
      }
    } finally {
      try {
        this.conn.setAutoCommit(true);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  public Dao<Customer> getCustomerDao() {
    return new CustomerDaoImpl(this.conn);
  }

  public CachedDao<Customer> getCustomerCachedDao() {
    return new CustomerCachedDaoImpl(this.conn);
  }

  public ResultSetDao<Customer> getCustomerResultSetDao() {
    return new CustomerResultSetDaoImpl(this.conn);
  }

  public DaoManager setProperties(String fileName) throws IOException, SQLException {
    Properties prop = new Properties();
    FileInputStream fis = null;
    fis = new FileInputStream(fileName);
    prop.loadFromXML(fis);

    driver = prop.getProperty("driver");
    url = prop.getProperty("url");
    user = prop.getProperty("user");
    pass = prop.getProperty("pass");
    this.open();
    return this;
  }
}
