package Entity;
public class Director extends Employee {
    public Director(String id, String name, String position, int baseSalary) {
        super(id, name, position, baseSalary);
    }

    @Override
    public double getSalaryMultiplier() {
        return 1.5;
    }
}
