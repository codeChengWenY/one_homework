package service;

/**
 * @ClassName EmailSendService
 * @Description:
 * @Author CoderCheng
 * @Date 2020-12-03 12:32
 * @Version V1.0
 **/
public interface EmailSendService {

    public boolean sendEmail(String email, String code);

}
