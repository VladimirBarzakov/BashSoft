package main.bg.softuni.contracts;

import java.util.Map;

public interface DataFilter {
    void printFilteredStudents(
            Map<String, Double> studentsWithMarks,
            String filterType,
            Integer numberOfStudents);
}
