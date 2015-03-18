/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.lcddisplay.configuration;

/**
 *
 * @author blinov_is
 */
public class DisplayPage {
    public String PageName;             //ID
    public boolean HaveDynamicElements; //want to exec anmimation by thread
    public String[] HWDisplays;         //links to HWDisplays
}
