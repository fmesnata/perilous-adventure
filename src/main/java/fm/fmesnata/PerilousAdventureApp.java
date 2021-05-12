package fm.fmesnata;

import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import fm.fmesnata.component.PlayerComponent;
import fm.fmesnata.factory.PerilousAdventureFactory;
import javafx.scene.input.KeyCode;

import static com.almasb.fxgl.dsl.FXGL.*;


public class PerilousAdventureApp extends GameApplication {

    private Entity player;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(1280);
        gameSettings.setHeight(720);
        gameSettings.setApplicationMode(ApplicationMode.DEVELOPER);
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new PerilousAdventureFactory());
        setLevelFromMap("level0.tmx");
        player = spawn("player", 100, 400);

        Viewport viewport = getGameScene().getViewport();
        viewport.bindToEntity(player, getAppWidth() / 2, getAppHeight() / 2);
        viewport.setLazy(true);
    }

    @Override
    protected void initPhysics() {
        getPhysicsWorld().setGravity(0, 800);
    }

    @Override
    protected void initInput() {
        getInput().addAction(new UserAction("right") {
            @Override
            protected void onAction() {
                player.getComponent(PlayerComponent.class).right();
            }

            @Override
            protected void onActionEnd() {
                player.getComponent(PlayerComponent.class).stop();
            }
        }, KeyCode.D);
        getInput().addAction(new UserAction("left") {
            @Override
            protected void onAction() {
                player.getComponent(PlayerComponent.class).left();
            }

            @Override
            protected void onActionEnd() {
                player.getComponent(PlayerComponent.class).stop();
            }
        }, KeyCode.A);
        getInput().addAction(new UserAction("jump") {
            @Override
            protected void onActionBegin() {
                player.getComponent(PlayerComponent.class).jump();
            }

            @Override
            protected void onActionEnd() {
                player.getComponent(PlayerComponent.class).stop();
            }
        }, KeyCode.W);
        getInput().addAction(new UserAction("rightDash") {
            @Override
            protected void onActionBegin() {
                player.getComponent(PlayerComponent.class).rightDash();
            }

            @Override
            protected void onActionEnd() {
                player.getComponent(PlayerComponent.class).stop();
            }
        }, KeyCode.RIGHT);
        getInput().addAction(new UserAction("leftDash") {
            @Override
            protected void onActionBegin() {
                player.getComponent(PlayerComponent.class).leftDash();
            }

            @Override
            protected void onActionEnd() {
                player.getComponent(PlayerComponent.class).stop();
            }
        }, KeyCode.LEFT);
    }
}