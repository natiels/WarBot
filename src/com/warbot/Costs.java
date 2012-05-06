package com.warbot;

/**
 * Created by IntelliJ IDEA.
 * User: han han
 * Date: 3/7/12
 * Time: 9:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class Costs {
    public static int getCastleUpgradeCost(int currentLevel){
        int cost = 1000;
        if(currentLevel == 0)
            return cost;

        for(int i = 0;i < currentLevel;i++){
            cost = cost * 2;
        }

        return cost;
    }
    
    public static boolean canAffordHouse(Resources resources){
        return (resources.wood >= 100 && resources.stone >= 100);
    }
}
