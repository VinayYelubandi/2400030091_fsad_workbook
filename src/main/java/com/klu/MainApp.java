package com.klu;

import com.klu.config.AppConfig;
import com.klu.model.Student;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp {
    public static void main(String[] args) {
        System.out.println("================= XML Configuration =================");
        ApplicationContext xmlContext = new ClassPathXmlApplicationContext("applicationContext.xml");

        System.out.println("--- Constructor Injection ---");
        Student xmlStudent1 = (Student) xmlContext.getBean("studentConstructor");
        System.out.println(xmlStudent1);

        System.out.println("--- Setter Injection ---");
        Student xmlStudent2 = (Student) xmlContext.getBean("studentSetter");
        System.out.println(xmlStudent2);

        System.out.println("\n============== Annotation Configuration ==============");
        ApplicationContext annotationContext = new AnnotationConfigApplicationContext(AppConfig.class);

        System.out.println("--- Constructor Injection ---");
        Student annStudent1 = (Student) annotationContext.getBean("studentConstructorAnnotation");
        System.out.println(annStudent1);

        System.out.println("--- Setter Injection ---");
        Student annStudent2 = (Student) annotationContext.getBean("studentSetterAnnotation");
        System.out.println(annStudent2);
    }
}
