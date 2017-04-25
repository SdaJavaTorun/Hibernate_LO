package com.sdajava.hibernate;

import com.sdajava.hibernate.entity.BooksEntity;
import org.hibernate.*;
import org.hibernate.query.Query;
import org.hibernate.cfg.Configuration;

import javax.persistence.metamodel.EntityType;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by lukas on 25.04.2017.
 */
public class Main {
    private static final SessionFactory ourSessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();

            ourSessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();
    }

    public static void main(final String[] args) throws Exception {
        Session session = ourSessionFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            List<BooksEntity> books =
                session.createQuery("FROM " + BooksEntity.class.getName()).list();

            for (BooksEntity book: books){
                System.out.println(" Autor: " + book.getAuthor());
                System.out.println("  Tytul: " + book.getTitle());
            }

            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }
}
