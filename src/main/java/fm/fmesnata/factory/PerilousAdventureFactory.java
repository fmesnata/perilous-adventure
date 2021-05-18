package fm.fmesnata.factory;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import fm.fmesnata.component.GoalComponent;
import fm.fmesnata.component.PlayerComponent;
import javafx.geometry.Point2D;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;
import static com.almasb.fxgl.physics.box2d.dynamics.BodyType.DYNAMIC;
import static fm.fmesnata.factory.EntityType.*;

public class PerilousAdventureFactory implements EntityFactory {

    @Spawns("platform")
    public Entity newPlatform(SpawnData data) {
        return entityBuilder(data)
                .type(PLATFORM)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .build();
    }

    @Spawns("spike")
    public Entity newSpike(SpawnData data) {
        return entityBuilder(data)
                .type(SPIKE)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(DYNAMIC);
        physics.setFixtureDef(new FixtureDef().friction(0));
        physics.addGroundSensor(new HitBox("GROUND_SENSOR", new Point2D(0, 0), BoundingShape.box(64, 64)));

        return entityBuilder(data)
                .type(PLAYER)
                .bbox(BoundingShape.box(64,64))
                .with(physics)
                .with(new CollidableComponent(true))
                .with(new PlayerComponent())
                .build();
    }

    @Spawns("goal")
    public Entity newGoal(SpawnData data) {
        return entityBuilder(data)
                .type(GOAL)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new GoalComponent())
                .with(new CollidableComponent(true))
                .build();
    }

}
