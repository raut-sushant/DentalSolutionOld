/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ekdant.dentalsolution.domain;

/**
 *
 * @author Sushant Raut
 */
public class SettingsBean {
    private String key;
    private String value;
    private boolean editable;
    
    public SettingsBean(){
    }
    
    public SettingsBean(String key, String value){
        this.key = key;
        this.value = value;
        this.editable = true;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }
    
}
