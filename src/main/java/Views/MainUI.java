package Views;

import Controller.AppController;
import Interface.SMModelListener;
import Model.InteractionModel;
import Model.SMModel;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.io.FileNotFoundException;

public class MainUI extends StackPane {

    public MainUI() throws FileNotFoundException {

        HBox mainBox = new HBox();
        ToolPalette toolPalette = new ToolPalette();
        DiagramView diagramView = new DiagramView(600,700);

        AppController appController = new AppController();

//Interaction Model
        InteractionModel interactionModel = new InteractionModel();
        interactionModel.addSubscriber(toolPalette);
        interactionModel.addSubscriber(diagramView);
        interactionModel.addSMSubscriber(diagramView);
        toolPalette.setInteractionModel(interactionModel);
        diagramView.setInteractionModel(interactionModel);
        appController.setInteractionModel(interactionModel);

//SmModel
        SMModel smModel = new SMModel();
        smModel.addSubscriber(diagramView);
        appController.setSmModel(smModel);
        diagramView.setSmModel(smModel);

        toolPalette.setController(appController);
        diagramView.setController(appController);

        System.out.println("Man\n\n");
        mainBox.getChildren().addAll(toolPalette, diagramView);
        this.getChildren().addAll(mainBox);

    }
}
