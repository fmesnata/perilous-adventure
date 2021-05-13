package fm.fmesnata.component;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.scene.image.Image;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.image;

public class PlayerComponent extends Component {

    public static final int DASH_VELOCITY = 2000;
    private PhysicsComponent physics;
    private final AnimationChannel animationIdle, animationRun, animationJump;
    private final AnimatedTexture texture;
    private int jump = 2;
    private int dash = 1;

    public PlayerComponent() {
        Image idle = image("player_idle.png");
        Image run = image("player_run.png");
        Image jump = image("player_jump.png");
        animationIdle = new AnimationChannel(idle, 11, 64, 64, Duration.seconds(0.6), 0, 10);
        animationRun = new AnimationChannel(run, 12, 64, 64, Duration.seconds(0.6), 0, 11);
        animationJump = new AnimationChannel(jump, 1, 64, 64, Duration.seconds(0.5), 0, 0);
        texture = new AnimatedTexture(animationIdle);
        texture.loop();
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
        physics.onGroundProperty().addListener((obs, old, isOnGround) -> {
            if (isOnGround) {
                jump = 2;
                dash = 1;
            }
        });
    }

    @Override
    public void onUpdate(double tpf) {
        if (physics.isMovingY()) {
            if (texture.getAnimationChannel() != animationJump) {
                texture.loopAnimationChannel(animationJump);
            }
        } else if (physics.isMovingX()) {
            if (texture.getAnimationChannel() != animationRun) {
                texture.loopAnimationChannel(animationRun);
            }
        } else {
            if (texture.getAnimationChannel() != animationIdle) {
                texture.loopAnimationChannel(animationIdle);
            }
        }
    }

    public void right() {
        entity.setScaleX(1);
        physics.setVelocityX(+300);
    }

    public void left() {
        entity.setScaleX(-1);
        physics.setVelocityX(-300);
    }

    public void jump() {
        if (true) {
            physics.setVelocityY(-1400);
            jump--;
        }
    }

    public void rightDash() {
        if (true) {
            entity.setScaleX(1);
            physics.setVelocityX(DASH_VELOCITY);
            dash--;
        }
    }

    public void leftDash() {
        if (true) {
            entity.setScaleX(-1);
            physics.setVelocityX(-DASH_VELOCITY);
            dash--;
        }
    }

    public void stop() {
        physics.setVelocityX(0);
    }
}
