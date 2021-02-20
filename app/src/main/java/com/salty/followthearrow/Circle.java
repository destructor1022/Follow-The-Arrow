package com.salty.followthearrow;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class Circle {
    public ImageView image;
    public int color;
    public Arrow arrowTo;
    public Arrow onArrow;

    public Circle(ImageView image) {
        this.image = image;
    }

    public void setRandomColor(ArrayList<Circle> b) {
        boolean matchingColors = true;
        while(matchingColors == true) {
            color = Color.rgb((int) (Math.random() * 156 + 100), (int) (Math.random() * 156 + 100), (int) (Math.random() * 156 + 100));
            matchingColors = this.colorsMatch(b);
        }

        image.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
    }

    public boolean colorsMatch(ArrayList<Circle> b) {
        int howManyColorsMatch = 0;
        for(Circle a : b) {
            int aColor = Color.red(a.color) + Color.green(a.color) + Color.blue(a.color);
            int thisColor = Color.red(this.color) + Color.green(this.color) + Color.blue(this.color);

            if (Math.abs(aColor - thisColor) <= 75) {
                howManyColorsMatch += 1;
            }
        }

        if(howManyColorsMatch > 1) {
            return true;
        } else {
            return false;
        }
    }

    public void setArrowTo(Arrow i) {
        arrowTo = i;
    }

    public void setArrowTo(ArrayList<Arrow> list) {
        arrowTo = list.get((int) (Math.random() * list.size()));
    }

    public float x() {
        return image.getX();
    }

    public float y() {
        return image.getY();
    }

    public ArrayList<Float> coords() {
        ArrayList<Float> a = new ArrayList<>();
        a.add(this.x());
        a.add(this.y());

        return a;
    }

    public void moveCircle(int animationTime) {
        Path path1 = new Path();
        path1.moveTo(this.x(), this.y());
        path1.lineTo(arrowTo.x(), arrowTo.y());

        ObjectAnimator animation1 = ObjectAnimator.ofFloat(this.image, View.X, View.Y, path1);
        animation1.setDuration(animationTime);

        animation1.start();
    }

    public void updateCircle(ArrayList<Arrow> i) {
        this.onArrow = this.arrowTo;
        this.arrowTo = i.get(this.onArrow.idPointedTo());
    }

}
