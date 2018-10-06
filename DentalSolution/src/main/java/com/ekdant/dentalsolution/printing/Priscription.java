/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ekdant.dentalsolution.printing;

import com.ekdant.dentalsolution.dao.CheckUpDao;
import com.ekdant.dentalsolution.dao.ClinicDao;
import com.ekdant.dentalsolution.domain.CheckupBean;
import com.ekdant.dentalsolution.domain.ClinicBean;
import com.ekdant.dentalsolution.domain.PriscriptionBean;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 *
 * @author dinesh.mali
 */
public class Priscription extends Canvas implements Printable{
    int patientTreatmentId = 0;
    CheckUpDao checkUpDao;
    ClinicDao clinicDao;
    DateFormat displayDateFormat = new SimpleDateFormat("d MMM yyyy");
    
    public Priscription(int treatmentId){
        patientTreatmentId = treatmentId;
        checkUpDao = new CheckUpDao();
        clinicDao = new ClinicDao();
    }

    public int print(Graphics gx, PageFormat pf, int page) throws PrinterException {
        if (page > 0 || patientTreatmentId == 0) {
            return NO_SUCH_PAGE;
        } 
        Graphics2D g = (Graphics2D) gx; 
        g.translate(pf.getImageableX(), pf.getImageableY()); 
        
        CheckupBean checkup = checkUpDao.fetchCheckup(patientTreatmentId);
        printHeader(g);
        printPatientInfo(g, checkup);
        int priscriptionRowStart = 150;

        g.drawString("Rx - ", 80, priscriptionRowStart);
        g.drawLine(80, priscriptionRowStart + 5, 500, priscriptionRowStart + 5);
        int rowXpoint = 0;
        int row = 0;
        for (PriscriptionBean priscription : checkup.getPriscriptions()) {
            rowXpoint = priscriptionRowStart + 20 + (row++ * 40);
            String medicineTypeSelected = priscription.getMedicineType();
            String medicineName = priscription.getMedicineName();
            String medicineStrength = priscription.getMedicineStrength();
            String medicineFrequency = priscription.getFrequency();
            String medicineDuration = priscription.getDuration();
            g.drawString(medicineTypeSelected == null ? "" : "(" + medicineTypeSelected + ")", 100, rowXpoint);
            g.drawString(medicineName == null ? "N/A" : medicineName + " " + (medicineStrength == null ? "" : " - " + medicineStrength), 150, rowXpoint);
            g.drawString(medicineDuration == null ? "N/A" : medicineDuration + " Days", 400, rowXpoint);
            g.drawString(medicineFrequency == null ? "N/A" : "( " + medicineFrequency + ")", 180, rowXpoint + 15);
        }

        g.drawLine(80, rowXpoint + 20, 500, rowXpoint + 20);

        return PAGE_EXISTS; 
    }

    private void printHeader(Graphics g) {
        int pageCenter = 300;
        List<ClinicBean> clinics = clinicDao.fetchClinics();
        ClinicBean clinic = clinics.get(0);
        String clinicName = clinic.getName();
        String clinicAddress = clinic.getAddress();
        String cityPin = clinic.getCity();
        String contactNumber = clinic.getContact();

        g.drawString(clinicName, pageCenter - getLength(clinicName, g) / 2, 10);
        g.drawString(clinicAddress, pageCenter - getLength(clinicAddress, g) / 2, 25);
        g.drawString(cityPin, pageCenter - getLength(cityPin, g) / 2, 40);
        g.drawString(contactNumber, pageCenter - getLength(contactNumber, g) / 2, 55);
        g.drawLine(10, 60, 550, 60);
    }

    private int getLength(String str, Graphics g) {
        return (int) (g.getFontMetrics().getStringBounds(str, g).getWidth());
    }

    private void printPatientInfo(Graphics g, CheckupBean checkup) {
        String name = checkup.getPatient().getName();
        String casepapernumber = checkup.getPatient().getCaseId();
        String date = displayDateFormat.format(checkup.getDate());
        String premedicalHistory = checkup.getPatient().getPreMedicalHistory();
        String doctorName = checkup.getDentistName();
        int lableX = 20;
        int valueX = 130;
        g.drawString("Doctor Name  ", lableX, 75);
        g.drawString(":    " + doctorName, valueX, 75);
        g.drawString("Date ", 320, 75);
        g.drawString(":    " + date, 420, 75);
        g.drawString("Patient Name ", lableX, 95);
        g.drawString(":    " + name, valueX, 95);
        g.drawString("Case Paper No  ", 320, 95);
        g.drawString(":    " + casepapernumber, 420, 95);
        g.drawString("Pre Medical History ", lableX, 115);
        g.drawString(":    " + premedicalHistory, valueX, 115);
    }

    public void printPriscription() {
        PrinterJob job = PrinterJob.getPrinterJob(); 
        job.setPrintable(this); 
        if (job.printDialog() == true) { 
            try {
                job.print();
            } catch (PrinterException ex) {System.out.println(ex.getMessage());}
        }
    }
    
}
