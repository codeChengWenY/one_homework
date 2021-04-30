package com.lagou.edu.remote;

import com.lagou.edu.dto.CourseDto;
import edu.response.ResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "lagou-edu-course",path = "/course")
public interface CourseRemoteService {

    @GetMapping("/getAllCourses")
    List<CourseDto> getAllCourses();

    @PostMapping("/saveOrUpdate")
    ResponseDTO saveOrUpdateCourse(@RequestBody CourseDto spaceDTO);

    @PostMapping("/up")
    ResponseDTO upCourse(@RequestBody CourseDto courseDto);

    @PostMapping("/down")
    ResponseDTO downCourse(@RequestBody CourseDto courseDto);
}
