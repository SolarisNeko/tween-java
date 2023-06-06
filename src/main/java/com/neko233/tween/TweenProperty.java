package com.neko233.tween;

import java.lang.reflect.Field;

/**
 * tween 属性
 *
 * @param <T>
 */
public class TweenProperty<T extends Number> {

    private final String propertyName;
    private T startValue;
    private T endValue;

    public TweenProperty(String propertyName,
                         T startValue,
                         T endValue) {
        this.propertyName = propertyName;
        this.startValue = startValue;
        this.endValue = endValue;
    }

    public synchronized void setValue(Object target,
                                      Number interpolatedValue) {
        try {
            Object finalObj = target;
            String[] propertyNames = propertyName.split("\\.");


            if (propertyNames.length > 1) {
                Field finalField = null;
                for (int i = 0; i < propertyNames.length; i++) {
                    String name = propertyNames[i];

                    if (finalObj == null) {
                        return;
                    }
                    Class<?> targetClass = finalObj.getClass();
                    Field field = targetClass.getDeclaredField(name);
                    field.setAccessible(true);

                    if (i + 1 < propertyNames.length) {
                        finalObj = field.get(finalObj);
                    }

                    finalField = field;
                }

                finalField.setAccessible(true);
                finalField.set(finalObj, interpolatedValue);
            } else {
                Class<?> finalTargetClass = finalObj.getClass();
                String propertyNameNow = propertyNames[propertyNames.length - 1];
                Field finalField = finalTargetClass.getDeclaredField(propertyNameNow);
                finalField.setAccessible(true);
                finalField.set(finalObj, interpolatedValue);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public float getStartValue() {
        return startValue.floatValue();
    }

    public float getEndValue() {
        return endValue.floatValue();
    }

    public void reverse() {
        T temp = startValue;
        startValue = endValue;
        endValue = temp;
    }
}
