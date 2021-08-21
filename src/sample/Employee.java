package sample;

public class Employee extends Person{
    private String password;
    private String id;
    private String lastName;
    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public Employee search(Employee employee[] , String id){ // this method searches for the employees we have
        Employee employee1 = null;
        for (int i = 0; i < employee.length; i++) {
            if (id.equals(employee[i].getId())){
                employee1 = employee[i];
            }
        }

        return employee1;
    }
}
