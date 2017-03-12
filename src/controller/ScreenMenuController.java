package controller;

import common.ControlledScreen;

/**
 * Created by cactustribe on 12/03/17.
 */
public class ScreenMenuController implements ControlledScreen{
    ScreensController screen_controller;

    @Override
    public void setScreenParent(ScreensController screenParent){
        screen_controller = screenParent;
    }
}
