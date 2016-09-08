/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.lcddisplay;

import kkdev.kksystem.base.classes.plugins.PluginMessage;
import kkdev.kksystem.base.classes.plugins.simple.KKPluginBase;
import kkdev.kksystem.plugin.lcddisplay.manager.LcdDisplayManager;
import kkdev.kksystem.base.interfaces.IPluginBaseConnection;
import kkdev.kksystem.base.interfaces.IControllerUtils;


/**     
 *
 * @author blinov_is
 */
public final class KKPlugin extends KKPluginBase   {
    String DisplayID;
        public IControllerUtils SysUtils;
    public KKPlugin()
    { 
        super(new LEDPluginInfo());
        Global.PM = new LcdDisplayManager();

        DisplayID = java.util.UUID.randomUUID().toString();
    }

    @Override
    public void pluginInit(IPluginBaseConnection BaseConnector, String GlobalConfUID) {
        super.pluginInit(BaseConnector, GlobalConfUID); //To change body of generated methods, choose Tools | Templates.
        SysUtils = BaseConnector.systemUtilities();
        Global.PM.Init(this);
    }

    @Override
    public void executePin(PluginMessage Pin) {
        super.executePin(Pin);

        Global.PM.receivePin(Pin.FeatureID, Pin.UIContextID, Pin.pinName, Pin.getPinData());
    }

    public IControllerUtils GetUtils() {
        return SysUtils;
    }

}
