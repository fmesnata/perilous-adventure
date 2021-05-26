package fm.fmesnata.component;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.scene.image.Image;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.image;

public class RockHeadComponent extends Component {

    private PhysicsComponent physics;
    private final AnimatedTexture texture;
    public boolean onGround = false;
    public RockHeadComponent() {
        Image idle = image("rock_head_idle.png");
        AnimationChannel animationIdle = new AnimationChannel(idle, 1, 84, 84, Duration.seconds(0.6), 0, 0);
        texture = new AnimatedTexture(animationIdle);
        texture.loop();
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
//        physics.onGroundProperty().addListener((obs, old, isOnGround) -> {
//            if (isOnGround) {
//            }
//        });
    }

    @Override
    public void onUpdate(double tpf) {
    }

    public void fall() {
        physics.setVelocityY(300);
    }

    public void stop() {
        physics.setVelocityY(0);
        onGround = true;
    }
}
