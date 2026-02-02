import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Employee {
    int id;
    String name;
    String dept;
    int salary;
    List<String> skills;

    Employee(int id, String name, String dept, int salary, List<String> skills) {
        this.id = id;
        this.name = name;
        this.dept = dept;
        this.salary = salary;
        this.skills = skills;
    }

    public int getSalary() { return salary; }
    public String getDept() { return dept; }
    public String getName() { return name; }
    public List<String> getSkills() { return skills; }

    @Override
    public String toString() {
        return name + " | " + dept + " | " + salary;
    }
}

class DeptStats {
    double totalSalary;
    int count;

    public DeptStats(double totalSalary, int count) {
        this.totalSalary = totalSalary;
        this.count = count;
    }

    public static DeptStats merge(DeptStats s1, DeptStats s2) {
        return new DeptStats(s1.totalSalary + s2.totalSalary, s1.count + s2.count);
    }
}

public class pepassignment4 {

    public static void main(String[] args) {

        List<Employee> employees = List.of(
                new Employee(1, "Aman", "IT", 70000, List.of("Java", "Spring")),
                new Employee(2, "Ravi", "HR", 40000, List.of("Recruitment")),
                new Employee(3, "Neha", "IT", 90000, List.of("Java", "Docker")),
                new Employee(4, "Pooja", "Finance", 60000, List.of("Excel", "Accounts")),
                new Employee(5, "Aman", "IT", 70000, List.of("Java", "Spring"))
        );

        System.out.println("Task 1: Filter (Salary > 60k)");
        List<Employee> highEarners = employees.stream()
                .filter(e -> e.getSalary() > 60000)
                .collect(Collectors.toList());
        highEarners.forEach(System.out::println);

        System.out.println("Task 2: Map (Extract Names)");
        List<String> names = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.toList());
        System.out.println(names);

        System.out.println("Task 3: Distinct (Unique Names)");
        List<String> uniqueNames = employees.stream()
                .map(Employee::getName)
                .distinct()
                .collect(Collectors.toList());
        System.out.println(uniqueNames);

        System.out.println("Task 4: Sorting (Salary Descending)");
        List<Employee> sortedBySalary = employees.stream()
                .sorted(Comparator.comparingInt(Employee::getSalary).reversed())
                .collect(Collectors.toList());
        sortedBySalary.forEach(System.out::println);

        System.out.println("Task 5: Skip + Limit (2nd & 3rd Highest)");
        List<Employee> secondAndThird = employees.stream()
                .sorted(Comparator.comparingInt(Employee::getSalary).reversed())
                .skip(1)
                .limit(2)
                .collect(Collectors.toList());
        secondAndThird.forEach(System.out::println);

        System.out.println("Task 6: flatMap (All Unique Skills)");
        Set<String> allSkills = employees.stream()
                .flatMap(e -> e.getSkills().stream())
                .collect(Collectors.toSet());
        System.out.println(allSkills);

        System.out.println("Task 7: Reduce (Total Salary)");
        int totalSalary = employees.stream()
                .map(Employee::getSalary)
                .reduce(0, Integer::sum);
        System.out.println("Total Salary: " + totalSalary);

        System.out.println("Task 8: Average Salary (using map, reduce, count)");
        double sum = employees.stream()
                .map(Employee::getSalary)
                .reduce(0, Integer::sum);
        long count = employees.stream().count();
        System.out.println("Average Salary: " + (sum / count));

        System.out.println("Task 9: groupingBy (Group by Dept)");
        Map<String, List<Employee>> byDept = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDept));
        byDept.forEach((dept, empList) -> System.out.println(dept + ": " + empList));

        System.out.println("Task 10: groupingBy + Collector (Highest Paid per Dept)");
        Map<String, Employee> topEarnerPerDept = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDept,
                        Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparingInt(Employee::getSalary)),
                                Optional::get
                        )
                ));
        topEarnerPerDept.forEach((dept, emp) -> System.out.println(dept + " Top: " + emp));

        System.out.println("Task 11: Top Skilled High Earners");
        List<String> topSkills = employees.stream()
                .filter(e -> "IT".equals(e.getDept()))
                .filter(e -> e.getSalary() > 60000)
                .flatMap(e -> e.getSkills().stream())
                .distinct()
                .sorted()
                .limit(3)
                .collect(Collectors.toList());
        System.out.println("Top 3 IT Skills: " + topSkills);

        System.out.println("Task 12: Department Salary Report (Complex)");
        Map<String, Map<String, Double>> deptReport = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDept,
                        Collectors.collectingAndThen(
                                Collectors.reducing(
                                        new DeptStats(0, 0),
                                        e -> new DeptStats(e.getSalary(), 1),
                                        DeptStats::merge
                                ),
                                stats -> Map.of(
                                        "total", stats.totalSalary,
                                        "average", stats.count == 0 ? 0.0 : stats.totalSalary / stats.count,
                                        "count", (double) stats.count
                                )
                        )
                ));

        deptReport.forEach((dept, stats) -> System.out.println(dept + " = " + stats));
    }
}
