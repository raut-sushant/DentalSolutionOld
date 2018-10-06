/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ekdant.dentalsolution.principal;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author user
 */
class Login extends JFrame {

    public Login() {
        
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            SwingUtilities.updateComponentTreeUI(this);
        } catch (ClassNotFoundException error) {
            JOptionPane.showMessageDialog(null, "Error matching downloads theme : " + error);
        } catch (IllegalAccessException error) {
            JOptionPane.showMessageDialog(null, "Error matching downloads theme : " + error);
        } catch (InstantiationException error) {
            JOptionPane.showMessageDialog(null, "Error matching downloads theme : " + error);
        } catch (UnsupportedLookAndFeelException error) {
            JOptionPane.showMessageDialog(null, "Error matching downloads theme : " + error);
        }
        
    }
    
}
