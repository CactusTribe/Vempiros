package common;

import javafx.scene.media.AudioClip;

import java.nio.file.Paths;

/**
 * Created by cactustribe on 13/04/17.
 */
public class Sounds {

    public enum SoundType{
        DEATH_VAMP, GAME_OVER, GAME_WIN, GUN_FIRE, GUN_RELOAD;
    }

    public static AudioClip DEATH_VAMP = new AudioClip(Paths.get("resources/sounds/dead_sound.mp3").toUri().toString());
    public static AudioClip GUN_FIRE = new AudioClip(Paths.get("resources/sounds/gun_fire.mp3").toUri().toString());
    public static AudioClip GUN_RELOAD = new AudioClip(Paths.get("resources/sounds/gun_reload.mp3").toUri().toString());
    public static AudioClip GAME_OVER = new AudioClip(Paths.get("resources/sounds/game_over.mp3").toUri().toString());
    public static AudioClip GAME_WIN = new AudioClip(Paths.get("resources/sounds/game_win.mp3").toUri().toString());

    public static void play(SoundType type){
        switch (type){

            case DEATH_VAMP:
                DEATH_VAMP.play();
                break;
            case GAME_OVER:
                GAME_OVER.play();
                break;
            case GAME_WIN:
                GAME_WIN.play();
                break;
            case GUN_FIRE:
                GUN_FIRE.play();
                break;
            case GUN_RELOAD:
                GUN_RELOAD.play();
                break;
        }
    }
}
