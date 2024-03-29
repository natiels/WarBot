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

                warBot.getActiveSpells();

                //cast spells
                //if(!warBot.isSpellActive("Mist")) warBot.buySpellMist();
                //if(!warBot.isSpellActive("Snow")) warBot.buySpellSnow();
                if(!warBot.isSpellActive("Forest Call")) warBot.buySpellForestCall();
                if(!warBot.isSpellActive("Iron Pride")) warBot.buySpellIronPride();
                //if(!warBot.isSpellActive("Open Earth")) warBot.buySpellOpenEarth();
                //if(!warBot.isSpellActive("Mana")) warBot.buySpellMana();


                warBot.buyLongBow();

                //If we have more farmers allocated then needed we must reallocate.
                if(warBot.barracks.getCurrentFarmers() > warBot.barracks.getRequiredFarmers()){
                    Allocation allocation = warBot.barracks.getLongBowBuilderNoMana(5);
                    warBot.barracks.setAllocation(allocation);
                }


                //warBot.buyLongSword();
                //warBot.buyBow();
                //if(com.warbot.Costs.canAffordHouse(warBot.resources)) warBot.buyHouse();


                if(warBot.resources.stone >= Costs.getCastleUpgradeCost(warBot.resources.level)) warBot.upgradeCastle();
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
