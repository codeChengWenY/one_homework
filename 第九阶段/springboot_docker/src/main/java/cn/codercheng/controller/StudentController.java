package cn.codercheng.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cn.codercheng.pojo.Student;
import cn.codercheng.service.IStudentService;

import java.util.List;

/**
 * @ClassName StudentController
 * @Description:
 * @Author CoderCheng
 * @Date 2021-03-17 14:05
 * @Version V1.0
 **/
@RestController
public class StudentController {


    @Autowired
    private IStudentService studentService;


    @RequestMapping("add")
    public ResponseEntity<String> add() {

        Student student = new Student();
        student.setName("李四");

        studentService.save(student);
        return ResponseEntity.ok("添加成功");
    }

    @RequestMapping("del")
    public ResponseEntity<String> del() {
        studentService.removeById(1);
        return ResponseEntity.ok("删除成功");
    }

    @RequestMapping("find")
    public ResponseEntity<List<Student>> find() {
        return ResponseEntity.ok(studentService.list());

    }


    @RequestMapping("update")
    public ResponseEntity<String> update() {
        Student student = new Student();
        student.setId(1);
        student.setName("张三");
        studentService.saveOrUpdate(student);
        return ResponseEntity.ok("更新成功");

    }

}
