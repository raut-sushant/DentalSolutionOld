/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ekdant.dentalsolution.dao;

import com.ekdant.dentalsolution.domain.AppointmentBean;
import com.ekdant.dentalsolution.domain.DoctorBean;
import com.ekdant.dentalsolution.domain.PatientBean;
import com.ekdant.dentalsolution.domain.TimeslotBean;
import com.ekdant.dentalsolution.domain.TreatmentBean;
import com.ekdant.dentalsolution.utilities.ConnectionPool;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Sushant Raut
 */
public class AppointmentsDao {
    
    ConnectionPool connection;
    DateFormat databaseDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    public AppointmentsDao(){
        connection = ConnectionPool.getInstance();
    }
    
    public List<TimeslotBean> fetchTimeSlots(String startTime, String endTime){
        List<TimeslotBean> timeslots = new ArrayList<TimeslotBean>();
        String timeslotSQL = "SELECT * FROM TIMESLOT WHERE START_TIME BETWEEN (SELECT VALUE FROM SETTINGS WHERE KEYNAME = '"+startTime+"') " +
                                "AND (SELECT VALUE FROM SETTINGS WHERE KEYNAME = '"+endTime+"') ORDER BY ID";
        try{
            ResultSet rs = connection.getResult(timeslotSQL);
            while(rs.next()){
                TimeslotBean timeslot = new TimeslotBean();
                timeslot.setId(rs.getInt("ID"));
                timeslot.setStartTime(rs.getString("START_TIME").substring(0, 5));
                timeslot.setEndTime(rs.getString("END_TIME").substring(0, 5));
                timeslots.add(timeslot);
            }
        }catch(Exception e){System.out.println(e.getMessage());}
        return timeslots;
    }
    
    public String fetchStartDateForSlot(int slotId){
        String startDate = "";
        ResultSet rs = connection.getResult("SELECT START_TIME FROM TIMESLOT WHERE ID ="+slotId+" AND ACTIVEIND = 1");
        try {
            while(rs.next()){
                startDate = rs.getString(1);
            }
        } catch (SQLException ex) { }
        return startDate;
    }
    
    public int fetchAppointmentStatus(int slotId, String appointmentDate){
        int status = 1;
        ResultSet rs = connection.getResult("SELECT STATUS FROM appointments a WHERE DATE= '"+appointmentDate+"' AND SLOTID= "+slotId);
        try {
            while(rs.next()){
                status = rs.getInt(1);
            }
        } catch (SQLException ex) {System.out.println(ex.getMessage());}
        return status;
    }
    
    public boolean addAppointment(AppointmentBean appointment){
        boolean success = true;
        try {
            connection.stmt.execute("INSERT INTO APPOINTMENTS( DATE, SLOTID, PATIENTID, DOCTORID, TREATMENTID, COMMENTS, STATUS, ACTIVEIND) VALUES (  '"+databaseDateFormat.format(appointment.getAppointmentDate())+"', "+appointment.getSlotId()+", "+appointment.getPatient().getPatientId()+", "+appointment.getDoctor().getDoctorId()+", "+appointment.getTreatment().getTreatmentId()+", '"+appointment.getComment()+"', 1, 1)");
        } catch (SQLException ex) {success = false;}
        return success;
    }
    
    public List<AppointmentBean> fetchAppointmentDetails(String startDate, String endDate, String doctorName){
        List<AppointmentBean> appointments = new ArrayList<AppointmentBean>();
        String loadAppointmentMap = "SELECT A.ID AS APPOINTMENTID, A.DATE AS APPOINTMENTDATE, A.SLOTID AS SLOTID, A.PATIENTID AS PATIENTID, " +
                "A.DOCTORID AS DOCTORID, A.COMMENTS AS COMMENTS, A.STATUS AS APPOINTMENTSTATUS, A.TREATMENTID AS TREATMENTID, " +
                "P.NAME AS PATIENTNAME, P.CASEID AS CASEID, P.TELEPHONE AS PATIENTCONTACT, P.EMAIL AS PATIENTEMAIL, " +
                "D.NAME AS DOCTORNAME, D.TELEPHONE AS DOCTORCONTACT, T.TREATMENTNAME AS TREATMENTNAME FROM APPOINTMENTS A " +
                "JOIN PATIENTS P ON A.PATIENTID = P.PATIENTID " +
                "JOIN DOCTORS D ON A.DOCTORID = D.DOCTORID AND D.NAME = '"+doctorName+"' " +
                "LEFT JOIN TREATMENTS T ON A.TREATMENTID = T.TREATMENTID " +
                "WHERE DATE BETWEEN '"+startDate+"' AND '"+endDate+"' AND A.ACTIVEIND = 1 ORDER BY SLOTID";
        
        
        ResultSet rs = connection.getResult(loadAppointmentMap);
        
        try {
            while(rs.next()){
               AppointmentBean appointment = new AppointmentBean();
               DoctorBean doctor = new DoctorBean();
               TreatmentBean treatment = new TreatmentBean();
               PatientBean patient = new PatientBean();    
               appointment.setAppointmentId(rs.getInt("APPOINTMENTID"));
               appointment.setSlotId(rs.getInt("SLOTID"));
               if(rs.getString("APPOINTMENTDATE").isEmpty())
                    appointment.setAppointmentDate(databaseDateFormat.parse(rs.getString("APPOINTMENTDATE")));
               appointment.setStatus(rs.getInt("APPOINTMENTSTATUS"));
               appointment.setComment(rs.getString("COMMENTS"));
               doctor.setTelephone(rs.getString("DOCTORCONTACT"));
               doctor.setDoctorId(rs.getInt("DOCTORID"));
               doctor.setName(rs.getString("DOCTORNAME"));
               patient.setPatientId(rs.getInt("PATIENTID"));
               patient.setTelephone(rs.getString("PATIENTCONTACT"));
               patient.setEmail(rs.getString("PATIENTEMAIL"));
               patient.setName(rs.getString("PATIENTNAME"));
               patient.setCaseId(rs.getInt("CASEID")+"");
               treatment.setTreatmentId(rs.getInt("TREATMENTID"));
               treatment.setTreatmentName(rs.getString("TREATMENTNAME"));
               appointment.setTreatment(treatment);
               appointment.setDoctor(doctor);
               appointment.setPatient(patient);
               appointments.add(appointment);
            }
        } catch (Exception ex) {System.out.println(ex.getMessage());}
        
        return appointments;
    }
    
    public boolean updateAppointment(AppointmentBean appointment){
        boolean success = true;
        String sql = "update appointments set status = "+appointment.getStatus()+" where DATE= '"+databaseDateFormat.format(appointment.getAppointmentDate())+"' AND SLOTID= "+appointment.getSlotId();
        
        try {
            connection.stmt.executeUpdate(sql);
        }catch(Exception e){ success = false; }
        
        return success;
    }
    
    public int getAppointmentCount(Date date) {
        ResultSet rs = connection.getResult("SELECT COUNT(*) FROM APPOINTMENTS WHERE DATE = '"+databaseDateFormat.format(date)+"' AND ACTIVEIND = 1");
        int count = 0;
        try {
            while(rs.next()){
               count = rs.getInt(1);
            }
        } catch (SQLException ex) { }
        return count;
    }
}
