package com.lagou.edu.service;

import com.lagou.edu.pojo.Resume;

import java.util.List;

/**
 * @ClassName ResumeService
 * @Description:
 * @Author CoderCheng
 * @Date 2020-09-08 09:38
 * @Version V1.0
 **/
public interface IResumeService {


    List<Resume> findAll();

    Resume findone(Long id);


    void upadate(Resume resume);


    void del(Resume resume);


}
