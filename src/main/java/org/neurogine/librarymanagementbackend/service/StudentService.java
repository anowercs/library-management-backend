package org.neurogine.librarymanagementbackend.service;


import lombok.RequiredArgsConstructor;
import org.neurogine.librarymanagementbackend.entity.Student;
import org.neurogine.librarymanagementbackend.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;

    public Student add(Student student){
        return studentRepository.save(student);
    }

    public Student update(Student student){
        return studentRepository.save(student);
    }

    public void delete(Integer id){
        studentRepository.deleteById(id);
    }

    public List<Student> findAll(){
        return studentRepository.findAll();
    }

    public List<Student> findByKeyword(String keyword){
        return studentRepository.findByNameContaining(keyword);
    }

}
