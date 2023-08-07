package com.example.demo2;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CardGame extends Application {


    private Image cardBack = new Image(getClass().getResourceAsStream("blue.jpg"));
    private Image cardFront1 = new Image(getClass().getResourceAsStream("apple.jpg"));
    private Image cardFront2 = new Image(getClass().getResourceAsStream("lion.jpg"));
    private Image cardFront3 = new Image(getClass().getResourceAsStream("banana.jpg"));
    private Image cardFront4 = new Image(getClass().getResourceAsStream("rabbit.jpg"));


    private boolean[] cardState = {false, false, false, false, false, false, false, false};
    int[] card = {0, 0, 1, 1, 2, 2, 3, 3};
    Image[] cardf={cardFront1,cardFront2, cardFront3, cardFront4};
    int count=0;
    private int delayedCardIndex = -1;


    @Override
    public void start(Stage primaryStage) {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        //gridPane.setPrefHeight(400);
        //gridPane.setPrefWidth(400);

        Button[] buttons = new Button[8];
        Card(card);
        for (int i = 0; i < 8; i++) {
            final int index = i;
            buttons[i] = new Button();
            buttons[i].setOnAction(event -> {
                flipCard(index,buttons);
            });
            buttons[i].setGraphic(new ImageView(cardf[card[i]]));
            buttons[i].setPrefSize(60, 60);
            //buttons[i].setMinSize(120, 120);
            gridPane.add(buttons[i], i % 2, i / 2);
        }
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1500), e -> {
            for (int i = 0; i < 8; i++) {
                buttons[i].setGraphic(new ImageView(cardBack));
                //System.out.println(i+"cardFront2");
            }
        }));
        timeline.play();
        timeline.setOnFinished(e -> delayedCardIndex = -1);


        Scene scene = new Scene(gridPane);
        primaryStage.setTitle("Flip Card Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    int clickcard;
    private void flipCard(int index,Button[] buttons) {
        count++;
        if(count==1){
            if (cardState[index]) {
                cardState[index] = false;
            } else {
                cardState[index] = true;
            }
            clickcard=index;
            buttons[index].setGraphic(new ImageView(cardf[card[index]]));
        }else{
            if(card[index]==card[clickcard]){
                cardState[index] = true;
                cardState[clickcard] = true;
                buttons[index].setGraphic(new ImageView(cardf[card[index]]));
                buttons[clickcard].setGraphic(new ImageView(cardf[card[clickcard]]));
                buttons[index].setDisable(true);
                buttons[clickcard].setDisable(true);

            }else {
                buttons[index].setGraphic(new ImageView(cardf[card[index]]));
                Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), e -> {
                    cardState[index] = false;
                    cardState[clickcard] = false;
                    buttons[index].setGraphic(new ImageView(cardBack));
                    buttons[clickcard].setGraphic(new ImageView(cardBack));
                }));
                timeline.play();
                timeline.setOnFinished(e -> delayedCardIndex = -1);

            }
            count=0;
        }
        System.out.println(card[clickcard]+":"+clickcard+"<<-->>"+card[index]+":"+index);
        System.out.println(count);

    }

    private int[] Card(int[] index) {
        int r = 0;
        while(r<=10){
            int temp1 = (int)(Math.random()*8);
            int temp2 = (int)(Math.random()*8);
            int temp;

            temp=index[temp1];
            index[temp1]=index[temp2];
            index[temp2]=temp;
            r++;
        }
        return  index;
    }



    public static void main(String[] args) {
        launch(args);
    }
}