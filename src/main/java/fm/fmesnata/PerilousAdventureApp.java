package fm.fmesnata;

import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.level.Level;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import fm.fmesnata.component.PlayerComponent;
import fm.fmesnata.factory.PerilousAdventureFactory;
import javafx.scene.input.KeyCode;

import static com.almasb.fxgl.dsl.FXGL.*;
import static fm.fmesnata.factory.EntityType.*;


public class PerilousAdventureApp extends GameApplication {

    private Entity player;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setTitle("Perilous adventure");
        gameSettings.setWidth(1280);
        gameSettings.setHeight(704);
        gameSettings.setApplicationMode(ApplicationMode.DEVELOPER);
        gameSettings.setDeveloperMenuEnabled(true);
        gameSettings.setFullScreenAllowed(true);
        gameSettings.setManualResizeEnabled(true);
        gameSettings.setPreserveResizeRatio(true);
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new PerilousAdventureFactory());
        getGameScene().setBackgroundRepeat("background_blue.png");
        Level level = setLevelFromMap("level0.tmx");
        player = spawn("player", 100, 400);

        Viewport viewport = getGameScene().getViewport();
        viewport.setBounds(0, 0, level.getWidth(), level.getHeight());
        viewport.bindToEntity(player, getAppWidth() / 2.0, getAppHeight() / 2.0);
        viewport.setLazy(true);
    }

    @Override
    protected void initPhysics() {
        getPhysicsWorld().setGravity(0, 4000);
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(PLAYER, SPIKE) {
            @Override
            protected void onCollisionBegin(Entity a, Entity b) {
                getGameController().startNewGame();
            }
        });
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(PLAYER, GOAL) {
            @Override
            protected void onCollisionBegin(Entity a, Entity b) {
                System.out.println("win");
            }
        });
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
    }
}