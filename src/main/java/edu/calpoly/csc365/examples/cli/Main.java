package edu.calpoly.csc365.examples.cli;

import edu.calpoly.csc365.examples.cli.dao.ConnectionFactory;
import edu.calpoly.csc365.examples.cli.dao.Dao;
import edu.calpoly.csc365.examples.cli.dao.DaoManager;
import edu.calpoly.csc365.examples.cli.entity.Customer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Set;

public class Main {
  public static void main(String[] args) {
    String credFile = args[0];
    Properties prop = new Properties();
    FileInputStream fis = null;
    try {
      fis = new FileInputStream(credFile);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    try {
      prop.loadFromXML(fis);
      DaoManager daoManager = new DaoManager(new ConnectionFactory(
        prop.getProperty("driver"),
        prop.getProperty("url"),
        prop.getProperty("user"),
        prop.getProperty("pass")
      ));
      Dao<Customer> customerDao = daoManager.getCustomerDao();
      Set<Customer> customers = customerDao.getAll();
      for (Customer customer : customers) {
        System.out.println(customer);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
