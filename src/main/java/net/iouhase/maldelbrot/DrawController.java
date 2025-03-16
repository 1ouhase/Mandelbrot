package net.iouhase.maldelbrot;

import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class DrawController implements Runnable {
    final int THREADS_SQRT = 1;
    final double WIDTH = 1280;
    final double HEIGHT = 720;
    final double SCALE_START_X = -2.0;
    final double SCALE_END_X = 2;
    final double SCALE_START_Y = -2.0;
    final double SCALE_END_Y = 2.0;
    double scale = 1;
    double xCenter, yCenter;
    PixelWriter writer;
    WritableImage writableImage;
    ImageView image;
    public boolean running = true;

    public DrawController(ImageView image) {
        this.image = image;
        this.writableImage = new WritableImage((int)WIDTH, (int)HEIGHT);
        image.setImage(writableImage);
        this.writer = writableImage.getPixelWriter();
    }

    @Override
    public void run() {
        while(running) {
            draw();

            scale += 0.1;

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void draw() {
        for (int i = 0; i < THREADS_SQRT; i++) {
            for (int j = 0; j < THREADS_SQRT; j++) {
                double startX, startY;
                double endX, endY;
                double threadScaleStartX, threadScaleStartY;
                double threadScaleEndX, threadScaleEndY;
                double scaleStartXTemp, scaleStartYTemp;
                double scaleEndXTemp, scaleEndYTemp;
                scaleStartXTemp = SCALE_START_X / scale;
                scaleStartYTemp = SCALE_START_Y / scale;
                scaleEndXTemp = SCALE_END_X / scale;
                scaleEndYTemp = SCALE_END_Y / scale;
                startX = i * WIDTH / THREADS_SQRT;
                startY = j * HEIGHT/ THREADS_SQRT;
                endX = startX + WIDTH / THREADS_SQRT;
                endY = startY + HEIGHT / THREADS_SQRT;
                threadScaleStartX = scaleStartXTemp + ((scaleEndXTemp - scaleStartXTemp) / THREADS_SQRT) * i;
                threadScaleStartY = scaleStartYTemp + ((scaleEndYTemp - scaleStartYTemp) / THREADS_SQRT) * j;
                threadScaleEndX = threadScaleStartX + (Math.abs(scaleEndXTemp - scaleStartXTemp) / THREADS_SQRT);
                threadScaleEndY = threadScaleStartY + Math.abs(scaleEndYTemp - scaleStartYTemp) / THREADS_SQRT;


                Thread thread = new Thread(new DrawThread(writer, startX, startY, endX, endY, threadScaleStartX, threadScaleStartY, threadScaleEndX, threadScaleEndY));
                thread.start();
            }
        }
    }
}
