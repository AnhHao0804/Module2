package Service;
import Entity.*;
import java.util.*;
import java.io.*;
public class EmployeeManager {
    private static List<Employee> employees = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static User accountManager;
    private static final String FILE_DATA = "src/DataCompany/data.csv";
    public static void main(String[] args) {
        loadEmployees();
        initializeAccountManager();
        login();
        while (true) {
            Menu.showMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case Constant.CREATE_EMPLOYEE:
                    addEmployees();
                    break;
                case Constant.DISPLAY_EMPLOYEE:
                    displayEmployees();
                    break;
                case Constant.FIND_EMPLOYEE:
                    findEmployeeById();
                    break;
                case Constant.DELETE_EMPLOYEE:
                    deleteEmployeeById();
                    break;
                case Constant.UPDATE_EMPLOYEE:
                    updateEmployeeById();
                    break;
                case Constant.FIND_EMPLOYEE_FOR_SALARY:
                    findEmployeesByIncome();
                    break;
                case Constant.STAFF_ARRANGEMENTS:
                    sortEmployeesByIncome();
                    break;
                case Constant.HIGHEST_SALARY:
                    displayTop5HighestIncome();
                    break;
                case Constant.TAX_EMPLOYEE:
                    displayTaxEmployees();
                    break;
                case Constant.EXIT:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    private static void initializeAccountManager() {
        accountManager = new User("admin", "admin");
    }
    private static void login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (accountManager.getUsername().equals(username) && accountManager.checkPassword(password)) {
            System.out.println("Login successful!");
        } else {
            System.out.println("Login failed! Exiting program.");
            System.exit(0);
        }
    }
    private static void addEmployees() {
        System.out.print("Nhập số lượng nhân viên: ");
        int count = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < count; i++) {
            System.out.print("Nhập mã nhân viên: ");
            String id = scanner.nextLine();
            System.out.print("Nhập họ tên: ");
            String name = scanner.nextLine();
            System.out.print("Nhập vị trí công việc: ");
            String position = scanner.nextLine();
            System.out.print("Nhập lương cơ bản: ");
            int baseSalary = scanner.nextInt();
            scanner.nextLine();
            Employee employee = createEmployee(id, name, position, baseSalary);
            employees.add(employee);
        }
        saveEmployees();
    }
    static Employee createEmployee(String id, String name, String position, int baseSalary) {
        switch (position.toLowerCase()) {
            case "manager":
                return new Manager(id, name, position, baseSalary);
            case "bu head":
                return new BUHead(id, name, position, baseSalary);
            case "director":
                return new Director(id, name, position, baseSalary);
            default:
                return new Employee(id, name, position, baseSalary);
        }
    }
    private static void displayEmployees() {
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }
    private static void findEmployeeById() {
        System.out.print("Nhập mã nhân viên cần tìm: ");
        String id = scanner.nextLine();
        for (Employee employee : employees) {
            if (employee.getId().equals(id)) {
                System.out.println(employee);
                return;
            }
        }
        System.out.println("Không tìm thấy nhân viên với mã " + id);
    }
    private static void deleteEmployeeById() {
        System.out.print("Nhập mã nhân viên cần xóa: ");
        String id = scanner.nextLine();
        Iterator<Employee> iterator = employees.iterator();
        while (iterator.hasNext()) {
            Employee employee = iterator.next();
            if (employee.getId().equalsIgnoreCase(id)) {
                iterator.remove();
                System.out.println("Nhân viên với mã " + id + " đã bị xóa.");
                return;
            }
        }
        System.out.println("Không tìm thấy nhân viên với mã " + id);
    }
    private static void updateEmployeeById() {
        System.out.print("Nhập mã nhân viên cần cập nhật: ");
        String id = scanner.nextLine();

        for (Employee employee : employees) {
            if (employee.getId().equalsIgnoreCase(id)) {
                System.out.print("Nhập họ tên mới: ");
                String name = scanner.nextLine();
                System.out.print("Nhập vị trí công việc mới: ");
                String position = scanner.nextLine();
                System.out.print("Nhập lương cơ bản mới: ");
                int baseSalary = scanner.nextInt();
                scanner.nextLine();
                employee.setName(name);
                employee.setPosition(position);
                employee.setBaseSalary(baseSalary);
                System.out.println("Cập nhật thông tin nhân viên thành công.");
                return;
            }
        }
        System.out.println("Không tìm thấy nhân viên với mã " + id);
    }

    private static void findEmployeesByIncome() {
        System.out.print("Nhập mức thu nhập tối thiểu: ");
        double minIncome = scanner.nextDouble();
        scanner.nextLine();
        for (Employee employee : employees) {
            if (employee.calculateIncome() >= minIncome) {
                System.out.println(employee);
            }
        }
    }

    private static void sortEmployeesByIncome() {
        employees.sort(Comparator.comparingDouble(Employee::calculateIncome));
        System.out.println("Danh sách nhân viên đã được sắp xếp theo thu nhập.");
    }
    private static void displayTop5HighestIncome() {
       for (Employee employee : employees) {
           if (employee.calculateIncome() >= employees.get(employees.size() - 1).calculateIncome()) {
               System.out.println(employee);
               if (employees.size() > 5) {
                   break;
               }
           }
       }
    }
    private static void displayTaxEmployees() {
        for (Employee employee : employees) {
            if (employee.calculateTax() > 0) {
                System.out.println(employee);
            }
        }
    }
    private static void saveEmployees() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_DATA, true))) {
            for (Employee employee : employees) {
                writer.write(employee.toCSV());
                writer.newLine();
            }
            writer.flush();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
    private static void loadEmployees() {
        File file = new File(FILE_DATA);
        if (!file.exists()) {
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_DATA))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String id = parts[0];
                String name = parts[1];
                String position = parts[2];
                int baseSalary = Integer.parseInt(parts[3]);
                Employee employee = createEmployee(id, name, position, baseSalary);
                employees.add(employee);
            }
            System.out.println("Dữ liệu nhân viên đã được tải thành công.");
        } catch (IOException e) {
            System.out.println("Lỗi khi tải dữ liệu nhân viên: " + e.getMessage());
        }
    }
}
