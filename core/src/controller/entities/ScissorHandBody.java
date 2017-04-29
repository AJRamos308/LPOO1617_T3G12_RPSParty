package controller.entities;

import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import model.entities.PaperHandModel;
import model.entities.RockHandModel;
import model.entities.ScissorHandModel;

/**
 * Created by bibib on 29/04/2017.
 */

public class ScissorHandBody extends EntityBody{
    public ScissorHandBody(World world, ScissorHandModel model) {
        super(world, model);
        float density = 0.7f, friction = 0.4f, restitution = 0.3f;
        int width = 75, height = 75;
        PolygonShape shape =new PolygonShape();
        shape.setAsBox(width/2,height/2);

        createFixture(shape, width, height, density, friction, restitution, SCISSOR_BODY, (short) (ROCK_BODY | PAPER_BODY | SCISSOR_BODY));
    }
}
