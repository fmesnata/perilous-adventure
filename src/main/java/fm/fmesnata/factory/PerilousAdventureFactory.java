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
import fm.fmesnata.component.RockHeadComponent;
import javafx.geometry.Point2D;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;
import static com.almasb.fxgl.physics.box2d.dynamics.BodyType.*;
import static fm.fmesnata.factory.EntityType.*;

public class PerilousAdventureFactory implements EntityFactory {

    @Spawns("platform")
    public Entity newPlatform(SpawnData data) {
        return entityBuilder(data)
                .type(PLATFORM)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
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

    @Spawns("rockHead")
    public Entity newRockHead(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(KINEMATIC);
        physics.addGroundSensor(new HitBox("GROUND_SENSOR_RO", new Point2D(10, 10), BoundingShape.box(84, 94)));
        return entityBuilder(data)
                .type(ROCK_HEAD)
                .bbox(new HitBox(new Point2D(10, 10), BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(physics)
                .with(new RockHeadComponent())
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(DYNAMIC);
        physics.setFixtureDef(new FixtureDef().friction(0));
        physics.addGroundSensor(new HitBox("GROUND_SENSOR", new Point2D(10, 5), BoundingShape.box(54, 59)));

        return entityBuilder(data)
                .type(PLAYER)
                .bbox(new HitBox(new Point2D(10,5),BoundingShape.box(44,59)))
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

    @Spawns("blockHeadDetector")
    public Entity newBlockHeadDetector(SpawnData data) {
        return entityBuilder(data)
                .type(ROCK_HEAD_DETECTOR)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .build();
    }
}
