/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ekdant.dentalsolution.printing;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author dinesh.mali
 */
public class TreatmentCanvas extends Canvas implements Printable{
    int height;
    int width;
       
    public void paint(Graphics g){
        JPanel parent =(JPanel)this.getParent();
        g.drawRect(0, 0, parent.getWidth(), parent.getHeight());
        g.drawString("This Is Treatment", 50, 50);
    }
    
    public static void printPage(){
        TreatmentCanvas canvas = new TreatmentCanvas();
        JFrame printFrame = new JFrame();
        printFrame.add(canvas);
        printFrame.setSize(400,400);
        printFrame.setVisible(true);
        PrinterJob printJob = PrinterJob.getPrinterJob();
        printJob.setPrintable(canvas);
        boolean ok = printJob.printDialog();
        if(ok){
            try{
                printJob.print();
            }catch(Exception e){
                System.out.println("Error Printing Treatement Page");
            }    
        }
        printFrame.dispose();
    }

    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if(pageIndex>0){
            return NO_SUCH_PAGE;
        }
        Graphics2D g = (Graphics2D)graphics;
        JPanel parent =(JPanel)this.getParent();
        g.drawRect(0, 0, parent.getWidth(), parent.getHeight());
        g.drawString("This Is Treatment", 50, 50);
        return PAGE_EXISTS;
    }
    
}
