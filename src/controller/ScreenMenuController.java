package controller;

import common.ControlledScreen;

import java.awt.event.ActionEvent;

/**
 * Created by cactustribe on 12/03/17.
 */
public class ScreenMenuController implements ControlledScreen{
    ScreensController screen_controller;

    @Override
    public void setScreenParent(ScreensController screenParent){
        screen_controller = screenParent;
    }

    private void newGame(ActionEvent event){
        System.out.println("PLAY");
        //screen_controller.setScreen(Main.MENU_SCREEN);
    }
}
