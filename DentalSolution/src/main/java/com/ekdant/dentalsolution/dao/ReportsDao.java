/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ekdant.dentalsolution.dao;

import com.ekdant.dentalsolution.domain.MonthlyReportBean;
import com.ekdant.dentalsolution.domain.ReferedByReportBean;
import com.ekdant.dentalsolution.domain.TreatmentReportBean;
import com.ekdant.dentalsolution.utilities.ConnectionPool;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Sushant Raut
 */
public class ReportsDao {
    ConnectionPool connection;
    DateFormat databaseDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public ReportsDao(){
        connection = ConnectionPool.getInstance();
    }
    
    public List<TreatmentReportBean> fetchTreatmentReport(Date startDate, Date endDate){
        List<TreatmentReportBean> treatmentReports = new ArrayList<TreatmentReportBean>();
        ResultSet rs = connection.getResult("SELECT SUM(fees) AS TOTALFEES, COUNT(*) AS OPD, TREATMENT FROM CHECKUP CK WHERE DATE(DATE) >=  '" + databaseDateFormat.format(startDate) + "' AND DATE(DATE) <=  '" + databaseDateFormat.format(endDate) + "' AND CK.ACTIVEIND = 1 GROUP BY TREATMENT");
        try{
            while (rs.next()){
                TreatmentReportBean treatmentReport = new TreatmentReportBean();
                treatmentReport.setTotalFees(rs.getFloat("TOTALFEES"));
                treatmentReport.setTotalOPD(rs.getInt("OPD"));
                treatmentReport.setTreatmentName(rs.getString("TREATMENT"));
                treatmentReports.add(treatmentReport);
            }
        }catch(Exception e){System.out.println(e.getMessage());}
        return treatmentReports;
    }
    
    public List<ReferedByReportBean> fetchReferedByReport(Date startDate, Date endDate){
        List<ReferedByReportBean> referedByReports = new ArrayList<ReferedByReportBean>();
        ResultSet rs = connection.getResult("SELECT SUM(fees) AS TOTALFEES, COUNT(*) AS OPD, REFFEREDBY FROM CHECKUP CK WHERE DATE(DATE) >=  '" + databaseDateFormat.format(startDate) + "' AND DATE(DATE) <=  '" + databaseDateFormat.format(endDate) + "' AND CK.ACTIVEIND = 1 AND REFFEREDBY != '' GROUP BY REFFEREDBY");
        try{
            while (rs.next()){
                ReferedByReportBean referedByReport = new ReferedByReportBean();
                referedByReport.setTotalFees(rs.getFloat("TOTALFEES"));
                referedByReport.setTotalOPD(rs.getInt("OPD"));
                referedByReport.setReferedBy(rs.getString("REFFEREDBY"));
                referedByReports.add(referedByReport);
            }
        }catch(Exception e){System.out.println(e.getMessage());}
        return referedByReports;
    }
    
    public List<MonthlyReportBean> fetchMonthlyReport(Date startDate, Date endDate){
        List<MonthlyReportBean> monthlyReports = new ArrayList<MonthlyReportBean>();
        ResultSet rs = connection.getResult("SELECT SUM(fees) AS FEES, SUM(consultantingDoctorFee) AS CONSULTATIONDOCTORFEES, COUNT(*) AS OPD, DATE(DATE) AS DATE FROM CHECKUP CK WHERE DATE(DATE) >=  '" + databaseDateFormat.format(startDate) + "' AND DATE(DATE) <=  '" + databaseDateFormat.format(endDate) + "' AND CK.ACTIVEIND = 1 GROUP BY DATE(DATE)");
        
        try{
            while (rs.next()){
                MonthlyReportBean monthlyReport = new MonthlyReportBean();
                monthlyReport.setOpd(rs.getInt("OPD"));
                monthlyReport.setFees(rs.getFloat("FEES"));
                if(rs.getString("DATE").isEmpty())
                    monthlyReport.setDate(databaseDateFormat.parse(rs.getString("DATE")));
                monthlyReport.setConsultingFees(rs.getFloat("CONSULTATIONDOCTORFEES"));
                monthlyReports.add(monthlyReport);
            }
        }catch(Exception e){System.out.println(e.getMessage());}
        return monthlyReports;
    }
    
    public List<MonthlyReportBean> fetchYearlyReport(Date startDate, Date endDate){
        List<MonthlyReportBean> monthlyReports = new ArrayList<MonthlyReportBean>();
        ResultSet rs = connection.getResult("SELECT SUM(fees) AS FEES, SUM(consultantingDoctorFee) AS CONSULTATIONDOCTORFEES, COUNT(*) AS OPD FROM CHECKUP CK WHERE DATE(DATE) >= '" + databaseDateFormat.format(startDate) + "' AND DATE(DATE) <= '" + databaseDateFormat.format(endDate) + "'");

        try{
            while (rs.next()){
                MonthlyReportBean monthlyReport = new MonthlyReportBean();
                monthlyReport.setOpd(rs.getInt("OPD"));
                monthlyReport.setFees(rs.getFloat("FEES"));
                monthlyReport.setConsultingFees(rs.getFloat("CONSULTATIONDOCTORFEES"));
                monthlyReports.add(monthlyReport);
            }
        }catch(Exception e){System.out.println(e.getMessage());}
        return monthlyReports;
    }
}
