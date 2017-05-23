package com.rpsparty.game.view.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.rpsparty.game.controller.PaperGameController;
import com.rpsparty.game.view.PaperGameScreen;

/**
 * Created by afonso on 22/05/2017.
 */

public class PaperGameActor extends Actor {
    Texture texture = new Texture(Gdx.files.internal("Achieve.png"));
    public float toiletPaperLength = 0;
    public boolean started = false;

    public PaperGameActor(){
        setBounds(5*Gdx.graphics.getWidth()/16,4*Gdx.graphics.getHeight()/8,2*Gdx.graphics.getWidth()/8,2*Gdx.graphics.getWidth()/8);
        addListener(new ActorGestureListener(){
            public void fling(InputEvent event, float velocityX, float velocityY, int button) {
                if (velocityY < 0) {
                    //toiletPaperLength += velocityY;
                    PaperGameController.getInstance().update(velocityY);
                    started = true;
                }
                /*if (toiletPaperLength < 0)
                    ended = true;*/

            }
            public void tap(InputEvent event, float x, float y, int count, int button){
                System.out.println("Why");
            }
        });
    }

    @Override
    public void draw(Batch batch, float alpha){
        batch.draw(texture,5*Gdx.graphics.getWidth()/16,4*Gdx.graphics.getHeight()/8);
    }
}