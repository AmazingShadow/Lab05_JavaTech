package test;

import dao.AccountDAO;
import database.HibernateUtils;
import org.hibernate.Session;
import pojo.Account;

public class Test {
    public static void main(String[] args) {
        System.out.println(AccountDAO.getInstance().add(new Account("Huy", "username1@gmail.com", "123456")));
    }
}
