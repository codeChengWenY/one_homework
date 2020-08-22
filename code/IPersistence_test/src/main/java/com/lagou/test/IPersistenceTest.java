package com.lagou.test;

import com.lagou.dao.IUserDao;
import com.lagou.io.Resources;
import com.lagou.pojo.User;
import com.lagou.sqlSession.SqlSession;
import com.lagou.sqlSession.SqlSessionFactory;
import com.lagou.sqlSession.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;

public class IPersistenceTest {

    @Test
    public void test() throws Exception {
        InputStream resourceAsSteam = Resources.getResourceAsSteam("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsSteam);
        SqlSession sqlSession = sqlSessionFactory.openSession();

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

        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
//
//        List<User> all = userDao.findAll();
//        for (User user1 : all) {
//            System.out.println(user1);
//        }

        // 删除
        User user = new User();
        user.setId(1);
        Integer result = userDao.delete(user);
        System.out.println(result);

        // 新增
        User userAdd = new User();
        userAdd.setId(3);
        userAdd.setUsername("jack");
        userDao.save(userAdd);

        // 更新
        User userEdit = new User();
        userEdit.setId(3);
        userEdit.setUsername("editjack");
        userDao.update(userEdit);
    }


}
