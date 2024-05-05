/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cw.dao;

import com.cw.model.Billing;
import com.cw.model.Patient;

/**
 *
 * @author daves
 */
import java.util.ArrayList;
import java.util.List;

public class BillingDAO {

    private static List<Billing> billings = new ArrayList<>();

    static {
        billings.add(new Billing(1,
                new Patient(6, "No History", "Excellent", 14, "Robert", "robert@example.com", "7/67 Iwan St"),
                 7000, false));
        billings.add(new Billing(2,
                new Patient(7, "No History", "Good", 15, "Jane", "Jane@example.com", "123 Main St"),
                 12000, false));
        billings.add(new Billing(3,
                new Patient(8, "Allergic", "Fair", 16, "Don", "Don@example.com", "56 Lake St"),
                 13000, true));
    }

    public List<Billing> getAllBillings() {
        return billings;
    }

    public Billing getBillingById(int id) {
        for (Billing billing : billings) {
            if (billing.getId() == id) {
                return billing;
            }
        }
        return null;
    }

    public void addBilling(Billing billing) {
        int nextId = getNextUserId();
        billing.setId(nextId);
        billings.add(billing);
    }

    public void updateBilling(Billing updatedBilling) {
        for (int i = 0; i < billings.size(); i++) {
            Billing billing = billings.get(i);
            if (billing.getId() == updatedBilling.getId()) {
                billings.set(i, updatedBilling);              
                break;
            }
        }
    }

    public void deleteBilling(int id) {
        billings.removeIf(billing -> billing.getId() == id);
    }

    private int getNextUserId() {
        int maxUserId = Integer.MIN_VALUE;
        for (Billing billing : billings) {
            if (billing.getId() > maxUserId) {
                maxUserId = billing.getId();
            }
        }
        return maxUserId + 1;
    }
}
