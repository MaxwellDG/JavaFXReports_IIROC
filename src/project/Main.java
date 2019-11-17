package project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private int screen_width = 1000;
    private int screen_height = 850;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/sample.fxml"));
        primaryStage.setTitle("IIROC Report Creation");
        primaryStage.setScene(new Scene(root, screen_width, screen_height));
        primaryStage.show();
        primaryStage.setResizable(false);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
    /*
    Some code that was used to parse the .csv files and get them into exactly the format I needed when put into a
    resource-esque file. The weird characters and some minor problems with extra commas
     were just corrected manually

    List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Max\\IdeaProjects\\IIROC Forms\\src\\sample\\dealer_codes.csv"))) {
                String line;
                while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                for (int i = 0; i < values.length; i++){
        values[i] = "\"" + values[i] + "\"";
        }
        records.add(Arrays.asList(values));
        System.out.println(Arrays.asList(values));
        }
        } catch (IOException e) {
        e.printStackTrace();
        }
   */
