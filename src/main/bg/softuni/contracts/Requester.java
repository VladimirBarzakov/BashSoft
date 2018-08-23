package main.bg.softuni.contracts;

import main.bg.softuni.data_structures.SimpleSortedList;

import java.util.Comparator;

public interface Requester {
    void getStudentMarkInCourse(String courseName, String studentName);
    void getStudentsByCourse(String course);
    SimpleSortedList<Course> getAllCoursesSorted(Comparator<Course> cmp);
    SimpleSortedList<Student> getAllStudentsSorted(Comparator<Student> cmp);
}
