package com.warbot;


public class Allocation {
    public int miners = 0;
    public int woodcutters = 0;
    public int quarriers = 0;
    public int farmers = 0;
    public int warriors = 0;

    public String toString(){
        return "Miners: " + miners + " ** Woodcutters: " + woodcutters + " ** Quarriers: " + quarriers + " ** Warriors: " + warriors + " ** Farmers: " + farmers;
    }

    public int getTotalAllocated(){
        return miners + woodcutters + quarriers + farmers + warriors;
    }
}

