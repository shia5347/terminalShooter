package com.github.shia5347.ironcloset;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setIdleFPS(60);
        config.useVsync(true);
        config.setTitle("IronCloset");
        config.setResizable(false);
        
        config.setWindowedMode(1280,720);
       
        config.setWindowIcon(Files.FileType.Internal, "assets/Player.png");
        
        new Lwjgl3Application(new Boot(), config);
        
    }
}