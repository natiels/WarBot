package com.warbot;

import org.apache.commons.logging.LogFactory;

import java.util.Date;

public class Main {
    static {
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
    }

    public static void main(String[] args)
    {
        int checkTime = 0;
        WarBot warBot = new WarBot();

        while(true) {
            try{
                System.out.println("--------------------------" + new Date() + "--------------------------");
                
                if(!warBot.loggedIn){
                    warBot.login();
                }

                //Properties configFile = new Properties();
                //configFile.load(getClass().getClassLoader().getResourceAsStream("etc/example.properties"));

                //System.out.println(configFile.getProperty("user"));

                warBot.getActiveSpells();
                
                //warBot.buyLongBow();

                //cast spells
                //if(!warBot.isSpellActive("Snow")) warBot.buySpellSnow();
                //if(!warBot.isSpellActive("Forest Call")) warBot.buySpellForestCall();
                //if(!warBot.isSpellActive("Iron Pride")) warBot.buySpellIronPride();
                //if(!warBot.isSpellActive("Open Earth")) warBot.buySpellOpenEarth();


                //mana loop
                //if(!warBot.isSpellActive("Mana")) warBot.buySpellMana();

                //if(!warBot.isSpellActive("Mist")) warBot.buySpellMist();

                //warBot.buyLongBow();
                //warBot.buyLongSword();
                //warBot.buyBow();
                //if(com.warbot.Costs.canAffordHouse(warBot.resources)) warBot.buyHouse();

                //if(warBot.resources.stone >= com.warbot.Costs.getCastleUpgradeCost(warBot.resources.level)) warBot.upgradeCastle();
                //if(com.warbot.Costs.canAffordHouse(warBot.resources)) warBot.buyHouse();

                //warBot.repairCastle();

                checkTime = 300000;
                float minutes = (float)checkTime / 60000;
                System.out.println("Sleeping until next check in " + minutes + " minutes");
                Thread.sleep(checkTime);
            }
            catch(Exception e){
                //if something blows up wait 2 mins and try again
                warBot.loggedIn = false;
                try{
                    Thread.sleep(120000);
                }catch(Exception ex){
                    System.out.println("Something horrible has happened");
                }
            }
        }
        
        //warBot.quit();
    }
}
