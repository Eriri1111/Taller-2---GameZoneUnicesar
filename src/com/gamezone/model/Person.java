/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gamezone.model;

/**
 * Represents a person who interacts with the store.
 * This is the base class for Client and Seller.
 */

public abstract class Person {
    private String name;
    private String id;
    private String phone;
    
    
    /**
     * Creates a new Person with the given basic information.
     *
     * @param name  the full name of the person
     * @param id    the identification number of the person
     * @param phone the contact phone number
     */
    public Person(String name, String id, String phone) {
        this.name = name;
        this.id = id;
        this.phone = phone;
    }
    
    
     /**
     * Returns the name of the person.
     *
     * @return the person's name
     */
    public String getName() {
        return name;
    }

    /**
     * Updates the name of the person.
     *
     * @param name the new name to set
     */
    public void setName(String name) {
        this.name = name;
    }

   /**
    * Returns the id of the person.
    *
    * @return the person's id
    */
    public String getId() {
        return id;
    }

    /**
     * Updates the id of the person.
     *
      * @param id the new id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Returns the phone of the person.
     *
     * @return the person's phone
     */
    public String getPhone() {
        return phone;
    }

     /**
     * Updates the phone of the person.
     *
      * @param phone the new phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
     /**
     * Returns a full description of this person, including
     * the specific details of its concrete type (client or seller).
     *
     * @return a textual description of the person
     */
    public abstract String getDescription();
    
}

 

