package com.lagou.test;

import com.lagou.dao.IUserDao;
import com.lagou.io.Resources;
import com.lagou.pojo.User;
import com.lagou.sqlSession.SqlSession;
import com.lagou.sqlSession.SqlSessionFactory;
import com.lagou.sqlSession.SqlSessionFactoryBuilder;
import org.dom4j.DocumentException;
import org.junit.Before;
import org.junit.Test;

import java.beans.PropertyVetoException;
import java.io.InputStream;

public class IPersistenceTest {


    public SqlSession sqlSession;

    public IUserDao userDao;


    @Before
    public void before() throws PropertyVetoException, DocumentException {
        InputStream resourceAsSteam = Resources.getResourceAsSteam("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsSteam);
        sqlSession = sqlSessionFactory.openSession();
        userDao = sqlSession.getMapper(IUserDao.class);
    }

    @Test
    public void test() throws Exception {


        //调用
//        User user = new User();
//        user.setId(1);
//        user.setUsername("张三");
      /*  User user2 = sqlSession.selectOne("user.selectOne", user);

        System.out.println(user2);*/

       /* List<User> users = sqlSession.selectList("user.selectList");
        for (User user1 : users) {
            System.out.println(user1);
        }*/

//
//        List<User> all = userDao.findAll();
//        for (User user1 : all) {
//            System.out.println(user1);
//        }


    }

    // 新增
    @Test
    public void saveUserTest() {
        // 新增
        User userAdd = new User();
        userAdd.setId(6);
        userAdd.setUsername("addjack");
        userDao.save(userAdd);

    }


    @Test
    public void UpateUserTest() {
        // 编辑
        User userEdit = new User();
        userEdit.setId(6);
        userEdit.setUsername("editjack");
        userDao.update(userEdit);
    }

    @Test
    public void delUserTest() {
        // 删除
        User userDel = new User();
        userDel.setId(6);
        userDao.delete(userDel);
    }
}
