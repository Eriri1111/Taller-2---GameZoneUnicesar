package model;

/**
 * Represents a seller (employee) of GameZone Unicesar. In addition to the
 * common attributes inherited from {@link Person}, a seller has an employee
 * code and an assigned work shift.
 */
public class Seller extends Person {

    private String employeeCode;
    private String shift;

    /**
     * Creates a new seller.
     *
     * @param id           unique identification of the seller
     * @param name         full name of the seller
     * @param phone        contact phone number
     * @param employeeCode internal employee code
     * @param shift        assigned work shift (e.g. morning, afternoon, night)
     */
    public Seller(String id, String name, String phone, String employeeCode, String shift) {
        super(id, name, phone);
        this.employeeCode = employeeCode;
        this.shift = shift;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    @Override
    public String getRoleDescription() {
        return "Seller [employeeCode=" + employeeCode + ", shift=" + shift + "]";
    }
}
