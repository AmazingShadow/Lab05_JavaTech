package dao;

import database.HibernateUtils;
import org.hibernate.Session;
import pojo.Account;
import pojo.Product;
import java.util.List;

public class ProductDAO implements Repository<Product, Integer>{

    public static ProductDAO getInstance() {
        return new ProductDAO();
    }

    @Override
    public boolean add(Product item) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        try {
            if (ProductDAO.getInstance().get(item.getId()) != null) {
                return false;
            }
            session.save(item);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            session.close();
        }
        return true;
    }

    @Override
    public Product get(Integer id) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Product product = null;
        try {
            product = session.find(Product.class, id);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            session.close();
        }
        return product;
    }

    @Override
    public List<Product> getAll() {
        Session session = HibernateUtils.getSessionFactory().openSession();
        List<Product> products = null;
        try {
            products = session.createQuery("From Product").list();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            session.close();
        }
        return products;
    }

    @Override
    public boolean update(Product item) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        try {
            Product result = session.find(Product.class, item.getId());
            session.getTransaction().begin();
            result.setProductName(item.getProductName());
            result.setPrice(item.getPrice());
            session.merge(result);
            session.getTransaction().commit();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            session.close();
        }
        return true;
    }

    @Override
    public boolean remove(Integer id) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        try {
            Product product = session.find(Product.class, id);
            session.getTransaction().begin();
            session.remove(product);
            session.getTransaction().commit();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            session.close();
        }
        return true;
    }
}
