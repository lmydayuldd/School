package com.mygdx.schoolRPG.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.schoolRPG.tools.Button;
import com.mygdx.schoolRPG.tools.CircularSelector;
import com.mygdx.schoolRPG.tools.MenuListSelector;

import java.util.ArrayList;

/**
 * Created by IVO on 15.07.2014.
 * main menu
 */

public class MainMenu extends Menu {
    public int ID = 0;

    float PLAYSIZE;

    public int curMenu = 0;
    Texture backGround, title, cursor, overlay;
    float overlayAngle = 0;

    MenuListSelector selector;

    public MainMenu(int id, boolean android) {
        super(id, android);
        ID = 0;

        PLAYSIZE = Gdx.graphics.getHeight()/2.5f;

    }

    public void invalidate() {
        super.invalidate();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            int index = selector.getSelectedIndex();
            if (index == 0) {
                nextMenuSetting = 0;
                nextMenu = 1;
            } else if (index == 1) {
                optionsOpen = true;
                //nextMenuSetting = 0;
                //nextMenu = 2;
            }
            else if (index == 2) {
                nextMenuSetting = 1;
                nextMenu = 1;
            }
        }
    }

    @Override
    public void updateLanguage() {
        super.updateLanguage();
        ArrayList<String> list = new ArrayList<String>();
        if (currentLanguage == 0) {
            list.add("Play");
            list.add("Options");
            list.add("Info");
        } else {
            list.add("Играть");
            list.add("Опции");
            list.add("Инфо");
        }
        if (selector != null) {
            selector.titles = list;
        } else {
            selector = new MenuListSelector(list, assets, "cursor.png", mainFont, Gdx.graphics.getHeight(), 0, 0, true, this);
        }
    }

    @Override
    public void draw(SpriteBatch batch, ShapeRenderer renderer) {
        float screenRatioX = Gdx.graphics.getWidth()/1280.0f;
        float screenRatioY = Gdx.graphics.getHeight()/720.0f;
        batch.begin();
        batch.draw(backGround, 0, 0, Gdx.graphics.getWidth()/screenRatioX, Gdx.graphics.getHeight()/screenRatioY);
        float centerX = Gdx.graphics.getWidth()/screenRatioX/2;
        float centerY = Gdx.graphics.getHeight()/screenRatioY/2;
        batch.draw(new TextureRegion(overlay), centerX - overlay.getWidth()/2, centerY - overlay.getHeight()/2, 750.0f, 750.0f, 1500, 1500, 1, 1, overlayAngle, true);
        overlayAngle += 0.01f;
        batch.draw(title, Gdx.graphics.getWidth()/screenRatioX/2 - title.getWidth()/2, Gdx.graphics.getHeight()/screenRatioY - title.getHeight() * 1.5f);
        selector.draw(batch, optionsOpen);
        batch.end();
        super.draw(batch, renderer);
    }

    @Override
    public void load(AssetManager assets) {
        super.load(assets);
        //assets.load("play.png", Texture.class);
        assets.load("bg.png", Texture.class);
        assets.load("bg_new.png", Texture.class);
        assets.load("bg_overlay.png", Texture.class);
        assets.load("title.png", Texture.class);
        assets.load("cursor.png", Texture.class);
        //assets.load("options.png", Texture.class);
        //assets.load("credits.png", Texture.class);
    }

    @Override
    public void initialiseResources() {
        if (!initialised) {
            super.initialiseResources();
            backGround = (assets.get("bg_new.png"));
            updateLanguage();
            title = assets.get("title.png", Texture.class);
            overlay = assets.get("bg_overlay.png", Texture.class);
            initialised = true;
        }
    }

}
