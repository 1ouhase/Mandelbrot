package net.iouhase.maldelbrot;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class ImageController {
    @FXML
    private ImageView image;

    @FXML
    private void initialize() {
        Thread drawController = new Thread(new DrawController(image));
        drawController.start();
    }
}