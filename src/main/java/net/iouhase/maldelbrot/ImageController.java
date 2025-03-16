package net.iouhase.maldelbrot;

import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class ImageController {
    @FXML
    private ImageView image;

    @FXML
    private void initialize() {
        final int THREADS_SQRT = 1;
        final double WIDTH = 1920;
        final double HEIGHT = 1080;
        final double SCALE_START_X = -2.5;
        final double SCALE_END_X = 1.5;
        final double SCALE_START_Y = -2.0;
        final double SCALE_END_Y = 2.0;
        double scale = 1;

        WritableImage writableImage = new WritableImage((int)WIDTH, (int)HEIGHT);
        PixelWriter pixelWriter = writableImage.getPixelWriter();
        image.setImage(writableImage);

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


                Thread thread = new Thread(new DrawThread(pixelWriter, startX, startY, endX, endY, threadScaleStartX, threadScaleStartY, threadScaleEndX, threadScaleEndY));
                thread.start();
            }
        }
    }
}