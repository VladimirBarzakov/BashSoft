package main.bg.softuni.contracts;

import java.util.Map;

public interface DataSorter {
    void printSortedStudents(
            Map<String, Double> studentsWithMarks,
            String comparisonType,
            int numberOfStudents);
}
