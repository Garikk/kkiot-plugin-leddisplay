/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.lcddisplay.manager;

import kkdev.kksystem.base.classes.plugins.PluginMessage;

/**
 *
 * @author blinov_is
 */
public interface IObjPinProcessing {
    public void sendPIN_ObjPin(String Tag,Object Data);
      public void sendPIN_StringPin(String Tag,String Data);
}