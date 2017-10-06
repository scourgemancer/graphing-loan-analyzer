package com.timothygeary;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/** Aptly named class responsible for the Graphical User Interface */
public class GUI extends Application{
    @Override
    public void start(Stage stage) throws Exception{
        //Sets the size of the window
        stage.setHeight( Screen.getPrimary().getBounds().getHeight() * 7 / 8 );
        stage.setWidth( Screen.getPrimary().getBounds().getWidth() / 2 );

        BorderPane root = new BorderPane();
        Scene scene = new Scene( root );

        //Sets up the section above the graph
        BorderPane topSection = new BorderPane();
        root.setTop( topSection );

        Label topLabel = new Label("Loans:");
        topLabel.setUnderline( true );
        topSection.setTop( topLabel );

        //Initializes the text fields to gather loan information
        VBox inputs = new VBox();
        TextField name = new TextField("Name");
        TextField amount = new TextField("Loan amount");
        TextField interest = new TextField("Interest Rate (percent)");
        TextField interval = new TextField("How many times is interest compounded annually? (4, 12, 52, 365.25)");
        inputs.getChildren().addAll(name, amount, interest, interval);
        topSection.setCenter( inputs );

        //Creates a button to trigger generating a graph from the information in the text fields
        Button graphButton = new Button("Graph");
        graphButton.setOnAction(e -> {});//todo - implement
        topSection.setBottom(graphButton);


        //The actual graph and its axes
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        StackedAreaChart<Number, Number> graphs = new StackedAreaChart<>(xAxis, yAxis);
        graphs.setTitle("Overall Debt");
        root.setBottom( graphs );


        //Customize the application, its window, and display everything
        stage.setTitle("Graphing Loan Analyzer");
        stage.setScene( scene );
        //stage.getIcons().add( new Image(getClass().getResourceAsStream("./resources/icon.png")) );
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}

//todo - option to toggle between stacked vs. unstacked graph
//todo - show separate graphs (overall debt, specific loans, different strategies, money spent, money saved vs., etc)
