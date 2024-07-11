package Entity;
public class BUHead extends Employee {
    public BUHead(String id, String name, String position, int baseSalary) {
        super(id, name, position, baseSalary);
    }

    @Override
    public double getSalaryMultiplier() {
        return 1.3;
    }
}
