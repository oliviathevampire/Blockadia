/*
 * this is to only test for things for the game
 * this is a warning from pheonix
 * if works pheonixfirewingz will add to the main game
 * -------------------------------------
 * @author Pheonixfirewingz aka Mystic4pheonix.
 * -------------------------------------
 */
package team.priv.pheonix.testingzone.engine;

import team.hdt.blockadia.game_engine.core.Display;

/**
 *
 * @author 326296
 */
public class TestMain {
    public static Display display;   
    public static void main(String[] args){
        display = new Display("tester",1280,720);
        Init.run();
        display.TestEnd();
    }
    
}
