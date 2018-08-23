package main.bg.softuni.models;

import main.bg.softuni.contracts.Course;
import main.bg.softuni.contracts.Student;
import main.bg.softuni.exceptions.DuplicateEntryInStructureException;
import main.bg.softuni.exceptions.InvalidStringException;
import main.bg.softuni.exceptions.KeyNotFoundException;
import main.bg.softuni.staticData.ExceptionMessages;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class SoftUniStudent implements Student {
    private String userName;
    private Map<String, Course> enrolledCourses;
    private Map<String, Double> marksByCourseName;

    public SoftUniStudent(String userName) {
        this.setUserName(userName);
        this.enrolledCourses = new LinkedHashMap<>();
        this.marksByCourseName = new LinkedHashMap<>();
    }

    @Override
    public String getUserName() {
        return this.userName;
    }

    @Override
    public Map<String, Course> getEnrolledCourses() {
        return Collections.unmodifiableMap(this.enrolledCourses);
    }

    @Override
    public Map<String, Double> getMarksByCourseName() {
        return Collections.unmodifiableMap(this.marksByCourseName);
    }

    private void setUserName(String userName) {
        if (userName==null||userName.equals("")){
            throw new InvalidStringException();
        }
        this.userName = userName;
    }

    @Override
    public void enrollInCourse(Course course){
        if (this.enrolledCourses.containsKey(course.getName())){
            throw new DuplicateEntryInStructureException(this.userName, course.getName());
        }
        this.enrolledCourses.put(course.getName(), course);
    }

    @Override
    public void setMarksInCourse(String courseName, int... scores){
        if (!this.enrolledCourses.containsKey(courseName)){
            throw new KeyNotFoundException();
        }
        if (scores.length> SoftUniCourse.NUMBER_OF_TASKS_ON_EXAM){
            throw new IllegalArgumentException(ExceptionMessages.INVALID_NUMBER_OF_SCORES);
        }

        double mark = calculateMark(scores);
        this.marksByCourseName.put(courseName, mark);
    }

    private double calculateMark(int[] scores) {
        double percentageOfSolvedExam = Arrays.stream(scores).sum()/
                (double)(SoftUniCourse.NUMBER_OF_TASKS_ON_EXAM* SoftUniCourse.MAX_SCORE_ON_EXAM_TASK);
        double mark = percentageOfSolvedExam*4+2;
        return mark;
    }

    @Override
    public int compareTo(Student other) {
        return this.getUserName().compareTo(other.getUserName());
    }

    @Override
    public String toString() {
        return this.getUserName();
    }
}
