package com.neko233.tween;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * Tween 行为链路
 */
@Slf4j
@Getter
public class TweenAction {
    private final Object target;
    private List<TweenProperty<?>> properties = new ArrayList<>();
    private float durationSecond;
    private boolean repeatForever;
    private int repeatCount;
    private boolean isYoyo;
    private float delaySecond;
    private boolean isComplete = false;
    private boolean isAddToRemove = false;
    private Runnable onComplete;

    private float elapsedSecond;
    private boolean isReverse;
    private int completedCycles;

    private TweenAction(Object target) {
        this.target = target;
    }

    public static TweenAction create(Object target) {
        return new TweenAction(target);
    }

    public TweenAction by(float durationSecond,
                          TweenParams params) {
        this.durationSecond = durationSecond;
        this.properties.addAll(params.getTweenPropertyList());
        this.repeatForever = params.isRepeatForever();
        this.repeatCount = params.getRepeatCount();
        this.isYoyo = params.isYoyo();
        this.delaySecond = params.getDelaySecond();
        this.onComplete = params.getOnComplete();
        Tween.addAction(this);
        return this;
    }

    public TweenAction to(float durationSecond,
                          TweenParams params) {
        if (isYoyo && isReverse) {
            reverseProperties();
        }
        TweenAction tweenAction = new TweenAction(target);
        tweenAction.durationSecond = durationSecond;
        tweenAction.properties.addAll(params.getTweenPropertyList());
        tweenAction.repeatForever = params.isRepeatForever();
        tweenAction.repeatCount = params.getRepeatCount();
        tweenAction.isYoyo = params.isYoyo();
        tweenAction.delaySecond = params.getDelaySecond();
        tweenAction.onComplete = params.getOnComplete();
        Tween.addAction(tweenAction);
        return tweenAction;
    }

    public TweenAction call(Runnable runnable) {
        TweenAction tweenAction = new TweenAction(null);
        tweenAction.setCallback(runnable);
        Tween.addAction(tweenAction);
        return this;
    }

    public void update(float deltaTimeSecond) {
        log.debug("deltaTimeSecond = {}", deltaTimeSecond);

        if (delaySecond > 0) {
            delaySecond -= deltaTimeSecond;
            return;
        }

        elapsedSecond += deltaTimeSecond;
        float normalizedTime = Math.min(elapsedSecond / durationSecond, 1.0f);
        if (isYoyo && isReverse) {
            normalizedTime = 1.0f - normalizedTime;
        }

        for (TweenProperty<?> property : properties) {
            float startValue = property.getStartValue();
            float endValue = property.getEndValue();
            float interpolatedValue = startValue + (endValue - startValue) * normalizedTime;
            property.setValue(target, interpolatedValue);
        }

        if (elapsedSecond >= durationSecond) {
            if (isYoyo) {
                isReverse = !isReverse;
            }

            completedCycles++;
            if (repeatForever || completedCycles < repeatCount) {
                elapsedSecond = 0;
                if (isYoyo && isReverse) {
                    reverseProperties();
                }
            } else {
                isComplete = true;
                if (onComplete != null) {
                    onComplete.run();
                }
            }
        }
    }


    private void reverseProperties() {
        for (TweenProperty<?> property : properties) {
            property.reverse();
        }
    }

    private void setCallback(Runnable callback) {
        this.onComplete = callback;
    }

    public void setAddToRemove(boolean addToRemove) {
        isAddToRemove = addToRemove;
    }
}