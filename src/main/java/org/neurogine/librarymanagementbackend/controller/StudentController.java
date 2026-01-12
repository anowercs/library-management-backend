package org.neurogine.librarymanagementbackend.controller;

import lombok.RequiredArgsConstructor;
import org.neurogine.librarymanagementbackend.entity.Student;
import org.neurogine.librarymanagementbackend.repository.StudentRepository;
import org.neurogine.librarymanagementbackend.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/students")
@CrossOrigin
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public List<Student> list(){
        return studentService.findAll();
    }

    @PostMapping
    public Student add(@RequestBody Student student){
        return studentService.add(student);
    }

    @PutMapping("/{id}")
    public Student update(@PathVariable Integer id, @RequestBody Student student){
        student.setId(id);
        return studentService.update(student);
    }
    @GetMapping("/{id}")
    public Student getById(@PathVariable Integer id) {
        return studentService.getById(id);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        studentService.delete(id);
    }


    @GetMapping("/search")
    public List<Student> search(@RequestParam String keyword){
        return studentService.findByKeyword(keyword);
    }

}
