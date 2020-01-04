/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.lcddisplay.manager;

/**
 *
 * @author blinov_is
 */
public interface IObjPinProcessing {
    void sendPIN_ObjPin(String Tag, Object Data);
    void sendPIN_StringPin(String Tag, String Data);
}