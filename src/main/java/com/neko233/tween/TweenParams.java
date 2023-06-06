package com.neko233.tween;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * Tween 调用参数
 */
@Getter
@ToString
public class TweenParams {

    private final List<TweenProperty<?>> tweenPropertyList = new ArrayList<>();
    private boolean repeatForever;
    private int repeatCount;
    private boolean yoyo;
    private float delaySecond;
    private Runnable onComplete;


    public static TweenParams of(String propertyName,
                                 Number startValue,
                                 Number endValue) {
        return new TweenParams().addTweenProperty(propertyName, startValue, endValue);
    }

    public TweenParams addTweenProperty(String propertyName,
                                        Number startValue,
                                        Number endValue) {
        TweenProperty<Number> tweenProperty = new TweenProperty<>(propertyName, startValue, endValue);
        tweenPropertyList.add(tweenProperty);
        return this;
    }

    public TweenParams setRepeatForever() {
        this.repeatForever = true;
        return this;
    }

    public TweenParams setRepeatCount(int repeatCount) {
        this.repeatCount = repeatCount;
        return this;
    }

    public TweenParams setYoyo() {
        this.yoyo = true;
        return this;
    }

    public TweenParams setDelaySecond(float delaySecond) {
        this.delaySecond = delaySecond;
        return this;
    }

    public TweenParams setOnComplete(Runnable onComplete) {
        this.onComplete = onComplete;
        return this;
    }
}