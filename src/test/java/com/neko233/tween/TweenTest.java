package com.neko233.tween;

import com.neko233.tween.data.Node3D;
import com.neko233.tween.data.Position;
import lombok.extern.slf4j.Slf4j;

/**
 * @author SolarisNeko
 * Date on 2023-01-02
 */
@Slf4j
public class TweenTest {

    public static void main(String[] args) throws InterruptedException {


        Node3D node3D = new Node3D();
        node3D.setPosition(Position.zero());
        node3D.setEulerAngles(0);


        Tween.tween(node3D)
                .by(2, TweenParams.of("eulerAngles", 0, 90))
                .to(1, TweenParams.of("position.x", 0, 90))
        ;

//        float deltaTime = 0.1f;
        float totalTimeSecond = 0.0f;

        long allStart = System.currentTimeMillis();
        long start = System.currentTimeMillis();
        long deltaTimeSecond = 0;

        while (System.currentTimeMillis() - allStart < 4000) {
            deltaTimeSecond = System.currentTimeMillis() - start;
            start = System.currentTimeMillis();

            float deltaTime = (float) deltaTimeSecond / 1000;
            Tween.update(deltaTime);

            totalTimeSecond += deltaTime;

            Thread.sleep(100);
            System.out.println("EulerAngles: " + node3D.getEulerAngles());
            Position position = node3D.getPosition();
            System.out.println("position.x: " + position.getX());
            log.info("past {} second", totalTimeSecond);
        }

        node3D.getEulerAngles();
    }
}