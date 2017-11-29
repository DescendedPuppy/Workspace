package at.tdog;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUI extends Application {

	public Truthtable truth;
	public KVDiagram diagram;
	public Dialog<Object> loading;
	private TextField input;
	private TabPane tabPane;
	private final ContextMenu cm = new ContextMenu();

	@Override
	public void start(Stage prim) throws Exception {
		

		MenuItem export = new MenuItem("Copy");
		export.setOnAction(e->{
			Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
			clpbrd.setContents(new StringSelection(tabPane.getTabs().get(tabPane.getSelectionModel().getSelectedIndex()).getContent().toString()), null);
			try {
				ImageIO.write(((PaneInterface) tabPane.getTabs().get(tabPane.getSelectionModel().getSelectedIndex()).getContent()).toImage(), "png", new File("Text.png"));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//System.out.println(tabPane.getTabs().get(tabPane.getSelectionModel().getSelectedIndex()).getContent());
		});
		
		MenuItem close = new MenuItem("Close");
		cm.getItems().addAll(export,close);
		
		prim.getIcons().add(new Image("/at/sources/KVTT.png"));
		loading = new Dialog<Object>();
		DialogPane diapane = new DialogPane();
		loading.setTitle("Loading...");
		diapane.setContent(new ImageView(new Image("/at/sources/giphy.gif")));
		loading.setDialogPane(diapane);
		loading.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);

		VBox vbox = new VBox();
		input = new TextField("Term...");
		input.setOnMouseClicked(e -> {
			if (input.getText().equals("Term..."))
				input.setText("");
		});
		input.setOnKeyPressed(e -> {
			if (e.getCode().equals(KeyCode.ENTER))
				runIt();

		});
		Button update = new Button("Update");
		Button help = new Button(" ? ");
		help.setOnAction(e -> {
			Dialog<String> a = new Dialog<String>();
			DialogPane dp = new DialogPane();
			a.setTitle("Information");
			a.setDialogPane(dp);
			dp.setContent(new ImageView(new Image("at/sources/diagram.png")));
			dp.setOnKeyPressed(e1 -> {
				if (e1.getCode().equals(KeyCode.ESCAPE))
					a.close();
			});
			a.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
			a.show();
		});

		update.setOnAction(e -> {
			runIt();
		});

		
		prim.setTitle("KVDiagrams & Truthtables");
		tabPane = new TabPane();
		tabPane.setPrefWidth(500);
		tabPane.setPrefHeight(400);

		HBox hInput = new HBox();
		hInput.setTranslateY(20);
		input.setPrefWidth(300);
		help.setStyle(
                "-fx-background-radius: 100px; "
        );
		
		prim.setMinHeight(400);
		prim.setMinWidth(400);
		
		hInput.getChildren().addAll(input,help);
		hInput.setAlignment(Pos.BOTTOM_CENTER);
		HBox.setMargin(help, new Insets(0,0,0,10));
		update.setTranslateY(30);
		tabPane.setTranslateY(50);
		vbox.getChildren().addAll(hInput, update, tabPane);
		vbox.setAlignment(Pos.TOP_CENTER);
		Scene scene = new Scene(vbox, 500, 500);
		prim.setScene(scene);
		prim.show();
		input.requestFocus();
	}

	public void runIt() {
		Runnable r = new Runnable() {
			@Override
			public void run() {

				Platform.runLater(() -> loading.show());
				Result r = new Result(input.getText());

				try {
					r.generateResults();
					truth = new Truthtable(r);
					Platform.runLater(() -> {
						Tab tab = new Tab();
						tab.setText("TT:" + input.getText());
						Platform.runLater(()->truth.setOnContextMenuRequested(e->{cm.show(truth, e.getScreenX(), e.getScreenY());}));
						tab.setContent(truth);
						tabPane.getTabs().add(tab);
					});
					diagram = new KVDiagram(r);
					Platform.runLater(() -> {
						Tab tab = new Tab();
						tab.setText("KV:" + input.getText());
						Platform.runLater(()->diagram.setOnContextMenuRequested(e->{cm.show(diagram, e.getScreenX(), e.getScreenY());}));
						tab.setContent(diagram);
						tabPane.getTabs().add(tab);
					});

				} catch (Exception e1) {
					Platform.runLater(() -> showAlert(e1));
				}
				Platform.runLater(() -> loading.close());
			}
		};
		Thread t = new Thread(r);
		t.start();
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void showAlert(Exception e1) {
		loading.close();
		Alert a = new Alert(AlertType.ERROR);
		a.setHeaderText("Whoops. Something went wrong");
		a.setContentText("Errorcode:\n" + e1.getLocalizedMessage());
		a.show();
	}
}
