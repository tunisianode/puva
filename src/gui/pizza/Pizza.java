package gui.pizza;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Pizza extends Application
{

    private static Configuration conf;

    TextArea bestelltext;

    List<String> zutaten = new ArrayList<>();

    int Preis;

    String piz;

    VBox root;

    public void start(Stage PrimaryStage)
    {
        root = new VBox();
        HBox hbox = new HBox();
        ToggleGroup tg = new ToggleGroup();
        Button btn = new Button("Bestellen");
        btn.setOnAction(e -> Command());
        bestelltext = new TextArea();

        for (int i = 0; i < conf.SizeNames.length; i++)
        {
            int j = i;

            RadioButton rb = new RadioButton(conf.getSizeNames()[i]);
            rb.setToggleGroup(tg);
            rb.setOnAction(e -> {
                if (rb.isSelected())
                {
                    this.Preis = +conf.getToppingPrices()[j];
                    this.piz = conf.getSizeNames()[j];
                }
            });
            hbox.getChildren().add(rb);
        }

        for (int i = 0; i < conf.getToppingNames().length; i++)
        {
            int j = i;

            CheckBox cb = new CheckBox();
            cb.setText(conf.getToppingNames()[i]);
            cb.setOnAction(e -> {
                if (cb.isSelected())
                {
                    zutaten.add(conf.getToppingNames()[j]);
                    this.Preis = +conf.getToppingPrices()[j];
                }
                else
                {
                    zutaten.remove(conf.getToppingNames()[j]);
                    this.Preis = -conf.getToppingPrices()[j];
                }
            });
            if (i < conf.getNumberOfDefaultToppings())
            {
                cb.setDisable(true);
                cb.setSelected(true);
            }
            root.getChildren().add(cb);
        }

        root.getChildren().add(hbox);
        root.getChildren().add(btn);
        root.getChildren().add(bestelltext);
        Scene scene = new Scene(root);
        PrimaryStage.setScene(scene);
        PrimaryStage.setTitle("Hello World");
        PrimaryStage.show();
        System.out.println();
    }

    public String arrayToString(List<String> array)
    {
        if (array.size() == 0)
            return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.size(); ++i)
        {
            sb.append(", ").append(array.get(i));
        }
        return sb.substring(1);
    }

    private void Command()
    {

        String txt = "Sie gaben ein Pizza bestellt.\n Zutaten: " + arrayToString(zutaten) + ".\n Die Grösse ist " + this.piz + " .\n Der Preis beträgt : " + this.Preis + "€.\n Vielen Dank.";
        bestelltext.setText(txt);
    }

    public static void main(String[] args)
    {
        conf = new Configuration((args[2].split("="))[1].split(","), ParameterConverter((args[3].split("="))[1].split(",")), (args[0].split("="))[1].split(","), ParameterConverter((args[1].split("="))[1].split(",")), Integer.parseInt(args[4].split("=")[1]));

        launch(args);
    }

    public static int[] ParameterConverter(String[] array)
    {
        int[] sTable = new int[array.length];

        for (int i = 0; i < array.length; i++)
        {
            sTable[i] = Integer.parseInt(array[i].replace(" ", ""));
        }

        return sTable;
    }
}




