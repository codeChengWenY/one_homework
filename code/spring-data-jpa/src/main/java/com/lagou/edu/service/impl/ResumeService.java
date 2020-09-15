package com.lagou.edu.service.impl;

import com.lagou.edu.dao.ResumeDao;
import com.lagou.edu.pojo.Resume;
import com.lagou.edu.service.IResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @ClassName ResumeService
 * @Description:
 * @Author CoderCheng
 * @Date 2020-09-08 09:38
 * @Version V1.0
 **/
@Service
public class ResumeService implements IResumeService {


    @Autowired
    private ResumeDao resumeDao;

    @Override
    public Resume findone(Long id) {
        Optional<Resume> resume = resumeDao.findById(id);
        return resume.get();
    }

    @Override
    public List<Resume> findAll() {
        return resumeDao.findAll();
    }

    @Override
    public void upadate(Resume resume) {
        resumeDao.save(resume);
    }

    @Override
    public void del(Resume resume) {
        resumeDao.delete(resume);
    }
}
