package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a client of GameZone Unicesar. In addition to the common
 * attributes inherited from {@link Person}, a client has an email address
 * and a history of the sales that have been made to them.
 */
public class Client extends Person {

    private String email;
    private List<String> purchaseHistory;

    /**
     * Creates a new client.
     *
     * @param id    unique identification of the client
     * @param name  full name of the client
     * @param phone contact phone number
     * @param email email address of the client
     */
    public Client(String id, String name, String phone, String email) {
        super(id, name, phone);
        this.email = email;
        this.purchaseHistory = new ArrayList<>();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getPurchaseHistory() {
        return purchaseHistory;
    }

    public void setPurchaseHistory(List<String> purchaseHistory) {
        this.purchaseHistory = purchaseHistory;
    }

    /**
     * Registers a new sale identifier in this client's purchase history.
     *
     * @param saleId identifier of the sale to register
     */
    public void addPurchase(String saleId) {
        this.purchaseHistory.add(saleId);
    }

    @Override
    public String getRoleDescription() {
        return "Client [email=" + email + ", totalPurchases=" + purchaseHistory.size() + "]";
    }
}
