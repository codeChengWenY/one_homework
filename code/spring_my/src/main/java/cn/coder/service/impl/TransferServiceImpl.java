package cn.coder.service.impl;


import cn.coder.anno.Autowired;
import cn.coder.anno.Service;
import cn.coder.dao.AccountDao;
import cn.coder.pojo.Account;
import cn.coder.service.TransferService;

/**
 * @author 应癫
 */
@Service()
//@Transactional
public class TransferServiceImpl implements TransferService {


    // 最佳状态

    @Autowired
    private AccountDao accountDao;


    @Override
    public void transfer(String fromCardNo, String toCardNo, int money) throws Exception {


            Account from = accountDao.queryAccountByCardNo(fromCardNo);
            Account to = accountDao.queryAccountByCardNo(toCardNo);

            from.setMoney(from.getMoney()-money);
            to.setMoney(to.getMoney()+money);

            accountDao.updateAccountByCardNo(to);
            int c = 1/0;
            accountDao.updateAccountByCardNo(from);


    }
}
