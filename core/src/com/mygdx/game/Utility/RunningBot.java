package com.mygdx.game.Utility;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.Actor.NodeActor;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

/**
 * Created by boon8 on 11/11/2559.
 */
public class RunningBot {

    private Bot bot;
    private int ai_level;
    static public String filename;

    public RunningBot(int ai_level) {
        this.ai_level = ai_level;
    }

    public Bot startBot(int team, ArrayList<PlayerData> allData, ArrayList<NodeActor> allNode) {


        String path = Gdx.files.getLocalStoragePath();
        String packageName = null;

        if (this.ai_level == 1) {
            packageName = "com.mygdx.game.BotContainer.Default";
        } else {
            String classname = filename.split("\\.")[0];
            packageName = "com.mygdx.game.BotContainer." + classname;
        }

        File file = new File(path);

        try {

            URL url = file.toURI().toURL();
            URL[] urls = new URL[]{url};

            ClassLoader cl = new URLClassLoader(urls);
            Class cls = cl.loadClass(packageName);

            bot = (Bot) cls.getConstructor(Integer.TYPE, ArrayList.class, ArrayList.class).newInstance(team, allData, allNode);
            new Thread(bot).start();

        } catch (MalformedURLException e) {
        } catch (ClassNotFoundException e) {
        } catch (NullPointerException e) {
        } catch (IllegalAccessException e) {
        } catch (InstantiationException e) {
        } catch (NoSuchMethodException e) {
        } catch (InvocationTargetException e) {
        }

        return bot;

    }

}
