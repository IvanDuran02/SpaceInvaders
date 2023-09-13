module com.invaders.spaceinvaders {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.invaders.spaceinvaders to javafx.fxml;
    exports com.invaders.spaceinvaders;
}