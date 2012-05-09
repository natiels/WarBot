package com.warbot;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

import java.util.List;

public class Barracks {
    private WarBot warBot;


    public Barracks(WarBot warBot){
        this.warBot = warBot;
    }

    public void refreshAllocation() throws Exception{
        HtmlInput input = (HtmlInput) warBot.getPage().getElementByName("miners");
        //miners = Integer.parseInt(input.getValueAttribute());

        input = (HtmlInput) warBot.getPage().getElementByName("woodcutters");
        //miners = Integer.parseInt(input.getValueAttribute());
    }

    public int getCurrentFarmers(){
        warBot.goBarracks();
        HtmlInput input = (HtmlInput) warBot.getPage().getElementByName("farmers");
        return Integer.parseInt(input.getValueAttribute());
    }

    public void setAllocation(Allocation allocation){

        try{
            System.out.println("Setting Allocation to:");
            System.out.println("   " + allocation.toString());
            HtmlInput input = (HtmlInput) warBot.getPage().getElementByName("miners");
            input.setValueAttribute(String.valueOf(allocation.miners));
            input = (HtmlInput) warBot.getPage().getElementByName("woodcutters");
            input.setValueAttribute(String.valueOf(allocation.woodcutters));
            input = (HtmlInput) warBot.getPage().getElementByName("quarriers");
            input.setValueAttribute(String.valueOf(allocation.quarriers));
            input = (HtmlInput) warBot.getPage().getElementByName("farmers");
            input.setValueAttribute(String.valueOf(allocation.farmers));
            input = (HtmlInput) warBot.getPage().getElementByName("warriors");
            input.setValueAttribute(String.valueOf(allocation.warriors));

            HtmlSubmitInput submit;
            List<HtmlElement> list = warBot.getPage().getElementsByTagName("input");
            for (HtmlElement e:list){
                if(e instanceof HtmlSubmitInput){
                    submit = (HtmlSubmitInput) e;
                    if(submit.getValueAttribute().equals("Change allocation")){
                        submit.click();
                        Thread.sleep(2000);
                        break;
                    }
                }
            }

        }catch (Exception e){
            warBot.errorLogout();
        }

    }

    public Allocation getCastleBuilderNoMana(int warriors){
        int total = warBot.resources.citizens;

        int farmers = getRequiredFarmers();
        total = total - farmers;
        total = total - warriors;
        int quarriers = total;

        Allocation allocation = new Allocation();
        allocation.miners = 0;
        allocation.woodcutters = 0;
        allocation.warriors = warriors;
        allocation.farmers = (int) farmers;
        allocation.quarriers = quarriers;
        System.out.println(allocation.toString());
        return allocation;
    }

    public Allocation getLongBowBuilderNoMana(int warriors){
        Allocation allocation = new Allocation();
        int total = warBot.resources.citizens;
        allocation.farmers = getRequiredFarmers();
        allocation.warriors = warriors;
        allocation.woodcutters = (int) ((total - allocation.farmers - warriors) * .625);
        allocation.miners = total - allocation.getTotalAllocated();

        int remainder = total - allocation.getTotalAllocated();
        if(remainder > 0){
            allocation.woodcutters += remainder;
        }

        return allocation;
    }

    public int getRequiredFarmers(){
        float farmers = (warBot.resources.citizens * 2) / 10 + 1;
        return (int) farmers;
    }


}
