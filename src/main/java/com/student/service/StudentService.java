package com.student.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.student.entity.Student;
import com.student.entity.User;
import com.student.repository.StudentRepository;

import jakarta.transaction.Transactional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public void saveStudentData(Student student) {
        studentRepository.save(student);
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Student with id " + id + " not found"));
    }

    public void deleteById(Long id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
        } else {
            throw new RuntimeException("Student with id " + id + " not found");
        }
    }

    public Student updateStudentById(Long id, Student updateStudentDetails) {
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Student with id " + id + " not found"));

        existingStudent.setFirstName(updateStudentDetails.getFirstName());
        existingStudent.setEmail(updateStudentDetails.getEmail());
        existingStudent.setAge(updateStudentDetails.getAge());

        return studentRepository.save(existingStudent);
    }

    public Page<Student> getAll(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }
    public List<Student> search(String keyword) {
        return studentRepository
            .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(keyword, keyword);
    }
    @Transactional
    public List<Student> saveAllStudents(List<Student> students) {
        return studentRepository.saveAll(students);
    }

}
