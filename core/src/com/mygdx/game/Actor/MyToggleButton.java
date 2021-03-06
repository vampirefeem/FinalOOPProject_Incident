package com.mygdx.game.Actor;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.Screen.GameScreen;

public class MyToggleButton extends Actor {

    private final PrimitiveSqaure shape;
    private final MyTextDisplay text;
    private Boolean selected;
    private Boolean active;

    public MyToggleButton() {
        super();
        active = false;
        selected = false;
        shape = new PrimitiveSqaure(0);
        text = new MyTextDisplay("fonts/helveticaneue/HelveticaNeue Light.ttf", 28, 1);

        addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (active) {
                    long id = GameScreen.clickSound.play(1.0f);
                    GameScreen.clickSound.setPitch(id, 5);
                    myClick();
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
        text.setPosition(getX() + getWidth() / 2, getY() + getHeight() / 2);

        if (selected) {
            shape.setDrawMode(0);
        } else {
            shape.setDrawMode(1);
        }
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

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
        if (selected) {
            changeColor(new Color(0.2f, 0.6f, 1, 1));
        } else {
            changeColor(Color.WHITE);
        }
    }

    public void changePosition(float x, float y, float d) {
        active = false;

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

        SequenceAction action = new SequenceAction();
        action.addAction(ma);
        action.addAction(ra);

        addAction(action);
    }

    private void changeColor(Color c) {
        ColorAction ca = new ColorAction();
        ca.setEndColor(c);
        ca.setDuration(0.5f);
        ca.setInterpolation(Interpolation.pow3Out);
        addAction(ca);
    }

    public void changeSize(float w, float h, Interpolation i, float t) {
        SizeToAction sa = new SizeToAction();
        sa.setSize(w, h);
        sa.setInterpolation(i);
        sa.setDuration(t);

        MoveToAction ma = new MoveToAction();
        ma.setPosition(getX(), getY() - ((h - getHeight()) / 2));
        ma.setInterpolation(i);
        ma.setDuration(t);

        ParallelAction action = new ParallelAction();
        action.addAction(sa);
        action.addAction(ma);

        addAction(action);
    }

    public void myClick() {

    }

}
