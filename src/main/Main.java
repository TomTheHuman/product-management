package main;

/**
 * @author Thomas Shaw
 **/

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Part;
import model.Product;
import org.jetbrains.annotations.NotNull;

import java.nio.file.attribute.UserDefinedFileAttributeView;


/** <p>The Inventory Management System is designed for creating parts and products.
 * Both parts and products can be searched, modified and deleted after their creation.
 * Products can have associated parts.</p>
 *
 * <p>A future update to this application could include a details view on the Main Form. When
 * a part or product is selected, more details would appear in the view without needing to click
 * the Modify button and navigate to a new page. This would make the user experience more
 * fluid and convenient.</p>
 *
 * <p>I ran into NullPointerException runtime errors when testing thehe modify part and modify
 * product buttons. When I clicked the Modify button without selecting a part or product from the
 * table view, the loader was attempting to send an object to the next page without knowing what
 * to send. I wrapped my modify methods in a try/catch block, which would catch the exception
 * and display an error stating no item was selected. I also found this same issue affected
 * the available and associated parts tables when adding or removing in the add product and modify
 * product forms. I added the same try/catch block to my methods for each and the error was handled
 * in a clean manner.</p>
 *
 */
public class Main extends Application {

    /**
     * Loads Main Form
     * Sets size of scene to 1000x450 pixels
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(@NotNull Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../view/MainForm.fxml"));
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(new Scene(root, 1000, 450));
        primaryStage.show();
    }

    /**
     * Launches GUI
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

}
