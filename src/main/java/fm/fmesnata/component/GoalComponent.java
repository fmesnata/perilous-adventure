package fm.fmesnata.component;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.scene.image.Image;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.image;

public class GoalComponent extends Component {

    private PhysicsComponent physics;
    private final AnimatedTexture texture;

    public GoalComponent() {
        Image idle = image("terrain_goal.png");
        AnimationChannel animationIdle = new AnimationChannel(idle, 10, 160, 160, Duration.seconds(0.6), 0, 9);
        texture = new AnimatedTexture(animationIdle);
        texture.loop();
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
    }
}
