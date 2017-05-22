package com.rpsparty.game.view.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;

/**
 * Created by afonso on 22/05/2017.
 */

public class PaperGameActor extends Actor {
    Texture texture = new Texture(Gdx.files.internal("Achieve.png"));
    float actorX = 0, actorY = 0;
    public boolean started = false;

    public PaperGameActor(){
        setBounds(actorX,actorY,200,200);
        addListener(new ActorGestureListener(){
            public void fling(InputEvent event, float velocityX, float velocityY, int button) {
                if (velocityY < 0)
                    System.out.println("SAD");
            }
            public void tap(InputEvent event, float x, float y, int count, int button){
                System.out.println("Why");
            }
        });
    }

    @Override
    public void draw(Batch batch, float alpha){
        batch.draw(texture,actorX,actorY);
    }
}