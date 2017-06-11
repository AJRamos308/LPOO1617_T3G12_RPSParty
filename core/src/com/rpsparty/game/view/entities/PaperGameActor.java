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


public class PaperGameActor extends Actor {
    Texture texture = new Texture(Gdx.files.internal("paperRoll.png"));
    private float timeToNextFrame = 0.1f;
    private float totalVelocity = 0;
    private float deltaTime = 0;
    private float flingTime = 0;
    private boolean touching = false;

    /**
     * class constructor
     */
    public PaperGameActor(){
        float width = texture.getWidth();
        float height = texture.getHeight();
        setBounds(Gdx.graphics.getWidth()/16,0,Gdx.graphics.getWidth()-Gdx.graphics.getWidth()/8,Gdx.graphics.getHeight()-Gdx.graphics.getHeight()/8);
        addListener(new ActorGestureListener(){
            public void fling(InputEvent event, float velocityX, float velocityY, int button) {
                if(velocityY < 0) {
                    totalVelocity -= velocityY;
                    timeToNextFrame = (684 / 15) / totalVelocity;
                    PaperGameController.getInstance().setDistance(-velocityY * flingTime);
                    flingTime = 0;
                }
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

    /**
     * gets time for the next animation's frame
     */
    public void saveTimeToNextFrame() { PaperGameController.getInstance().setTimeToNextFrame(timeToNextFrame); }

    @Override
    /**
     * draw toilet paper
     */
    public void draw(Batch batch, float alpha){
        batch.draw(texture,Gdx.graphics.getWidth()/16,0,Gdx.graphics.getWidth()-Gdx.graphics.getWidth()/8,Gdx.graphics.getHeight()-Gdx.graphics.getHeight()/8);
    }

    @Override
    /**
     * toilet paper move
     */
    public void act(float delta) {
        if(!touching) {//ir reduzindo a velocidade de transicao entre frames enquanto nao ha fling; aumentamos o timeToNextFrame
            PaperGameController.getInstance().setDistance(totalVelocity * delta);
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