package com.example.whackamole;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends Application {
    HolePane[] holes = new HolePane[9];
    int mole;
    Timer timer;
    TimerTask timerTask;

    @Override
    public void start(Stage stage) throws IOException {
        // Initializing each HolePane in the holes array, and adding the lambda expression for when clicked
        for (int i = 0; i < holes.length; i++) {
            holes[i] = new HolePane();

            holes[i].setOnMousePressed(e -> {
                HolePane clickedHole = (HolePane) e.getSource();
                if (clickedHole.whack()) {
                    timer.cancel();
                }
            });
        }

        // Creating start and stop button and the lambda expressions for button pressed
        Button start = new Button("Start");
        Button stop = new Button("Stop");
        start.setOnAction((ActionEvent e) -> {
            if (timer != null) {
                timer.cancel();
            }
            timerTask = new MolePopper();
            timer = new Timer();
            timer.schedule(timerTask, 0, 1000);
        });
        stop.setOnAction((ActionEvent e) -> {
           timer.cancel();
        });

        // Creating the hBox with the start and stop button
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(start, stop);

        // Creating the gridPane with the holes
        GridPane grid = new GridPane();
        grid.setHgap(0);
        grid.setVgap(0);
        int currentMole = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid.add(holes[currentMole], i, j);
                currentMole++;
            }
        }

        // Creating the borderPane with the grid and hBox
        BorderPane border = new BorderPane();
        border.setBottom(hBox);
        border.setCenter(grid);

        // Creating the scene with the border
        Scene scene = new Scene(border, 561, 400);

        // Creating the stage with the scene
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    // MolePopper class which hides the current mole and pops out the mole at a new hole
    private class MolePopper extends TimerTask {
        public void run() {
            holes[mole].hide();
            mole = (int)(Math.random() * (9));
            holes[mole].popOut();
        }
    }
}

class HolePane extends StackPane {
    Image in = new Image("file:in.png");
    Image out = new Image("file:out.png");
    Image empty = new Image("file:empty.png");
    ImageView view = new ImageView();
    Text text = new Text();

    // HolePane constructor, which makes the hole empty and adds the ImageView and Text
    public HolePane() {
        view.setImage(empty);
        getChildren().addAll(view, text);
    }

    // Sets the hole to empty
    public void hide() {
        text.setText("");
        view.setImage(empty);
    }

    // Sets the hole to out
    public void popOut() {
        text.setText("");
        view.setImage(out);
    }

    // Sets the hole to in
    public void popIn() {
        text.setText("");
        view.setImage(in);
    }

    // Pops in, says Ouch, and returns true if the mole is out; else, returns false
    public boolean whack() {
        if (view.getImage() == out) {
            popIn();
            text.setText("Ouch!!");
            return true;
        }
        else {
            return false;
        }
    }
}