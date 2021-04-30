package com.lagou.edu.course.remote;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.lagou.edu.course.config.WebSocketConfig;
import com.lagou.edu.course.entity.Course;
import com.lagou.edu.course.service.ICourseService;
import com.lagou.edu.dto.CourseDto;
import com.lagou.edu.remote.CourseRemoteService;
import edu.response.ResponseDTO;
import edu.util.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseRemoteServiceImpl implements CourseRemoteService {

    @Autowired
    ICourseService courseService;



    @Override
    public List<CourseDto> getAllCourses() {
        List<Course> list = courseService.list();
        return ConvertUtils.convertList(list, CourseDto.class);
    }

    @Override
    public ResponseDTO saveOrUpdateCourse(CourseDto spaceDTO) {
        Course entity = ConvertUtils.convert(spaceDTO, Course.class);

        if (entity.getId() == null) {
            entity.setCreateTime(LocalDateTime.now());
            entity.setUpdateTime(LocalDateTime.now());
            entity.setIsDel(0);
        } else {
            entity.setUpdateTime(LocalDateTime.now());
        }

        ResponseDTO responseDTO = null;
        try {
            courseService.saveOrUpdate(entity);
            responseDTO = ResponseDTO.success();
        } catch (Exception e) {
            responseDTO = ResponseDTO.ofError(e.getMessage());
            e.printStackTrace();
        }
        return responseDTO;
    }

    @Override
    public ResponseDTO upCourse(CourseDto courseDto) {

        ResponseDTO responseDTO = null;
        try {
            courseService.update(new UpdateWrapper<Course>().set("status", 2).eq("id", courseDto.getId()));


            System.out.println("开始推送了..................");

            Course course = courseService.getById(courseDto.getId());

            for (SocketIOClient client : WebSocketConfig.clients) {
                client.sendEvent("hello", course.getCourseName() + "课程新上线，欢迎试学。");
            }

            responseDTO = ResponseDTO.success();
        } catch (Exception e) {
            responseDTO = ResponseDTO.ofError(e.getMessage());
            e.printStackTrace();
        }
        return responseDTO;
    }

    @Override
    public ResponseDTO downCourse(CourseDto courseDto) {
        ResponseDTO responseDTO = null;
        try {
            courseService.update(new UpdateWrapper<Course>().set("status", 1).eq("id", courseDto.getId()));
            responseDTO = ResponseDTO.success();
        } catch (Exception e) {
            responseDTO = ResponseDTO.ofError(e.getMessage());
            e.printStackTrace();
        }
        return responseDTO;
    }
}
