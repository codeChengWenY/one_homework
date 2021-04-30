package com.lagou.edu.course.controller;

import com.lagou.edu.dto.CourseDto;
import com.lagou.edu.remote.CourseRemoteService;
import edu.response.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    CourseRemoteService courseRemoteService;

    @RequestMapping("/getAllCourses")
    public ResponseDTO getAllCourses() throws Exception{
        List<CourseDto> dtoList = courseRemoteService.getAllCourses();
        return ResponseDTO.success(dtoList);
    }

    @PostMapping("/saveOrUpdate")
    public ResponseDTO saveOrUpdateCourse(@RequestBody CourseDto spaceDTO) {
        ResponseDTO responseDTO = courseRemoteService.saveOrUpdateCourse(spaceDTO);
        return responseDTO;
    }

    @PostMapping("/up")
    public ResponseDTO upCourse(@RequestBody CourseDto spaceDTO) {
        ResponseDTO responseDTO = courseRemoteService.upCourse(spaceDTO);
        return responseDTO;
    }

    @PostMapping("/down")
    public ResponseDTO downCourse(@RequestBody CourseDto spaceDTO) {
        ResponseDTO responseDTO = courseRemoteService.downCourse(spaceDTO);
        return responseDTO;
    }
}
