package com.salty.followthearrow;

import android.graphics.PorterDuff;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;

public class Arrow {
    public ImageView image;
    public int idx;
    public String name;
    public int pointedToId;
    public Circle connectedCircle;
    private HashMap<Integer, ph> directionMap;

    private class ph {
        float rotValue;
        int idTo;
        String nameTo;

        public ph() {

        }

        public ph(float rotValue, int idTo, String nameTo) {
            this.rotValue = rotValue;
            this.idTo = idTo;
            this.nameTo = nameTo;
        }
    }

    public Arrow(int idx, ImageView image) {
        directionMap = new HashMap<>();
        this.image = image;

        this.idx = idx;

        ph ph0;
        ph ph1;
        ph ph2;

        if(idx == 0) {
            this.name = "right";
            ph0 = new ph(0f, 2, "left");
            ph1 = new ph(270f + 45f, 3, "down");
            ph2 = new ph(0f + 45f, 1, "up");
        } else if(idx == 1) {
            this.name = "up";
            ph0 = new ph(270f + 45f, 2, "left");
            ph1 = new ph(270f, 3, "down");
            ph2 = new ph(180f + 45f, 0, "right");
        } else if(idx == 2) {
            this.name = "left";
            ph0 = new ph(180f, 0, "right");
            ph1 = new ph(180f + 45f, 3, "down");
            ph2 = new ph(90f + 45f, 1, "up");
        } else if(idx == 3) {
            this.name = "down";
            ph0 = new ph(90f + 45f, 0, "right");
            ph1 = new ph(0f + 45f, 2, "left");
            ph2 = new ph(90f, 1, "up");
        } else {
            ph0 = new ph();
            ph1 = new ph();
            ph2 = new ph();
        }

        directionMap.put(0, ph0);
        directionMap.put(1, ph1);
        directionMap.put(2, ph2);
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

    public void setRotation() {
        pointedToId = (int) (Math.random() * 3);
        image.setRotation(getRotation());
    }

    public void setRotation(int direction) {
        pointedToId = direction;
        image.setRotation(getRotation());
    }

    public float getRotation() {
        return directionMap.get(pointedToId).rotValue;
    }

    public int idPointedTo() {
        return directionMap.get(pointedToId).idTo;
    }

    public void setImageColor() {
        image.setColorFilter(connectedCircle.color, PorterDuff.Mode.MULTIPLY);
    }

    public void setConnectedCircle(ArrayList<Circle> list) {
        connectedCircle = list.get((int) (Math.random() * list.size()));
    }

    public void setConnectedCircle(Circle a) {
        connectedCircle = a;
    }

}
