package Entity;

public class Employee {
    private String id;
    private String name;
    private String position;
    private int baseSalary;
    public Employee(String id, String name, String position, int baseSalary) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.baseSalary = baseSalary;
    }
    public double calculateIncome() {
        baseSalary *= getSalaryMultiplier();
        return baseSalary;
    }

    public double calculateTax() {
        double income = calculateIncome();
        if (income < 9000000) {
            return 0;
        } else if (income <= 15000000) {
            return (income - 9000000) * 0.1;
        } else {
            return (income - 15000000) * 0.12 + 600000;
        }
    }
    public double getSalaryMultiplier() {
        return 1.0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(int baseSalary) {
        this.baseSalary = baseSalary;
    }
    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", baseSalary=" + baseSalary +
                '}';
    }
    public String toCSV() {
        return String.join(",", id, name, position, String.valueOf(baseSalary));
    }
}

