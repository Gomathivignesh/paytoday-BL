package com.example.paytoday.api;


import com.example.paytoday.dao.UserDAO;
import com.example.paytoday.data.Student;
import com.example.paytoday.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

@org.springframework.web.bind.annotation.RestController




@RequestMapping("/api")
public class APIController {



    @Autowired
    UserDAO userDAO;

    public List<Student> getDetails = Arrays.asList(new Student(1,"GV"), new Student(2, "Test"));


    @GetMapping(path = "/getData/{studentId}")

    public Student getStudentDetails(@PathVariable("studentId") Integer studentId){






        return getDetails.stream()
                .filter(student -> student.getId().equals(studentId))
                .findFirst()
                .orElseThrow(()-> new IllegalStateException("no Data found"));

    }


    @GetMapping(path = "/test")
    public String getDetails(){
        return "test";
    }
}
