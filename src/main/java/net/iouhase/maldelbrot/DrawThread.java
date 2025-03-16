package net.iouhase.maldelbrot;

import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

public class DrawThread implements Runnable {
    final PixelWriter writer;
    double startX, startY;
    double endX, endY;
    double rangeStartX, rangeStartY;
    double rangeEndX, rangeEndY;

    public DrawThread(PixelWriter writer, double startX, double startY, double endX, double endY, double rangeStartX, double rangeStartY, double rangeEndX, double rangeEndY, double scale) {
        this.writer = writer;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.rangeStartX = rangeStartX / scale;
        this.rangeStartY = rangeStartY / scale;
        this.rangeEndX = rangeEndX / scale;
        this.rangeEndY = rangeEndY / scale;
    }

    @Override
    public void run() {

        for (double x = startX; x < endX; x++) {
            for (double y = startY; y < endY; y++) {
                double a = rangeStartX + ((rangeEndX - rangeStartX) / (endX - startX)) * (x - startX);
                double b = rangeStartY + ((rangeEndY - rangeStartY) / (endY - startY)) * (y - startY);

                double brightness = calcBrightness(a,b);

                synchronized (writer) {
                    System.out.println(brightness);
                    writer.setColor((int) x, (int) y, new Color(brightness, brightness, brightness, 1));
                }
            }
        }
    }

    private double calcBrightness(double a, double b){
        double n = 0;

        double originalA = a;
        double originalB = b;

        while (n < 100){
            double aa = a*a - b*b;
            double bb = 2 * a * b;

            a = aa + originalA;
            b = bb + originalB;

            if(Math.abs(a + b) > 16){
                break;
            }

            n++;
        }

        return n / 100;
    }
}

