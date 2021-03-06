package com.mygdx.schoolRPG;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.schoolRPG.tools.AnimationSequence;
import com.mygdx.schoolRPG.tools.GlobalSequence;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Kraft on 04.09.2016.
 */
public class Item {
    enum EquipSlot {
        HEAD,
        BODY,
        NONE
    }
    EquipSlot equipSlot;
    float mass;
    String fileName;
    ArrayList<String> namesInLanguages;
    ArrayList<String> descriptionsInLanguages;
    String replaces;
    int stack;
    boolean stackable;
    int maxStack;
    String flagName;
    Texture icon;
    Texture bigIcon;
    GlobalSequence sides;

    public String getName(int language) {
        return namesInLanguages.get(language);
    }

    public String getDescription(int language) {
        return descriptionsInLanguages.get(language);
    }

    public Item(Item item) {
        fileName = item.fileName;
        icon = item.icon;
        bigIcon = item.bigIcon;
        sides = item.sides;
        mass = item.mass;
        stack = 1;
        equipSlot = item.equipSlot;
        stackable = item.stackable;
        maxStack = item.maxStack;
        flagName = item.flagName;
        replaces = item.replaces;
        namesInLanguages = item.namesInLanguages;
        descriptionsInLanguages = item.descriptionsInLanguages;
    }

    public Item(AssetManager assets, String worldPath, String name) {
        fileName = name;
        icon = assets.get(worldPath + "/items/icons/" + fileName + ".png");
        if (assets.isLoaded(worldPath + "/items/big_icons/" + fileName + ".png")) {
            bigIcon = assets.get(worldPath + "/items/big_icons/" + fileName + ".png");
        }
        sides = new GlobalSequence(assets,worldPath + "/items/sides/" + fileName + ".png", 3);
        FileHandle itemDir =  Gdx.files.internal(worldPath + "/items");
        FileHandle itemXML = null;
        for (FileHandle entry: itemDir.list()) {
            if (entry.nameWithoutExtension().equals(fileName)) {
                itemXML = entry;
            }
        }
        //loadTextInfo(assets, worldPath, language);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document xml = null;
        try {
            xml = dBuilder.parse(itemXML.file());
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        xml.getDocumentElement().normalize();
        mass = Integer.parseInt(xml.getDocumentElement().getAttribute("mass"));
        stack = 1;
        String slot = xml.getDocumentElement().getAttribute("equip_slot");
        if (slot.equals("HEAD")) {
            equipSlot = EquipSlot.HEAD;
        } else if (slot.equals("BODY")) {
            equipSlot = EquipSlot.BODY;
        } else {
            equipSlot = EquipSlot.NONE;
        }
        stackable = Boolean.parseBoolean(xml.getDocumentElement().getAttribute("stackable"));
        maxStack = Integer.parseInt(xml.getDocumentElement().getAttribute("maxStack"));
        flagName = xml.getDocumentElement().getAttribute("flag");
        replaces = xml.getDocumentElement().getAttribute("replaces");
        NodeList nList = xml.getElementsByTagName("eng");
        Node nNode = nList.item(0);
        Element eElement = (Element) nNode;
        namesInLanguages = new ArrayList<String>();
        namesInLanguages.add(eElement.getAttribute("name"));
        descriptionsInLanguages = new ArrayList<String>();
        descriptionsInLanguages.add(eElement.getAttribute("description"));
        nList = xml.getElementsByTagName("rus");
        nNode = nList.item(0);
        eElement = (Element) nNode;
        namesInLanguages.add(eElement.getAttribute("name"));
        descriptionsInLanguages.add(eElement.getAttribute("description"));
    }
}
