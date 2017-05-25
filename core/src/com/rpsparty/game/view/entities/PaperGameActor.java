package com.rpsparty.game.view.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.rpsparty.game.controller.PaperGameController;
import com.rpsparty.game.view.PaperGameScreen;

import static com.badlogic.gdx.scenes.scene2d.ui.Table.Debug.actor;

/**
 * Created by afonso on 22/05/2017.
 */

public class PaperGameActor extends Actor {
    Texture texture = new Texture(Gdx.files.internal("Achieve.png"));
    public float toiletPaperLength = 0;
    public boolean started = false;
    private float timeToNextFrame = 0.1f;
    private float totalVelocity = 0;
    private float deltaTime = 0;
    private float flingTime = 0;
    private boolean touching = false;

    public PaperGameActor(){
        setBounds(5*Gdx.graphics.getWidth()/16,4*Gdx.graphics.getHeight()/8,2*Gdx.graphics.getWidth()/8,2*Gdx.graphics.getWidth()/8);
        addListener(new ActorGestureListener(){
            public void fling(InputEvent event, float velocityX, float velocityY, int button) {
                totalVelocity -= velocityY;
                timeToNextFrame = (684/15)/totalVelocity;
                PaperGameController.getInstance().setDistance(-velocityY*flingTime);
                flingTime = 0;
            }

        });
        addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                touching = true;
                flingTime += deltaTime;
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                touching = false;
            }
        });

    }

    public void saveTimeToNextFrame() { PaperGameController.getInstance().setTimeToNextFrame(timeToNextFrame); }

    @Override
    public void draw(Batch batch, float alpha){
        batch.draw(texture,5*Gdx.graphics.getWidth()/16,4*Gdx.graphics.getHeight()/8);
    }

    @Override
    public void act(float delta) {
        if(!touching) {//ir reduzindo a velocidade de transicao entre frames enquanto nao ha fling; aumentamos o timeToNextFrame
            totalVelocity = totalVelocity > 0 ? totalVelocity - (50+ totalVelocity/90) : 0;//a cada segundo reduz 1/3 da velocidade total
            if(totalVelocity <= 0) { //rolo de papel parado apos esta ultima atualizacao
                totalVelocity = 0;
                timeToNextFrame = 0;
            } else {
                timeToNextFrame = (684/15)/totalVelocity;
            }
        }
        saveTimeToNextFrame();
        deltaTime = delta;
    }
}