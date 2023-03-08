package dao;

import database.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pojo.Account;

import java.util.List;

public class AccountDAO implements Repository<Account, String>{
    public static AccountDAO getInstance() {
        return new AccountDAO();
    }

    @Override
    public boolean add(Account item) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            if (AccountDAO.getInstance().get(item.getUsername()) != null) {
                return false;
            }

            transaction = session.beginTransaction();
            session.save(item);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return true;
    }

    @Override
    public Account get(String id) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Account account = null;
        try {
            account = session.find(Account.class, id);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            session.close();
        }
        return account;
    }

    @Override
    public List<Account> getAll() {
        Session session = HibernateUtils.getSessionFactory().openSession();
        List<Account> accounts = null;
        try {
            accounts = session.createQuery("From Account").list();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            session.close();
        }
        return accounts;
    }

    @Override
    public boolean update(Account item) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        try {
            Account account = session.find(Account.class, item.getUsername());
            session.getTransaction().begin();
            account.setPassword(item.getPassword());
            session.getTransaction().commit();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            session.close();
        }
        return true;
    }

    @Override
    public boolean remove(String id) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        try {
            Account account = session.find(Account.class, id);
            session.getTransaction().begin();
            session.remove(account);
            session.getTransaction().commit();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            session.close();
        }
        return true;
    }
}
