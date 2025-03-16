package net.iouhase.maldelbrot;

import javafx.fxml.FXML;
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
        double SCALE = 1;


        WritableImage writableImage = new WritableImage((int)WIDTH, (int)HEIGHT);
        PixelWriter pixelWriter = writableImage.getPixelWriter();
        image.setImage(writableImage);

        while (true){
            for (int i = 0; i < THREADS_SQRT; i++) {
                for (int j = 0; j < THREADS_SQRT; j++) {
                    double startX, startY;
                    double endX, endY;
                    startX = i * WIDTH / THREADS_SQRT;
                    startY = j * HEIGHT/ THREADS_SQRT;
                    endX = startX + WIDTH / THREADS_SQRT;
                    endY = startY + HEIGHT / THREADS_SQRT;

                    Thread thread = new Thread(new DrawThread(pixelWriter, startX, startY, endX, endY, SCALE_START_X, SCALE_START_Y, SCALE_END_X, SCALE_END_Y, SCALE));
                    thread.start();
                }
            }
            try {
                wait(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}