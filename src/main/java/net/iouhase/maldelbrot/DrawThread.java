package net.iouhase.maldelbrot;

import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

public class DrawThread implements Runnable {
    final PixelWriter writer;
    double startX, startY;
    double endX, endY;
    double rangeStartX, rangeStartY;
    double rangeEndX, rangeEndY;
    double maxIterations;

    public DrawThread(PixelWriter writer, double startX, double startY, double endX, double endY, double rangeStartX, double rangeStartY, double rangeEndX, double rangeEndY) {
        this.writer = writer;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.rangeStartX = rangeStartX;
        this.rangeStartY = rangeStartY;
        this.rangeEndX = rangeEndX;
        this.rangeEndY = rangeEndY;
        this.maxIterations = 50;
    }

    @Override
    public void run() {

        for (double x = startX; x < endX; x++) {
            for (double y = startY; y < endY; y++) {
                synchronized (writer) {
                    double a = rangeStartX + ((rangeEndX - rangeStartX) / (endX - startX)) * (x - startX);
                    double b = rangeStartY + ((rangeEndY - rangeStartY) / (endY - startY)) * (y - startY);

                    double brightness = calcBrightness(a,b);

                    writer.setColor((int) x, (int) y, new Color(brightness, brightness * 5 % 1, brightness * 10 % 1, 1));
                }
            }
        }
    }

    private double calcBrightness(double a, double b){
        double n = 0;

        double originalA = a;
        double originalB = b;

        while (n < maxIterations) {
            double aa = a*a - b*b;
            double bb = 2 * a * b;

            a = aa + originalA;
            b = bb + originalB;

            if(Math.abs(a + b) > 8){
                break;
            }
            n++;
        }
        if(n >= maxIterations){
            return 0;
        }
        return n / maxIterations;
    }
}

