package cn.codercheng.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import cn.codercheng.mapper.StudentMapper;
import cn.codercheng.pojo.Student;
import cn.codercheng.service.IStudentService;

/**
 * @ClassName StudentService
 * @Description:
 * @Author CoderCheng
 * @Date 2021-03-17 14:11
 * @Version V1.0
 **/
@Service
public class StudentService extends ServiceImpl<StudentMapper, Student> implements IStudentService {

}
