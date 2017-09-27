package com.hencoder.hencoderpracticedraw7.practice;

import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.hencoder.hencoderpracticedraw7.R;

public class Practice04PropertyValuesHolderLayout extends RelativeLayout {
    View view;
    Button animateBt;

    public Practice04PropertyValuesHolderLayout(Context context) {
        super(context);
    }

    public Practice04PropertyValuesHolderLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice04PropertyValuesHolderLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        view = findViewById(R.id.objectAnimatorView);
        animateBt = (Button) findViewById(R.id.animateBt);

        animateBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                // 使用 PropertyValuesHolder.ofFloat() 来创建不同属性的动画值方案
                // 第一个： scaleX 从 0 到 1
                // 第二个： scaleY 从 0 到 1
                // 第三个： alpha 从 0 到 1

                // 然后，用 ObjectAnimator.ofPropertyValuesHolder() 把三个属性合并，创建 Animator 然后执行
            }
        });
    }

    private void implByViewPropertyAnimator(){
        view.setScaleX(0);
        view.setScaleY(0);
        view.setAlpha(0);
        view.animate().scaleX(1).scaleY(1).alpha(1);
    }


    private void implByObjectAnimator() {
        PropertyValuesHolder scaleXHolder = PropertyValuesHolder.ofFloat("scaleX", 0, 1);
        PropertyValuesHolder scaleYHolder = PropertyValuesHolder.ofFloat("scaleY", 0, 1);
        PropertyValuesHolder alphaHolder = PropertyValuesHolder.ofFloat("alpha", 0, 1);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(view, scaleXHolder, scaleYHolder, alphaHolder);
        animator.start();
    }

    private void implByValueAnimator() {
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new MyTypeEvaluator(), new MyType(0, 0, 0), new MyType(1, 1, 1));
        valueAnimator.setDuration(5000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (animation.getAnimatedValue() instanceof MyType) {
                    MyType value = (MyType) animation.getAnimatedValue();
                    view.setScaleX(value.scaleX);
                    view.setScaleY(value.scaleY);
                    view.setAlpha(value.alpha);
                }

            }
        });
        valueAnimator.start();


    }

    class MyTypeEvaluator implements TypeEvaluator<MyType> {

        @Override
        public MyType evaluate(float fraction, MyType startValue, MyType endValue) {
            return new MyType(
                    (endValue.scaleX - startValue.scaleX) * fraction,
                    fraction * (endValue.scaleY - startValue.scaleY),
                    (endValue.alpha - startValue.alpha) * fraction
            );
        }
    }

    class MyType {
        float scaleX;
        float scaleY;
        float alpha;

        MyType(float scaleX, float scaleY, float alpha) {
            this.scaleX = scaleX;
            this.scaleY = scaleY;
            this.alpha = alpha;
        }
    }
}
