package com.neko233.tween.data;

import lombok.Data;

// 创建一个简单的类，包含一个float类型的属性
@Data
public class Node3D {

    private float eulerAngles;
    private Position position;

    public float getEulerAngles() {
        return eulerAngles;
    }

    public void setEulerAngles(float eulerAngles) {
        this.eulerAngles = eulerAngles;
    }

}