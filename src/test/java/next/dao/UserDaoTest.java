package next.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import core.jdbc.ConnectionManager;
import next.model.User;

public class UserDaoTest {
    private UserDao userDao;

    @Before
    public void setup() {
        userDao = new UserDao();
    }

    @Test
    public void connection() {
        Connection con = userDao.getConnection();
        assertNotNull(con);
    }

    @Test
    public void insert() throws Exception {
     userDao.insert(UserTest.TEST_USER);
    }

    @Test
    public void crud() throws Exception {
        User expected = new User("userId", "password", "name", "javajigi@email.com");
        UserDao userDao = new UserDao();
        userDao.insert(expected);
        User actual = userDao.findByUserId(expected.getUserId());
        assertEquals(expected, actual);

        expected.update(new User("userId", "password2", "name2", "sanjigi@email.com"));
        userDao.update(expected);
        actual = userDao.findByUserId(expected.getUserId());
        assertEquals(expected, actual);
    }

    @Test
    public void findAll() throws Exception {
        UserDao userDao = new UserDao();
        List<User> users = userDao.findAll();
        assertEquals(1, users.size());
    }
}