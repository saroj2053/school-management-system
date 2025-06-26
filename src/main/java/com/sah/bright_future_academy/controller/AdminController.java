package com.sah.bright_future_academy.controller;

import com.sah.bright_future_academy.model.BrightFutureClass;
import com.sah.bright_future_academy.model.Courses;
import com.sah.bright_future_academy.model.Person;
import com.sah.bright_future_academy.repo.BrightFutureClassRepository;
import com.sah.bright_future_academy.repo.CoursesRepository;
import com.sah.bright_future_academy.repo.PersonRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private BrightFutureClassRepository brightFutureClassRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private CoursesRepository coursesRepository;

    @RequestMapping("/displayClasses")
    public ModelAndView displayClasses(Model model) {
        List<BrightFutureClass> brightFutureClasses = brightFutureClassRepository.findAll();
        ModelAndView modelAndView = new ModelAndView("classes.html");
        modelAndView.addObject("brightFutureClasses", brightFutureClasses);
        modelAndView.addObject("brightFutureClass", new BrightFutureClass());
        return modelAndView;
    }

    @PostMapping("/addNewClass")
    public ModelAndView addNewClass(Model model, @ModelAttribute("brightFutureClass") BrightFutureClass brightFutureClass) {
        brightFutureClassRepository.save(brightFutureClass);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayClasses");
        return modelAndView;
    }

    @RequestMapping("/deleteClass")
    public ModelAndView deleteClass(Model model, @RequestParam int id) {
        Optional<BrightFutureClass> eazyClass = brightFutureClassRepository.findById(id);
        for(Person person : eazyClass.get().getPersons()){
            person.setBrightFutureClass(null);
            personRepository.save(person);
        }
        brightFutureClassRepository.deleteById(id);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayClasses");
        return modelAndView;
    }

    @GetMapping("/displayStudents")
    public ModelAndView displayStudents(Model model, @RequestParam int classId, HttpSession session,
                                        @RequestParam(value = "error", required = false) String error) {
        String errorMessage = null;
        ModelAndView modelAndView = new ModelAndView("students.html");
        Optional<BrightFutureClass> brightFutureClass = brightFutureClassRepository.findById(classId);
        modelAndView.addObject("brightFutureClass", brightFutureClass.get());
        modelAndView.addObject("person", new Person());
        session.setAttribute("classId", brightFutureClass.get().getClassId());

        if(error != null){
            errorMessage = "Invalid Email entered!!";
            modelAndView.addObject("errorMessage", errorMessage);
        }
        return modelAndView;
    }

    @PostMapping("/addStudent")
    public ModelAndView addStudent(Model model, @ModelAttribute("person") Person person, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        Integer classId = (Integer) session.getAttribute("classId");
        BrightFutureClass brightFutureClass = brightFutureClassRepository.findById(classId).get();
        Person personEntity = personRepository.readByEmail(person.getEmail());
        if(personEntity==null || !(personEntity.getPersonId()>0)){
            modelAndView.setViewName("redirect:/admin/displayStudents?classId="+brightFutureClass.getClassId()
                    +"&error=true");
            return modelAndView;
        }
        personEntity.setBrightFutureClass(brightFutureClass);
        personRepository.save(personEntity);
        brightFutureClass.getPersons().add(personEntity);
        brightFutureClassRepository.save(brightFutureClass);
        modelAndView.setViewName("redirect:/admin/displayStudents?classId="+brightFutureClass.getClassId());
        return modelAndView;
    }

    @GetMapping("/deleteStudent")
    public ModelAndView deleteStudent(Model model, @RequestParam int personId, HttpSession session) {
        Integer classId = (Integer) session.getAttribute("classId");
        BrightFutureClass brightFutureClass = brightFutureClassRepository.findById(classId).get();

        Optional<Person> person = personRepository.findById(personId);

        if (person.isPresent()) {
            Person personEntity = person.get();
            personEntity.setBrightFutureClass(null);
            personRepository.save(personEntity);
        }

        BrightFutureClass brightFutureClassSaved = brightFutureClassRepository.save(brightFutureClass);
        session.setAttribute("classId",brightFutureClassSaved.getClassId());
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayStudents?classId="+brightFutureClass.getClassId());
        return modelAndView;
    }

    @GetMapping("/displayCourses")
    public ModelAndView displayCourses(Model model) {

//        STATIC SORTING
//        List<Courses> courses = coursesRepository.findAll();
//        List<Courses> courses = coursesRepository.findByOrderByNameDesc();

//        DYNAMIC SORTING
        List<Courses> courses = coursesRepository.findAll(Sort.by("name").ascending());

        ModelAndView modelAndView = new ModelAndView("courses.html");
        modelAndView.addObject("courses", courses);
        model.addAttribute("course", new Courses());
        return modelAndView;
    }

    @PostMapping("/addNewCourse")
    public ModelAndView addNewCourse(Model model, @ModelAttribute("course") Courses course) {
        ModelAndView modelAndView = new ModelAndView();
        coursesRepository.save(course);
        modelAndView.setViewName("redirect:/admin/displayCourses");
        return modelAndView;
    }

    @RequestMapping("/viewStudents")
    public ModelAndView viewStudents(Model model, @RequestParam int id, HttpSession session, @RequestParam(value = "error", required=false) String error) {
        String errorMessage = null;
        ModelAndView modelAndView = new ModelAndView("course_students.html");
        Optional<Courses> courses = coursesRepository.findById(id);
        if(courses.isPresent()){
            modelAndView.addObject("courses", courses.get());
            modelAndView.addObject("person", new Person());
            session.setAttribute("courseId", courses.get().getCourseId());

        }

        if(error != null){
            errorMessage = "Invalid Email entered!!";
            modelAndView.addObject("errorMessage", errorMessage);
        }
        return modelAndView;
    }

    @PostMapping("/addStudentToCourse")
    public ModelAndView addStudentToCourse(Model model, @ModelAttribute("person") Person person, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        Integer courseId = (Integer) session.getAttribute("courseId");
        Courses course = coursesRepository.findById(courseId).get();

        Person personEntity = personRepository.readByEmail(person.getEmail());

        if(personEntity==null || !(personEntity.getPersonId()>0)){
            modelAndView.setViewName("redirect:/admin/viewStudents?id="+course.getCourseId()+"&error=true");
            return modelAndView;
        }

        personEntity.getCourses().add(course);
        course.getPersons().add(personEntity);
        personRepository.save(personEntity);
        session.setAttribute("courseId", course.getCourseId());

        modelAndView.setViewName("redirect:/admin/viewStudents?id="+course.getCourseId());

        return modelAndView;
    }

    @GetMapping("/deleteStudentFromCourse")
    public ModelAndView deleteStudentFromCourse(Model model, @RequestParam int personId, HttpSession session) {
        Integer courseId = (Integer) session.getAttribute("courseId");
        Courses course  = coursesRepository.findById(courseId).get();

        Optional<Person> person = personRepository.findById(personId);

        if (person.isPresent()) {
            Person personEntity = person.get();
            personEntity.getCourses().remove(course);
            personRepository.save(personEntity);
        }
        session.setAttribute("courseId",course.getCourseId());
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/viewStudents?id="+course.getCourseId());
        return modelAndView;
    }
}



