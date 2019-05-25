package com.sca.ShoppingCartAppMind.daoImpl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sca.ShoppingCartAppMind.dao.AdminService;
import com.sca.ShoppingCartAppMind.dto.UserSession;
import com.sca.ShoppingCartAppMind.exception.DaoException;
import com.sca.ShoppingCartAppMind.model.Admin;
import com.sca.ShoppingCartAppMind.model.Apparal;
import com.sca.ShoppingCartAppMind.model.Book;
import com.sca.ShoppingCartAppMind.model.Product;
import com.sca.ShoppingCartAppMind.model.User;



@Repository
@Transactional
public class AdminServiceImpl implements AdminService {

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public String addBook(Book book) throws DaoException {
		if (UserSession.adminUserName.equals("admin")) {
			List<Product> products = getSession().createQuery("FROM Product WHERE prodName = :prodName ")
					.setParameter("prodName", book.getProdName()).list();
			if (products.isEmpty()) {
				getSession().save(book);
				return "New item added.";
			}
			return "Item with the name is already in the Prodduct section.";
		}
		throw new DaoException("Need admin access.");
	}

	@Override
	public String addApparal(Apparal apparal) throws DaoException {
		if (UserSession.adminUserName.equals("admin")) {
			List<Product> products = getSession().createQuery("FROM Product WHERE prodName =  :prodName")
					.setParameter("prodName", apparal.getProdName()).list();
			if (products.isEmpty()) {
				getSession().save(apparal);
				return "New item added.";
			}
			return "Item with the name is already in the Prodduct section.";
		}
		throw new DaoException("Need admin access.");
	}

	@Override
	public String updateProductPrice(int productId, int price) throws DaoException {
		if (UserSession.adminUserName.equals("admin")) {

			List<Product> products = getSession().createQuery("FROM Product WHERE productId = :productId")
					.setParameter("productId", productId).list();
			if (products.get(0) == null) {
				return "no product is associated with id: " + productId;
			}
			products.get(0).setPrice(price);
			getSession().update(products.get(0));
			return "The price is updated for the product - " + products.get(0).getProdName();	
		}
		throw new DaoException("Need admin access.");
	}

	@Override
	public String deleteProduct() throws DaoException {
		if (UserSession.adminUserName.equals("admin")) {

		}
		throw new DaoException("Need admin access.");
	}

	@Override
	public String adminLogin(Admin admin) throws DaoException {
		// TODO Auto-generated method stub
		List<Admin> admins = getSession().createQuery("FROM Admin where adminUserName = :adminUserName")
				.setParameter("adminUserName", admin.getAdminUserName()).list();
		if (admins.isEmpty()) {
			throw new DaoException("Not a admin.");
		} else if (admins.get(0).getAdminPassword().equals(admin.getAdminPassword())) {
			UserSession.adminUserName = admin.getAdminUserName();
			return "logged in sucessfully";
		}
		return "Password didnt match";
	}

	@Override
	public String addAdmin() {
		// TODO Auto-generated method stub
		Admin admin = new Admin();
		admin.setAdminUserName("admin");
		admin.setAdminPassword("admin123");
		getSession().save(admin);
		return "Added";
	}

	@Override
	public List<String> totalUsers() throws DaoException{
		if (UserSession.adminUserName.equals("admin")) {
		// TODO Auto-generated method stub
		List<String> userNames = new ArrayList<>();
		List<User> users = getSession().createQuery("FROM User").list();
		for(User u : users) {
			userNames.add(u.getUserName());
		}
		return userNames;
		}
		throw new DaoException("Need admin access.");
	}

}
