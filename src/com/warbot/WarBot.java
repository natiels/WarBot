package com.warbot;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.warbot.config.Config;

import java.util.ArrayList;
import java.util.List;

public class WarBot {
    private final WebClient webClient;
    private HtmlPage page;
    public Resources resources = new Resources();
    public ArrayList<String> activeSpells = new ArrayList<String>();
    public boolean loggedIn = false;
    
    public WarBot(){
        webClient = new WebClient(BrowserVersion.FIREFOX_3_6);
    }
    
    public void login() throws Exception {
        System.out.println("Logging In");
        try{
            page = webClient.getPage(Config.getBaseUrl() + "/login/");
        }catch(Exception e){
            e.printStackTrace();
        }

        HtmlForm form = page.getForms().get(0);

        HtmlInput user = form.getInputByName("username");
        HtmlInput pass = form.getInputByName("password");
        page.setFocusedElement(pass);
        HtmlSubmitInput submit = form.getInputByValue("    Submit    ");

        List<HtmlElement> list = form.getElementsByTagName("button");
        System.out.println();

        user.setValueAttribute(Config.getUserId());
        pass.setValueAttribute(Config.getPassword());

        try{
            page = submit.click();
        }catch(Exception e){
            e.printStackTrace();
        }
        loggedIn = true;
        System.out.println("Logged In!");

    }

    public void quit(){
        webClient.closeAllWindows();
    }

    public void goThroneRoom(){
        try{
            System.out.println("Going to Throne Room");
            HtmlAnchor anchor = page.getAnchorByText("Throne Room");
            page = anchor.click();
            refreshResources();
        }catch(Exception e){
            errorLogout();
        }
    }
    
    private void refreshResources() throws Exception {
        List<HtmlElement> list = page.getElementsByTagName("td");
        for (HtmlElement e:list){
            if(e.getTextContent().contains("Ore:")){
                String content = e.getTextContent();
                content = content.replace("Ore:", "");
                content = content.trim();
                resources.ore = Integer.parseInt(content);

                System.out.println("Found Ore: " + resources.ore);
            }
            if(e.getTextContent().contains("Wood:")){
                String content = e.getTextContent();
                content = content.replace("Wood:", "");
                content = content.trim();
                resources.wood = Integer.parseInt(content);

                System.out.println("Found Wood: " + resources.wood);
            }
            if(e.getTextContent().contains("Stone:")){
                String content = e.getTextContent();
                content = content.replace("Stone:", "");
                content = content.trim();
                resources.stone = Integer.parseInt(content);

                System.out.println("Found Stone: " + resources.stone);
            }
        }
        list = page.getElementsByTagName("th");
        for(HtmlElement e:list){
            if(e.getTextContent().contains("Castle")){
                String content = e.getTextContent();
                content = content.replace("Castle:", "");
                content = content.trim();
                resources.level = Integer.parseInt(content);

                System.out.println("Castle Level: " + resources.level);
                break;
            }
        }
    }

    public void goForge(){
        try{
            System.out.println("Going to Forge");
            page = webClient.getPage(Config.getBaseUrl() + "/forge/");
            refreshResources();
        }catch(Exception e){
            errorLogout();
        }
    }

    public void goTower(){
        try{
            System.out.println("Going to Tower");
            page = webClient.getPage(Config.getBaseUrl() + "/tower/");
            refreshResources();
        }catch(Exception e){
            errorLogout();
        }
    }
    
    public void goCastle(){
        try{
            System.out.println("Going to Castle");
            page = webClient.getPage(Config.getBaseUrl() + "/castle/");
            refreshResources();
        }catch(Exception e){
            errorLogout();
        }
    }

    public void goBarracks(){
        try{
            System.out.println("Going to Barracks");
            page = webClient.getPage(Config.getBaseUrl() + "/barracks/");
        }catch(Exception e){
            errorLogout();
        }
    }
    
    public void buyItem(String item, String count){
        try{
            HtmlInput input = (HtmlInput) page.getElementByName(item);
            input.setValueAttribute(count);
            HtmlSubmitInput submit = (HtmlSubmitInput) page.getElementByName("buy");

            submit.click();
            Thread.sleep(2000);
        }catch (Exception e){
            errorLogout();
        }
    }

    public void buySpellSnow(){
        buySpell("Snow", "/tower/cast/snow");
    }

    public void buySpellMana(){
        buySpell("Mana", "/tower/cast/mana");
    }

    public void buySpellMist(){
        buySpell("Mist", "/tower/cast/mist");
    }

    public void buySpellForestCall(){
        buySpell("Forest Call", "/tower/cast/forest-call");
    }

    public void buySpellIronPride(){
        buySpell("Iron Pride", "/tower/cast/iron-pride");
    }

    public void buySpellOpenEarth(){
        buySpell("Open Earth", "/tower/cast/open-earth");
    }
    
    private void buySpell(String messageName, String elementName){
        goTower();
        try{
            System.out.println("Buying Spell: " + messageName);
            HtmlSubmitInput submit = (HtmlSubmitInput) page.getElementByName(elementName);
            submit.click();
            Thread.sleep(2000);
        }catch (Exception e){
            errorLogout();
        }
    }

    public void buyLongBow(){
        goForge();
        System.out.println("Buying Long Bow");
        buyItem("custom_buy_Long Bow", "1");
    }
    
    public void buyLongSword(){
        goForge();
        System.out.println("Buying Long Sword");
        buyItem("custom_buy_Long Sword", "1");
    }

    public void buyBow(){
        goForge();
        System.out.println("Buying Bow");
        buyItem("custom_buy_Bow", "1");
    }

    public void buyHouse(){
        goCastle();
        try{
            HtmlSubmitInput submit = (HtmlSubmitInput) page.getElementByName("build_house");
            submit.click();
            Thread.sleep(2000);
        }catch (Exception e){
            errorLogout();
        }
    }

    public void upgradeCastle(){
        goCastle();
        try{
            HtmlSubmitInput submit = (HtmlSubmitInput) page.getElementByName("upgrade_castle");
            System.out.println("Attempting Castle Upgrade");
            submit.click();
            Thread.sleep(2000);
        }catch (Exception e){
            //if we get an exception then attempt repairing
            repairCastle();
            //errorLogout();
        }
    }

    public void repairCastle(){
        goCastle();
        try{
            HtmlSubmitInput submit = (HtmlSubmitInput) page.getElementByName("repair_castle");
            System.out.println("Attempting to Repair Castle");
            submit.click();
            Thread.sleep(2000);
        }catch (Exception e){
            errorLogout();
        }
    }
    
    public void getActiveSpells(){
        goTower();
        try{
            HtmlAnchor anchor = page.getAnchorByHref("#active_spells");
            page = anchor.click();
            Thread.sleep(1000);

            activeSpells.clear();
            HtmlElement spellDiv = page.getElementById("active_spells");
            List<HtmlElement> rows = spellDiv.getHtmlElementsByTagName("tr");
            for(HtmlElement e:rows){
                List<HtmlElement> cols = e.getHtmlElementsByTagName("td");
                for(HtmlElement td:cols){
                    activeSpells.add(td.getTextContent());
                    break; //bail after first col
                }
            }

            for(String spell:activeSpells){
                System.out.println("Active Spell: " + spell);
            }
        }catch (Exception e){
            errorLogout();
        }
    }
    
    public boolean isSpellActive(String spell){
        for(String s:activeSpells){
            if(s.equalsIgnoreCase(spell)) return true;
        }
        return false;
    }
    
    private void errorLogout(){
        System.out.println("Error encountered: setting loggedIn to false for retry.");
        loggedIn = false;
    }


}

