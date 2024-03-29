package cn.codercheng.repository;

import cn.codercheng.bean.Resume;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

;

public interface ResumeRepository extends MongoRepository<Resume,String> {
    List<Resume>  findByNameEquals(String name);
    List<Resume>  findByNameAndExpectSalary(String name,double expectSalary);
}
