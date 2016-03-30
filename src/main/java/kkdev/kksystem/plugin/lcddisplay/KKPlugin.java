/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.lcddisplay;

import kkdev.kksystem.base.classes.plugins.PluginInfo;
import kkdev.kksystem.base.classes.plugins.PluginMessage;
import kkdev.kksystem.base.classes.plugins.simple.KKPluginBase;
import kkdev.kksystem.base.interfaces.IPluginBaseInterface;
import kkdev.kksystem.plugin.lcddisplay.manager.LcdDisplayManager;


/**     
 *
 * @author blinov_is
 */
public final class KKPlugin extends KKPluginBase   {
    String DisplayID;
    
    public KKPlugin()
    {
        super(new LEDPluginInfo());
        Global.PM=new LcdDisplayManager();
        DisplayID=java.util.UUID.randomUUID().toString();
    }

    @Override
    public void PluginInit(IPluginBaseInterface BaseConnector, String GlobalConfUID) {
        super.PluginInit(BaseConnector, GlobalConfUID); //To change body of generated methods, choose Tools | Templates.
        Global.PM.Init(this);
    }

    @Override
    public PluginMessage ExecutePin(PluginMessage Pin) {
        super.ExecutePin(Pin);
        Global.PM.ReceivePin(Pin.UIContextID,Pin.FeatureID,Pin.PinName,Pin.PinData);
        return null;
    }
}
