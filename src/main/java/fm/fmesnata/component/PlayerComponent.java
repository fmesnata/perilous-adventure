package fm.fmesnata.component;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.scene.image.Image;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.image;

public class PlayerComponent extends Component {

    public static final int RUN_VELOCITY = 300;
    public static final int JUMP_VELOCITY = -1400;
    public static final int PLAYER_SPRITE_SIZE = 64;
    private PhysicsComponent physics;
    private final AnimationChannel animationIdle, animationRun, animationJump, animationDoubleJump;
    private final AnimatedTexture texture;
    private int jump = 2;

    public PlayerComponent() {
        Image idle = image("player_idle.png");
        Image run = image("player_run.png");
        Image jump = image("player_jump.png");
        Image doubleJump = image("player_double_jump.png");
        animationIdle = new AnimationChannel(idle, 11, PLAYER_SPRITE_SIZE, PLAYER_SPRITE_SIZE, Duration.seconds(0.6), 0, 10);
        animationRun = new AnimationChannel(run, 12, PLAYER_SPRITE_SIZE, PLAYER_SPRITE_SIZE, Duration.seconds(0.6), 0, 11);
        animationJump = new AnimationChannel(jump, 1, PLAYER_SPRITE_SIZE, PLAYER_SPRITE_SIZE, Duration.seconds(0.5), 0, 0);
        animationDoubleJump = new AnimationChannel(doubleJump, 6, PLAYER_SPRITE_SIZE, PLAYER_SPRITE_SIZE, Duration.seconds(0.6), 0, 5);
        texture = new AnimatedTexture(animationIdle);
        texture.loop();
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
        physics.onGroundProperty().addListener((obs, old, isOnGround) -> {
            if (isOnGround) {
                jump = 2;
            }
        });
    }

    @Override
    public void onUpdate(double tpf) {
        if (physics.isMovingY()) {
            if (jump == 0) {
                if (texture.getAnimationChannel() != animationDoubleJump) {
                    texture.loopAnimationChannel(animationDoubleJump);
                }
            } else {
                if (texture.getAnimationChannel() != animationJump) {
                    texture.loopAnimationChannel(animationJump);
                }
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
        physics.setVelocityX(RUN_VELOCITY);
    }

    public void left() {
        entity.setScaleX(-1);
        physics.setVelocityX(-RUN_VELOCITY);
    }

    public void jump() {
        if (jump > 0) {
            physics.setVelocityY(JUMP_VELOCITY);
            jump--;
        }
    }

    public void stop() {
        physics.setVelocityX(0);
    }
}
