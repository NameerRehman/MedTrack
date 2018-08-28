package com.nrinfinity.nameer.medtrack;

import android.content.Context;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class Fx {
    public static void slide_down(Context ctx, View v){
        Animation a = AnimationUtils.loadAnimation(ctx, R.anim.slide_down);
        if(a!=null){
            a.reset();
            if(v != null){
                v.clearAnimation();
                v.startAnimation(a);
            }
        }
    }

    public static void slide_up(Context ctx, View v){
        Animation b = AnimationUtils.loadAnimation(ctx, R.anim.slide_up);
        b.setInterpolator(new AccelerateInterpolator());
        if(b!=null){
            b.reset();
            if(v != null){
                v.clearAnimation();
                v.startAnimation(b);
            }
        }
    }
}