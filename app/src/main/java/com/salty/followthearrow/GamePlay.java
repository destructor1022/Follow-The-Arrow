package com.salty.followthearrow;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.wearable.activity.WearableActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

public class GamePlay extends WearableActivity {
    public static final String EXTRA_MESSAGE = "0";

    public vart vars = new vart();

    public boolean satisfied;

    public class vart {
        boolean keepGoing = true;

        ArrayList<Circle> circles = new ArrayList<>();
        ArrayList<Arrow> arrows = new ArrayList<>();
        int animationTime = 3000;
        int currentMoving = 0;
        int score = 0;

        public void incrementMoving() {
            currentMoving += 1;
            if(currentMoving >= circles.size()) {
                currentMoving = 0;
            }
        }

        public void reduceAniTime() {
            if(animationTime > 450) {
                animationTime = (animationTime * 14) / 15;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        setAmbientEnabled();
    }

    @Override
    protected void onStart() {
        super.onStart();

        ConstraintLayout root = findViewById(R.id.root);


        root.post( new Runnable() {
            @Override
            public void run() {
                start();
            }
        });

        root.postDelayed( new Runnable() {
            @Override
            public void run() {
                move();
            }
        }, 500);

    }

    protected void onPause() {
        super.onPause();
        finish();
    }


    public void start() {
        vars.circles.add(new Circle((ImageView) findViewById(R.id.circle1)));
        vars.circles.add(new Circle((ImageView) findViewById(R.id.circle2)));

        vars.arrows.add(new Arrow(0, (ImageView) findViewById(R.id.arrow0)));
        vars.arrows.add(new Arrow(1, (ImageView) findViewById(R.id.arrow1)));
        vars.arrows.add(new Arrow(2, (ImageView) findViewById(R.id.arrow2)));
        vars.arrows.add(new Arrow(3, (ImageView) findViewById(R.id.arrow3)));


        for(Circle a : vars.circles) {
            a.setRandomColor(vars.circles);
            a.setArrowTo(vars.arrows);
        }


        for(Arrow a : vars.arrows) {
            a.setRotation();
            a.setConnectedCircle(vars.circles);
            a.setImageColor();
        }
    }

    public void move() {
        if(vars.keepGoing == true) {

            final Circle currentCircle = vars.circles.get(vars.currentMoving);

            try {
                currentCircle.onArrow.setConnectedCircle(currentCircle);
                currentCircle.onArrow.setImageColor();
            } catch (Exception ignored) {

            }

            currentCircle.moveCircle(vars.animationTime);
            currentCircle.onArrow = null;

            if(vars.keepGoing == true) {

                final Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public void run() {
                        if (vars.keepGoing == true) {

                            currentCircle.updateCircle(vars.arrows);
                            currentCircle.onArrow.setRotation();

                            vars.reduceAniTime();
                            vars.incrementMoving();


                            if (currentCircle.onArrow.connectedCircle == currentCircle) {
                                satisfied = true;
                            } else {
                                satisfied = false;
                            }

                            ImageView touch = (ImageView) findViewById(R.id.touch);
                            touch.setOnTouchListener(new View.OnTouchListener() {
                                @Override
                                public boolean onTouch(View view, MotionEvent motionEvent) {
                                    satisfied = !satisfied;

                                    return false;
                                }
                            });

                            final Handler handler = new Handler(Looper.getMainLooper());
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (satisfied == true) {
                                        end();
                                    }
                                }
                            }, vars.animationTime);

                            vars.score += 1;
                            ((TextView) findViewById(R.id.score)).setText(Integer.toString(vars.score));

                            if (vars.score % 5 == 0) {
                                changeColor();
                            }

                            if (vars.keepGoing == true) {
                                move();
                            }
                        }
                    }
                }, vars.animationTime);
            }

        }
    }


    public void end() {
        vars.keepGoing = false;

        Intent intent = new Intent(GamePlay.this, MainActivity.class);
        String message = String.valueOf(vars.score);
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);

        GamePlay.this.finish();
    }

    public void changeColor() {
        final Circle currentCircle = vars.circles.get(vars.currentMoving);

        currentCircle.setRandomColor(vars.circles);

        for(Arrow arrow : vars.arrows) {
            arrow.setImageColor();
        }
    }


}