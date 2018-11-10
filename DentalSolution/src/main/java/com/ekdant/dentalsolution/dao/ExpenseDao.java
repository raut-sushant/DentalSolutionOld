/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ekdant.dentalsolution.dao;

import com.ekdant.dentalsolution.domain.ExpenseBean;
import com.ekdant.dentalsolution.domain.ExpenseCategoryBean;
import com.ekdant.dentalsolution.utilities.ConnectionPool;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sushant Raut
 */
public class ExpenseDao {
    
    ConnectionPool connection;
    DateFormat databaseDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    public ExpenseDao(){
        connection = ConnectionPool.getInstance();
    }
    
    public List<String> fetchExpenseCategories(){
        List<String> expenseCategories = new ArrayList<String>();
        String getReferedByQuery = "SELECT EXPENSECATEGORY FROM EXPENSECATEGORY WHERE ACTIVEIND = 1";
        ResultSet rs = connection.getResult(getReferedByQuery);
        try{
            while(rs.next()){
                expenseCategories.add(rs.getString("EXPENSECATEGORY"));
            }
        }catch(SQLException e){ }
        return expenseCategories;
    }
    
    public List<ExpenseBean> fetchExpenses(String searchText){
        List<ExpenseBean> expenses = new ArrayList<ExpenseBean>();
        String sql = "";
        if(searchText == null || searchText.isEmpty()){
            sql = "SELECT * FROM EXPENSES WHERE ACTIVEIND = 1 ORDER BY ID DESC";
        }else{
            sql = "SELECT * FROM EXPENSES WHERE EXPENSECATEGORY LIKE '%"+searchText+"%' OR NOTES LIKE '%"+searchText+"%' OR AMOUNT LIKE '%"+searchText+"%' OR DATE LIKE '%"+searchText+"%' AND ACTIVEIND = 1 ORDER BY ID DESC";
        }
        ResultSet rs = connection.getResult(sql);
        
        try{
            while(rs.next()){
                ExpenseBean expense = new ExpenseBean();
                expense.setId(rs.getInt("ID"));
                if(rs.getString("DATE").isEmpty())
                    expense.setDate(databaseDateFormat.parse(rs.getString("DATE")));
                expense.setExpenseCategory(rs.getString("EXPENSECATEGORY"));
                expense.setNotes(rs.getString("NOTES"));
                expense.setAmount(rs.getInt("AMOUNT"));
                expenses.add(expense);
            }
        }
        catch(Exception errorTable){ System.out.println(errorTable.getMessage());}
        return expenses;
    }
    
    public boolean addExpense(ExpenseBean expense){
        boolean success = true;
        try {
            String sql = "INSERT INTO EXPENSES ( EXPENSECATEGORY, AMOUNT, DATE, NOTES, ACTIVEIND) VALUES( '"+expense.getExpenseCategory()+"', "+expense.getAmount()+", '"+databaseDateFormat.format(expense.getDate())+"', '"+expense.getNotes()+"', 1)";
            connection.stmt.execute(sql);
        } catch (SQLException ex) { success = false; }
        return success;
    }
    
    public boolean addExpenseCategory(ExpenseCategoryBean expenseCategory){
        boolean success = true;
        try {
            String insertExpenseCategorySQL = "INSERT INTO EXPENSECATEGORY ( EXPENSECATEGORY, ACTIVEIND) VALUES( '"+expenseCategory.getName()+"', 1)";
            connection.stmt.execute(insertExpenseCategorySQL);
        } catch (SQLException ex) { success = false; }
        return success;
    }
    
     public List<ExpenseCategoryBean> fetchExpenseCategories(String searchText){
        List<ExpenseCategoryBean> expenseCategories = new ArrayList<ExpenseCategoryBean>();
        String sql = "";
        if(searchText == null || searchText.isEmpty()){
            sql = "SELECT * FROM EXPENSECATEGORY WHERE ACTIVEIND = 1 ORDER BY EXPENSECATEGORY";
        }else{
            sql = "SELECT * FROM EXPENSECATEGORY WHERE EXPENSECATEGORY LIKE '%"+searchText+"%' AND ACTIVEIND = 1 ORDER BY EXPENSECATEGORY";
        }
        ResultSet rs = connection.getResult(sql);
        
        try{
            while(rs.next()){
                ExpenseCategoryBean expenseCategory = new ExpenseCategoryBean();
                expenseCategory.setId(rs.getInt("ID"));
                expenseCategory.setName(rs.getString("EXPENSECATEGORY"));
                expenseCategories.add(expenseCategory);
            }
        }
        catch(SQLException errorTable){ }
        return expenseCategories;
    }
     
    public boolean expenseCategoryNotPresent(String newExpenseCategory) {
        int expenseCategoryCount = 0;
        ResultSet rs = connection.getResult("SELECT COUNT(*) FROM expensecategory WHERE EXPENSECATEGORY = '"+newExpenseCategory+"' AND ACTIVEIND = 1");
        try {
            while(rs.next()){
                expenseCategoryCount = rs.getInt(1);
            }
        } catch (SQLException ex) {System.out.println(ex.getMessage());}
        return expenseCategoryCount == 0;
    }
    
    public boolean updateExpenseCategory(ExpenseCategoryBean expenseCategory){
        boolean success = true;
        try {
            String updateExpenseCategorySQL = "UPDATE EXPENSECATEGORY SET EXPENSECATEGORY = '" + expenseCategory.getName() + "' WHERE ID = " + expenseCategory.getId();
            connection.stmt.execute(updateExpenseCategorySQL);
        } catch (SQLException ex) { success = false; }
        return success;
    }
    
    public boolean deleteExpenseCategory(ExpenseCategoryBean expenseCategory){
        boolean success = true;
        try {
            String deleteExpenseCategorySQL = "INSERT INTO EXPENSECATEGORY ( EXPENSECATEGORY, ACTIVEIND) VALUES( '"+expenseCategory.getName()+"', 1)";
            connection.stmt.execute(deleteExpenseCategorySQL);
        } catch (SQLException ex) { success = false; }
        return success;
    }
}
