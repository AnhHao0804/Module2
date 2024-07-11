package Entity;
public class Manager extends Employee {
    public Manager(String id, String name, String position, int baseSalary) {
        super(id, name, position, baseSalary);
    }

    @Override
    public double getSalaryMultiplier() {
        return 1.2;
    }
}
