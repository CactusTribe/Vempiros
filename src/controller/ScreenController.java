package controller;

import common.ControlledScreen;

/**
 * Created by cactustribe on 12/03/17.
 */
public class ScreenController implements ControlledScreen {

    public ScreensController screensController;

    @Override
    public void setScreenParent(ScreensController screenParent){
        screensController = screenParent;
    }
}
