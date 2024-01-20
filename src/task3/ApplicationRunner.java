
package task3;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;


public class ApplicationRunner extends Application {
    //in order to store ride details
    ArrayList<Integer> distanceList = new ArrayList<Integer>();
    ArrayList<Integer> timeList = new ArrayList<Integer>();

     public void start(Stage primaryStage) throws Exception {
        // Create a new Scene with a root Node
        StackPane root = new StackPane();
        Scene scene = new Scene(root);

        
        // setting pane to black color
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        // -fx-color: #fff; -fx-font:15; -fx-font-weight:700; -fx-text-alignment:center;");
        

        root.getChildren().add(getCompositeLayout());
        primaryStage.sizeToScene();
        // Set the Scene on the primary Stage and show it
        primaryStage.setScene(scene);
        primaryStage.setTitle("Cyclo");
        primaryStage.show();
    }

    public class CustomButton extends Button {
        public CustomButton(String image, String label, String color, String textColor, int size, double w, double h) {  
            
            this.setMinWidth(w);
            this.setMinHeight(h);
            this.setMaxWidth(w);
            this.setMaxHeight(h);
            this.setPrefWidth(w);
            this.setPrefHeight(h);

            //Creating a graphic (image)
            String imgPath = System.getProperty("user.dir") + File.separator + "images";
            String url = Paths.get(imgPath).toUri().toString();
            url = url + image + ".png";
            this.setText(label);
            this.setAlignment(Pos.BOTTOM_CENTER);
            this.setStyle("-fx-background-color: " + color + ";  -fx-font-weight:800;  -fx-background-size: " + size +  "; -fx-background-position: center; -fx-background-repeat: no-repeat; -fx-background-image: url('" + url + "'); -fx-text-fill: " + textColor + "; -fx-content-display:center; -fx-border-radius:20;");

        }
    }

    public class HistoryScreen extends BorderPane {
    
        public HistoryScreen() {
            // Set the padding for the layout
            setPadding(new Insets(10));
            
            // Create the header label
            Label headerLabel = new Label("Cyclo");
            headerLabel.setStyle("-fx-font-size: 24px; -fx-text-alignment:center; -fx-font-weight: bold; -fx-padding: 0 0 10 0;");
            setTop(headerLabel);
            
            // Create the grid pane to hold the history entries
            GridPane historyGrid = new GridPane();
            historyGrid.setHgap(10);
            historyGrid.setVgap(5);
            historyGrid.setPadding(new Insets(10));
            setCenter(historyGrid);

          

            
            // Add the column headings
            Label distanceLabel = new Label("Distance (km)");
            distanceLabel.setStyle("-fx-font-weight: bold;");
            historyGrid.add(distanceLabel, 0, 0);
            
            Label timeLabel = new Label("Time");
            timeLabel.setStyle("-fx-font-weight: bold;");
            historyGrid.add(timeLabel, 1, 0);
            
            Label dateLabel = new Label("Date");
            dateLabel.setStyle("-fx-font-weight: bold;");
            historyGrid.add(dateLabel, 2, 0);
            
            // use the array list to get individual data
            for(int i =0; i < distanceList.size(); i++){
                int Distance =  distanceList.get(i);
                System.out.println(Distance);
            }
            
            // Create the 'Clear History' button
            Button clearButton = new Button("Clear History");
            clearButton.setOnAction(event -> {
                distanceList.clear();
                timeList.clear();
            });
            setBottom(clearButton);
        }
        
    }
    public EventHandler<ActionEvent> returnToMainScreen(Button returnButton) {
        returnButton.setOnAction(e -> {
            // Get the main stage from the button
            Stage mainStage = (Stage) returnButton.getScene().getWindow();
            mainStage.close();
       });
        return null;
    }

    Pane getHeader(){

        BorderPane borderPane = new BorderPane();

        //network and battery display
        CustomButton networkbtn = new CustomButton("network-icon","", "#000", "#fff", 30, 30,30);
        CustomButton batterybtn = new CustomButton("battery-icon-6","", "#000", "#fff",30, 30,30);
        
        //for the time display and cycle icon
        HBox box = new HBox();
        box.setPadding(new Insets(5, 5, 5, 5));
        Label clockLabel = new Label();
        clockLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            clockLabel.setText(currentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
        clockLabel.setStyle("-fx-text-fill:#fff;");
        clockLabel.setPadding(new Insets(3, 10, 0, 2));
        //gettin cycle image
        CustomButton cycle = new CustomButton("white-bike-icon","", "#000","#fff", 30, 30,25);
        
        box.getChildren().add(clockLabel);
        box.getChildren().add(cycle);
        borderPane.setLeft(box);

        HBox rbox = new HBox();
        rbox.setPadding(new Insets(3, 5, 5, 5));
        rbox.getChildren().add(networkbtn);
        rbox.getChildren().add(batterybtn);
        borderPane.setRight(rbox); 
        return borderPane;
    }
    //first two rows dashboard and navigate
    Pane getFirstRow() {
        HBox hBox = new HBox(6);
        hBox.setPadding(new Insets(3, 5, 5, 5));
        CustomButton btn1 = new CustomButton("white-cycling-icon","Dashboard", "#00bfff","#fff", 80, 150,140);        
        CustomButton btn2 = new CustomButton("nav-icon","Navigate", "#228b22","#fff", 80, 150,140);

        hBox.getChildren().add(btn1);   
        hBox.getChildren().add(btn2);
        
        return hBox;
    }
    //second row history and maps 
    Pane getSecondRow() {
        HBox hBox = new HBox(5);
        hBox.setPadding(new Insets(5, 5, 5, 5));
        CustomButton btn3 = new CustomButton("clock-event-history", "History", "#ffa500","#fff", 80, 150,140);
        CustomButton btn4 = new CustomButton("map-icon", "Map", "#800080","#fff", 80, 150,140);

        //onclicking history button show history
        btn3.setOnMouseClicked(event -> {
            // Create a new HistoryScreen object
            HistoryScreen historyScreen = new HistoryScreen();
        
            // Show the history screen in a new window
            Stage historyStage = new Stage();
            historyStage.setScene(new Scene(historyScreen, 800, 600));
            historyStage.setTitle("History of cycling");
            historyStage.show();
        });

        //on clicking map button show map
        btn4.setOnMouseClicked(e->{
            // Create a new Stage object for the map screen
            Stage mapStage = new Stage();

            // Load the map image
            Image mapImage = new Image("file:///C:/Users/ecs/Downloads/SummativePart2/Task3/images/hendon-area.png");

            // Create an ImageView object to display the map image
            ImageView mapImageView = new ImageView(mapImage);

            // Set the size of the ImageView to the size of the map image
            mapImageView.setFitWidth(mapImage.getWidth());
            mapImageView.setFitHeight(mapImage.getHeight());

            // Create a new Scene object for the map screen with the ImageView as the root node
            StackPane mapStack = new StackPane();
            Scene mapScene = new Scene(mapStack);
            

            VBox box = new VBox(5);
           
            //adding the header to box
            box.getChildren().add(getHeader());
            //adding the map to box
            box.getChildren().add(mapImageView);

            //adding the back button
            box.getChildren().add(getLastRow());

            mapStack.getChildren().add(box);

            // Set the title of the map screen
            mapStage.setTitle("Cyclo");

            //setting the background color
            mapStack.setStyle("-fx-background-color:#000;");

            // Set the scene of the map screen to the new Scene object
            mapStage.setScene(mapScene);

            // Show the map screen
            mapStage.show();
        });
        hBox.getChildren().add(btn3);
        hBox.getChildren().add(btn4);

        return hBox;
    }
    //for fitness and settings
    Pane getThirdRow() {
        HBox hBox = new HBox(5);
        hBox.setPadding(new Insets(5, 5, 5, 5));

        CustomButton btn5 = new CustomButton("fitness-icon", "Fitness", "#ff0000","#fff", 80, 150,140);
        CustomButton btn6 = new CustomButton("gear-icon", "Setting", "#cd853f","#fff", 80, 150,140);


        //fitness button on click logic
        btn5.setOnMouseClicked(event ->{
           
            // Create a new Stage object for the fitness screen
            Stage fitnessStage = new Stage();
            VBox box = new VBox(5);

            HBox h1Box = new HBox(5);
            h1Box.setPadding(new Insets(5, 5, 5, 5));

            HBox h2Box = new HBox(5);
            h2Box.setPadding(new Insets(5, 5, 5, 5));

            HBox h3Box = new HBox(5);
            h3Box.setPadding(new Insets(5, 5, 5, 5));

            CustomButton distance = new CustomButton("ruler-icon", "Distance", "#d3d3d3","#000", 80, 150,140);
            CustomButton time = new CustomButton("stopwatch-icon", "Time", "#d3d3d3","#000", 80, 150,140);
            CustomButton calories = new CustomButton("calories-icon", "Calories", "#d3d3d3","#000", 80, 150,140);
            CustomButton hrzone = new CustomButton("heart-rate-icon", "HR Zone", "##d3d3d3","#000", 80, 150,140);
            CustomButton pzone = new CustomButton("power-icon", "Power Zone", "#d3d3d3","#000", 80, 150,140);
            CustomButton stop = new CustomButton("stop-icon", "Stop", "#d3d3d3","#000", 80, 150,140);
            
            //adding header first
            box.getChildren().add(getHeader());

            h1Box.getChildren().add(distance);
            returnToMainScreen(distance);
            distance.setOnAction(e -> {
              getMeDis();  
            });

            returnToMainScreen(time);
            time.setOnAction(e->{
                getMeTime();
            });       //collect fitness screen options
            h1Box.getChildren().add(time);

            h2Box.getChildren().add(calories);
            h2Box.getChildren().add(hrzone);

            h3Box.getChildren().add(pzone);
            h3Box.getChildren().add(stop);

            box.getChildren().add(h1Box);
            box.getChildren().add(h2Box);
            box.getChildren().add(h3Box);

            box.getChildren().add(getLastRow());

            StackPane fitStack = new StackPane();
            Scene scene = new Scene(fitStack);
            fitStack.getChildren().add(box);
            fitStack.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

            //fitness screen display
            fitnessStage.setScene(scene);
            fitnessStage.sizeToScene();
            fitnessStage.setTitle("Fitness Screen");
            fitnessStage.show();

        });
        hBox.getChildren().add(btn5);
        hBox.getChildren().add(btn6);

        return hBox;
    }
    //for back button
    Pane getLastRow(){
        HBox hBox = new HBox(2);
        hBox.setPadding(new Insets(0, 5, 3, 5));
        CustomButton backbtn = new CustomButton("back-icon","", "#000", "#fff",30, 30,30);

        returnToMainScreen(backbtn);
        hBox.getChildren().add(backbtn);

        return hBox;
    }
    
    //putting all layout together for display
    Pane getCompositeLayout() {
        VBox vBox = new VBox(5);
        vBox.getChildren().add(getHeader());
        vBox.getChildren().add(getFirstRow());
        vBox.getChildren().add(getSecondRow());
        vBox.getChildren().add(getThirdRow());
        vBox.getChildren().add(getLastRow());
        return vBox;
    }
    //distance /time calculator 
    public void getMeDis(){
        Stage primaryStage = new Stage();
        
        
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(10);
        grid.setHgap(10);
        
        grid.setAlignment(Pos.CENTER);

        Label distanceLabel = new Label("Distance (km):");
        distanceLabel.setStyle("-fx-text-fill:#fff; -fx-font-weight:700;");
        TextField distanceTextField = new TextField();
        
        Label speedLabel = new Label("Speed (km/hr):");
        speedLabel.setStyle("-fx-text-fill:#fff; -fx-font-weight:700;");
        TextField speedTextField = new TextField();
        
        Label resultLabel = new Label();
        resultLabel.setStyle("-fx-text-fill:#fff; -fx-font-weight:700;");
        
        Button calculateButton = new Button("Go");
        calculateButton.setStyle("-fx-background-color:ORANGE;");
        calculateButton.setOnAction(e -> {
            double dist = Double.parseDouble(distanceTextField.getText());
            double speed = Double.parseDouble(speedTextField.getText());
            double timeInHours = dist / speed;
            int hours = (int)timeInHours;
            int minutes = (int)((timeInHours - hours) * 60);
            resultLabel.setText(String.format("%02d:%02d", hours, minutes));
        });
        
        HBox hbox = new HBox(10);
        hbox.getChildren().addAll(resultLabel);
        
        VBox vbox = new VBox(10);
        vbox.getChildren().add(getHeader());
        vbox.getChildren().addAll(distanceLabel, distanceTextField, speedLabel, speedTextField, calculateButton, hbox);
        
        grid.add(vbox, 0, 0);
        
        Scene scene = new Scene(grid);
        grid.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        grid.setStyle("-fx-text-fill:#fff;");
        primaryStage.setTitle("Cyclo");
        primaryStage.sizeToScene();
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    //distance /time calculator 
    public void getMeTime(){
        Stage primaryStage = new Stage();
                
                GridPane grid = new GridPane();
                grid.setPadding(new Insets(20, 20, 20, 20));
                grid.setVgap(10);
                grid.setHgap(10);
                
                grid.setAlignment(Pos.CENTER);

                Label distanceLabel = new Label("Hours (Hrs):");
                distanceLabel.setStyle("-fx-text-fill:#fff; -fx-font-weight:700;");
                TextField timeTextField = new TextField();
                
                Label speedLabel = new Label("Speed (km/hr):");
                speedLabel.setStyle("-fx-text-fill:#fff; -fx-font-weight:700;");
                TextField speedTextField = new TextField();
                
                Label resultLabel = new Label();
                resultLabel.setStyle("-fx-text-fill:#fff; -fx-font-weight:700;");
                
                Button calculateButton = new Button("Go");
                calculateButton.setStyle("-fx-background-color:ORANGE;");
                calculateButton.setOnAction(e -> {
                    double time = Double.parseDouble(timeTextField.getText());
                    double speed = Double.parseDouble(speedTextField.getText());
                    double dist = time * speed;
                    resultLabel.setText(String.format("Expected distacne is %02dkm", dist));
                });
                
                HBox hbox = new HBox(10);
                hbox.getChildren().addAll(resultLabel);
                
                VBox vbox = new VBox(10);
                vbox.getChildren().addAll(distanceLabel, timeTextField, speedLabel, speedTextField, calculateButton, hbox);
                
                grid.add(vbox, 0, 0);
                
                Scene scene = new Scene(grid);
                grid.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
                grid.setStyle("-fx-text-fill:#fff;");
                
                primaryStage.setTitle("Cyclo");
                primaryStage.setScene(scene);
                primaryStage.show();

    }
    
    

    public static void main(String[] args) {
        launch(args);
    }
    
}
