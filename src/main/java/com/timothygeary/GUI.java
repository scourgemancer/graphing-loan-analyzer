package com.timothygeary;

import com.timothygeary.extfx.DateAxis;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.application.Application;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.geometry.Pos;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.util.Date;

/** Aptly named class responsible for the Graphical User Interface */
public class GUI extends Application{

    /** Forces a text field to only allow numbers and one period */
    private void makeNumericOnly(TextField textField){
        textField.textProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if(newValue.matches("\\d*.\\d*")){
                        textField.setBorder(new Border(new BorderStroke(Paint.valueOf("green"), BorderStrokeStyle.SOLID,
                                new CornerRadii(5), BorderStroke.THIN)));
                    }else{
                        textField.setBorder(new Border(new BorderStroke(Paint.valueOf("red"), BorderStrokeStyle.SOLID,
                                new CornerRadii(5), BorderStroke.THIN)));
                    }
                }
        );
    }

    /** Customizes text fields */
    private void styleTextField(TextField textField, String name, Stage stage, boolean makeNumericOnly){
        textField.setPromptText( name );
        textField.setMaxWidth( stage.getWidth()/4 );
        if(makeNumericOnly) makeNumericOnly(textField);
    }

    @Override
    public void start(Stage stage) throws Exception{
        //Sets the size of the window
        stage.setHeight( Screen.getPrimary().getBounds().getHeight() * 7 / 8 );
        stage.setWidth( Screen.getPrimary().getBounds().getWidth() / 2 );


        BorderPane root = new BorderPane();
        Scene scene = new Scene( root );
        stage.setScene( scene );


        //Sets up the section above the graph
        BorderPane topSection = new BorderPane();
        root.setTop( topSection );

        Text topLabel = new Text("Loans:");
        topLabel.setUnderline( true );
        double size = 12;
        while(topLabel.getBoundsInParent().getWidth() < stage.getWidth()/13){ //increases the label's size until it fits
            size += 0.01;
            topLabel.setFont(Font.font(size));
        }
        topSection.setTop( topLabel );
        BorderPane.setAlignment(topLabel, Pos.CENTER);
        BorderPane.setMargin(topLabel, new Insets(0, 0, stage.getHeight()/50, 0));

        //Initializes the text fields to gather loan information
        VBox inputs = new VBox();
        inputs.setAlignment(Pos.CENTER);
        inputs.setSpacing( stage.getHeight()/75 );

        TextField name = new TextField();
        TextField amount = new TextField();
        TextField interest = new TextField();

        styleTextField(name, "Name", stage, false);
        styleTextField(amount, "Loan amount", stage, true);
        styleTextField(interest, "Interest rate (percent)", stage, true);

        Text intervalText = new Text("How often is interest charged?");

        ToggleGroup options = new ToggleGroup();
        RadioButton daily = new RadioButton("Daily");
        daily.setToggleGroup( options );
        daily.setSelected( true );
        RadioButton monthly = new RadioButton("Monthly");
        monthly.setToggleGroup( options );
        RadioButton quarterly = new RadioButton("Quarterly");
        quarterly.setToggleGroup( options );
        RadioButton annually = new RadioButton("Annually");
        annually.setToggleGroup( options );

        HBox intervalOptions = new HBox(daily, monthly, quarterly, annually );
        VBox interval = new VBox(intervalText, intervalOptions);
        interval.setSpacing( stage.getHeight()/150 );
        interval.setAlignment( Pos.CENTER );
        intervalOptions.setSpacing( stage.getWidth()/75 );
        intervalOptions.setAlignment( Pos.CENTER );

        inputs.getChildren().addAll(name, amount, interest, interval);
        topSection.setCenter( inputs );

        //Creates a button to trigger generating a graph from the information in the text fields
        Button graphButton = new Button("Graph");
        graphButton.setOnAction(e -> {});//todo - implement
        topSection.setBottom(graphButton);
        BorderPane.setAlignment(graphButton, Pos.CENTER);
        BorderPane.setMargin(graphButton, new Insets(stage.getHeight()/50, 0, 0, 0));


        //The actual graph and its axes
        DateAxis xAxis = new DateAxis();
        NumberAxis yAxis = new NumberAxis();
        StackedAreaChart<Date, Number> graphs = new StackedAreaChart<>(xAxis, yAxis);
        graphs.setTitle("Overall Debt");
        root.setBottom( graphs );
        scene.heightProperty().addListener( (observable, oldHeight, newHeight) -> {
            graphs.setPrefHeight( (newHeight.doubleValue() - topSection.getHeight()) * 0.95 );
        } );


        //Customize the application, its window, and display everything
        topSection.requestFocus(); //so the function doesn't start focusing a text field, obscuring it's default text
        stage.setTitle("Graphing Loan Analyzer");
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}

//todo - option to toggle between stacked vs. unstacked graph
//todo - show separate graphs (overall debt, specific loans, different strategies, money spent, money saved vs., etc)
