package model;

/**
 * Abstract base class that represents any person interacting with the store.
 * Holds the attributes that are common to every kind of person (clients and
 * sellers), while leaving role-specific behavior to be defined by subclasses.
 * This class cannot be instantiated directly, since a "generic person"
 * without a defined role has no meaning in the business domain.
 */
public abstract class Person {

    private String id;
    private String name;
    private String phone;

    /**
     * Creates a new person with the attributes shared by every role.
     *
     * @param id    unique identification of the person
     * @param name  full name of the person
     * @param phone contact phone number
     */
    protected Person(String id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Returns a role-specific description of this person. Each subclass
     * must provide its own implementation, since the relevant information
     * depends on the specific role (client or seller).
     *
     * @return a human-readable description of the person's role
     */
    public abstract String getRoleDescription();
}
