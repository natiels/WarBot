package com.warbot;

import com.gargoylesoftware.htmlunit.html.HtmlElement;

import java.util.List;

public class Resources {
    public int food = 0;
    public int stone= 0;
    public int ore = 0;
    public int wood = 0;
    public int level = 0;
    public int houses = 0;
    public int citizens = 0;
    public int availableSpells = 0;
    private WarBot warBot;

    public Resources(WarBot warBot){
        this.warBot = warBot;
    }

    public void refreshResources() throws Exception {
        List<HtmlElement> list = warBot.getPage().getElementsByTagName("td");
        for (HtmlElement e:list){
            if(e.getTextContent().contains("Ore:")){
                String content = e.getTextContent();
                content = content.replace("Ore:", "");
                content = content.trim();
                ore = Integer.parseInt(content);
            }
            if(e.getTextContent().contains("Wood:")){
                String content = e.getTextContent();
                content = content.replace("Wood:", "");
                content = content.trim();
                wood = Integer.parseInt(content);
            }
            if(e.getTextContent().contains("Stone:")){
                String content = e.getTextContent();
                content = content.replace("Stone:", "");
                content = content.trim();
                stone = Integer.parseInt(content);
            }
            if(e.getTextContent().contains("Food:")){
                String content = e.getTextContent();
                content = content.replace("Food:", "");
                content = content.trim();
                food = Integer.parseInt(content);
            }
            if(e.getTextContent().contains("Available Spells:")){
                String content = e.getTextContent();
                content = content.substring(content.indexOf('/') - 2, content.indexOf('/'));
                content = content.trim();
                availableSpells = Integer.parseInt(content);
            }


        }
        list = warBot.getPage().getElementsByTagName("th");
        for(HtmlElement e:list){
            if(e.getTextContent().contains("Castle")){
                String content = e.getTextContent();
                content = content.replace("Castle:", "");
                content = content.trim();
                level = Integer.parseInt(content);
            }
            if(e.getTextContent().contains("Citizens:")){
                String content = e.getTextContent();
                content = content.substring(content.indexOf(':') + 1);
                content = content.substring(0, content.indexOf('('));
                content = content.trim();
                citizens = Integer.parseInt(content);
            }

        }

        printResources();
    }

    public void printResources(){
        System.out.println("   Ore: " + ore + " ** Wood: " + wood + " ** Stone: " + stone + " ** Food: " + food);
        System.out.println("   Castle Level: " + level + " ** Available Spells: " + availableSpells + " ** Citizens: " + citizens);
    }
}
