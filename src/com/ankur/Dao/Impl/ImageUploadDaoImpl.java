package com.ankur.Dao.Impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ankur.Dao.ImageUploadDao;
import com.ankur.Model.FileUpload;

@Repository
public class ImageUploadDaoImpl implements ImageUploadDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Override
	@Transactional
	public void save(FileUpload fileUpload) {

		System.out.println("Control came till here");
		// sessionFactory.getCurrentSession().save(fileUpload);

		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(fileUpload);
		session.getTransaction().commit();
		session.close();
		System.out.println("Control Passed through this");
	}

}
