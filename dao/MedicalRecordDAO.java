/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cw.dao;

import com.cw.model.MedicalRecord;
import com.cw.model.Patient;

/**
 *
 * @author daves
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MedicalRecordDAO {

    private static List<MedicalRecord> medicalRecords = new ArrayList<>();

    static {
        medicalRecords.add(new MedicalRecord(1,
                new Patient(3, "No History", "Excellent", 
                        9, "Robert", "robert@example.com", "7/67 Iwan St"),
                Arrays.asList("Common cold", "Influenza"),
                Arrays.asList("medication")));
        medicalRecords.add(new MedicalRecord(2,
                new Patient(2, "No Significent medical history", "Good", 
                        8, "Stanly", "stanly@example.com", "123 Main St"),
                Arrays.asList("none"),
                Arrays.asList("Rutine Checkup")));
    }

    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecords;
    }

    public MedicalRecord getMedicalRecordById(int id) {
        for (MedicalRecord medicalRecord : medicalRecords) {
            if (medicalRecord.getId() == id) {
                return medicalRecord;
            }
        }
        return null;
    }

    public void addMedicalRecord(MedicalRecord medicalRecord) {
        int nextId = getNextUserId();
        medicalRecord.setId(nextId);
        medicalRecords.add(medicalRecord);
    }

    public void updateMedicalRecord(MedicalRecord updatedMedicalRecord) {
        for (int i = 0; i < medicalRecords.size(); i++) {
            MedicalRecord medicalRecord = medicalRecords.get(i);
            if (medicalRecord.getId() == updatedMedicalRecord.getId()) {
                medicalRecords.set(i, updatedMedicalRecord);
                break;
            }
        }
    }

    public void deleteMedicalRecord(int id) {
        medicalRecords.removeIf(medicalRecord -> medicalRecord.getId() == id);
    }

    private int getNextUserId() {
        int maxUserId = Integer.MIN_VALUE;
        for (MedicalRecord medicalRecord : medicalRecords) {
            if (medicalRecord.getId() > maxUserId) {
                maxUserId = medicalRecord.getId();
            }
        }
        return maxUserId + 1;
    }
}
