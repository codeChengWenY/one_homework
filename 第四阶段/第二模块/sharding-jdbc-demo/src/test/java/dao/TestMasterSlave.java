package dao;


import cn.codercheng.RunBootApplication;
import cn.codercheng.entity.COrder;
import cn.codercheng.repository.COrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RunBootApplication.class)
public class TestMasterSlave {

    @Resource
    private COrderRepository cOrderRepository;

    @Test
    @Repeat(10)
    public void testAdd() {
        Random random = new Random();
        int userId = random.nextInt(10);
        COrder order = new COrder();
        order.setDel(false);
        order.setCompanyId(1);
        order.setPositionId(3242342);
        order.setUserId(userId);
        order.setPublishUserId(1111);
        order.setResumeType(1);
        order.setStatus("AUTO");
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());
        cOrderRepository.save(order);
    }

    @Test
    public void testFind() {
        List<COrder> list = cOrderRepository.findAll();
        list.forEach(cOrder -> {
            System.out.println(cOrder.getId() + " " + cOrder.getUserId() + " " + cOrder.getCompanyId());
        });
    }

}
