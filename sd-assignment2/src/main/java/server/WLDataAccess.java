package server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.BoughtItem;
import model.ShopItem;
import model.User;

public class WLDataAccess {

	private static WLDataAccess dataAccess = null;
	private static SessionFactory factory;
	private Session session;
	private int id;
	
	public WLDataAccess() {
		factory = new Configuration().configure().addAnnotatedClass(BoughtItem.class).addAnnotatedClass(ShopItem.class).addAnnotatedClass(User.class).buildSessionFactory();
	}
	
	public static WLDataAccess getInstance() 
    { 
        if (dataAccess == null) 
            dataAccess = new WLDataAccess(); 
        return dataAccess; 
    } 
	
	public boolean login(String username, String password) {
			session = factory.getCurrentSession();
			session.beginTransaction();
			Query query = session.createSQLQuery("select id_users from users where username = :username and password = :password and status=0")
					    .setParameter("username", username)
					    .setParameter("password", password);
			if(query.list().isEmpty()) {
				session.close();
				return false;
			}
			id = (int) query.getResultList().get(0);
			session.get(User.class,id).setStatus(1);
			query = session.createSQLQuery(
				    "update users set status = 0" + " where username = :username ");
			query.setParameter("username", username);
			query.executeUpdate();
			session.getTransaction().commit();
			session.close();
			return true;
	}
	public void logout(String username,String password){
		try {
			session = factory.getCurrentSession();
			session.beginTransaction();
			Query query = session.createSQLQuery(
				    "update users set status = 0" + " where username = :username");
			query.setParameter("username", username);
			query.executeUpdate();
			session.getTransaction().commit();
		}
 	    finally {
 	    	session.close();
 		    //factory.close();
 		    }
    }
	
	public void createItems(List<BoughtItem> items) {
		try {
			session = factory.getCurrentSession();
			session.beginTransaction();
			for(BoughtItem item : items) {
				item.setIdUser(id);
				session.save(item);
			}
			session.getTransaction().commit();
		}
		catch(Exception e) {
			Alert failedLogin = new Alert(AlertType.ERROR);
			failedLogin.setHeaderText("Create Item failed!");
			failedLogin.setContentText(e.toString());
			failedLogin.showAndWait();
		}
		finally {
			session.close();
		}
	}
	
	public List<ShopItem> readAllGroceries() {
		List<ShopItem> groceries = null;
		try {
			session = factory.getCurrentSession();
			session.beginTransaction();
			Query query = session.createQuery("from ShopItem");
			groceries = query.list();
			session.getTransaction().commit();
		}
		finally{
			session.close();
		}
		return groceries;
	}
	
	public List<BoughtItem> readAllItems(String username) {
		List<BoughtItem> items = null;
		try {
			session = factory.getCurrentSession();
			session.beginTransaction();
			items = session.createQuery("from BoughtItem w where w.idUser = :id").setParameter("id",id).list();
			session.getTransaction().commit();
		}
		catch(Exception e) {
			System.out.println(e.toString());
		}
		finally {
			session.close();
		}
		return items;
	}

	public List<BoughtItem> readUnusedItems(String username) {
		List<BoughtItem> items = null;
		try {
			session = factory.getCurrentSession();
			session.beginTransaction();
			items = session.createQuery("from BoughtItem w where w.idUser = :id and w.consumptionDate = null").setParameter("id",id).list();
			session.getTransaction().commit();
		}
		catch(Exception e) {
			System.out.println(e.toString());
		}
		finally {
			session.close();
		}
		return items;
	}
	
	public void updateItems(List<String> removedItems) {
		try {
			session = factory.getCurrentSession();
			session.beginTransaction();
			for(String itemName : removedItems) {
				Query query = session.createQuery("update BoughtItem w set w.consumptionDate = :today where w.name = :name").setParameter("today",new Date()).setParameter("name", itemName);
				query.executeUpdate();
			}
			session.getTransaction().commit();
		}
		finally {
			session.close();
		}
	}
	
	//called at the beginning of the application
	//checks the item database and moves items that have been wasted to the report
	public List<BoughtItem> initWastedItems() {
		List<BoughtItem> wastedItems = null;
		try {
			Date today = new Date();
			session = factory.getCurrentSession();
			session.beginTransaction();
			Query query = session.createQuery("from BoughtItem w where w.expirationDate < :date and w.idUser = :id").setParameter("date",today).setParameter("id",id);
			wastedItems = query.list();
			for(BoughtItem i : wastedItems) 
				session.createQuery("delete BoughtItem w where w.name = :name").setParameter("name", i.getName()).executeUpdate();
			session.getTransaction().commit();
		}
		finally {
			session.close();
		}
		return wastedItems;
	}
	

}
