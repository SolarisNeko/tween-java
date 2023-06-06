package com.neko233.tween;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SolarisNeko
 * Date on 2023-01-02
 */
public class Tween {
    private static List<TweenAction> tweenActionList = new ArrayList<>();

    public static void update(float deltaTimeSecond) {
        List<TweenAction> actionsToRemove = new ArrayList<>();

        for (TweenAction action : tweenActionList) {

            if (action.isComplete()) {
                if (action.isAddToRemove()) {
                    continue;
                }
                actionsToRemove.add(action);
            }
            // 排他
            action.update(deltaTimeSecond);
            break;
        }
        tweenActionList.removeAll(actionsToRemove);
    }

    public static TweenAction tween(Object target) {
        return TweenAction.create(target);
    }

    public static void addAction(TweenAction action) {
        tweenActionList.add(action);
    }
}