package com.klu.config;

import com.klu.model.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    // Constructor Injection
    @Bean(name = "studentConstructorAnnotation")
    public Student studentConstructor() {
        return new Student(201, "Alice Williams", "Electrical Engineering", 3);
    }

    // Setter Injection
    @Bean(name = "studentSetterAnnotation")
    public Student studentSetter() {
        Student student = new Student();
        student.setStudentId(202);
        student.setName("Bob Brown");
        student.setCourse("Mechanical Engineering");
        student.setYear(4);
        return student;
    }
}
