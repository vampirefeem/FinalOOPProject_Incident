package com.mygdx.game.Actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;

public class MyButton extends Group {

    private PrimitiveSqaure shape;
    private MyTextDisplay text;
    private Boolean active;
    private Timer timer;

    private final Sound clickSound = Gdx.audio.newSound(Gdx.files.internal("clicked.mp3"));

    public MyButton(String path, int size) {
        super();
        active = false;
        shape = new PrimitiveSqaure(0);
        text = new MyTextDisplay(path, size, 1);
        addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(active){
                    changeColor(new Color(0.2f, 0.6f, 1, 0.5f), 0.2f, Interpolation.pow3Out);
                    changeSize(525, 75, Interpolation.pow3Out, 0.2f);
                }
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if(active) {
                    changeColor(new Color(0.2f, 0.6f, 1, 0.5f), 0.5f, Interpolation.pow3Out);
                    changeSize(550, 100, Interpolation.pow3Out, 0.5f);
                }

            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if(active) {
                    changeColor(new Color(0, 0, 0, 0), 0.5f, Interpolation.pow3Out);
                    changeSize(500, 50, Interpolation.pow3Out, 0.5f);
                }
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(active) {
                    long id = clickSound.play(1.0f);
                    clickSound.setPitch(id, 3);
                    clickDelay();
                }
            }
        });

        setBounds(getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        shape.draw(batch, parentAlpha);
        text.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        shape.setPosition(getX(), getY());
        shape.setSize(getWidth(), getHeight());
        shape.setColor(getColor());
        text.setPosition(getX() + getWidth()/2, getY()+getHeight()/2);
    }

    @Override
    public boolean remove() {
        shape.remove();
        text.remove();
        return super.remove();
    }

    public PrimitiveSqaure getShape() {
        return shape;
    }

    public MyTextDisplay getText() {
        return text;
    }

    public void changeColor(Color c, float d){
        ColorAction ca = new ColorAction();
        ca.setEndColor(c);
        ca.setDuration(d);
        ca.setInterpolation(Interpolation.pow3);
        addAction(ca);
    }
    public void changeColor(Color c, float d, Interpolation t){
        ColorAction ca = new ColorAction();
        ca.setEndColor(c);
        ca.setDuration(d);
        ca.setInterpolation(t);
        addAction(ca);
    }
    public void changeSize(float w, float h, Interpolation i, float t){
        SizeToAction sa = new SizeToAction();
        sa.setSize(w, h);
        sa.setInterpolation(i);
        sa.setDuration(t);

        MoveToAction ma = new MoveToAction();
        ma.setPosition(getX(), getY()-((h-getHeight())/2));
        ma.setInterpolation(i);
        ma.setDuration(t);

        ParallelAction action = new ParallelAction();
        action.addAction(sa);
        action.addAction(ma);

        addAction(action);
    }

    public void clickDelay(){
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                myClick();
            }
        }, 0.3f);
    }
    public void myClick(){
        clearActions();
    }

    public void changePosition(float x, float y, float d){
        active = false;

        changeColor(new Color(0, 0, 0, 0), 0.3f, Interpolation.pow3Out);
        changeSize(500, 50, Interpolation.pow3Out, 0.3f);

        MoveToAction ma = new MoveToAction();
        ma.setPosition(x, y);
        ma.setInterpolation(Interpolation.pow3);
        ma.setDuration(d);

        RunnableAction ra = new RunnableAction();
        ra.setRunnable(new Timer.Task() {
            @Override
            public void run() {
                active = true;
            }
        });

        final SequenceAction action = new SequenceAction();
        action.addAction(ma);
        action.addAction(ra);

        timer = new Timer();
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                addAction(action);
            }
        }, 0.3f);
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getActive() {
        return active;
    }
}
