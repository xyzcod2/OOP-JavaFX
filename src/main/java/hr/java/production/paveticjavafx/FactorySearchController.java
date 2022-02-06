package hr.java.production.paveticjavafx;

import hr.java.production.paveticjavafx.main.Main;
import hr.java.production.paveticjavafx.model.Category;
import hr.java.production.paveticjavafx.model.Factory;
import hr.java.production.paveticjavafx.model.Item;
import hr.java.production.paveticjavafx.model.NamedEntity;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FactorySearchController {
    @FXML
    private TextField factoryNameTextField;

    @FXML
    private TableView<Factory> factoryTableView;

    @FXML
    private TableColumn<Factory, String> nameTableColumn;

    @FXML
    private TableColumn<Factory, String> addressTableColumn;

    @FXML
    private TableColumn<Factory, String> itemsTableColumn;

    public static List<Factory> factoryList = new ArrayList<>();

    @FXML
    public void initialize() {
        nameTableColumn.setCellValueFactory(stringCellDataFeatures ->
                new SimpleStringProperty(stringCellDataFeatures.getValue().getName()));

        addressTableColumn.setCellValueFactory(stringCellDataFeatures ->
                new SimpleStringProperty(stringCellDataFeatures.getValue().getAddress().toString()));

        itemsTableColumn.setCellValueFactory(stringCellDataFeatures -> {
            List<String> names = stringCellDataFeatures.getValue().getItems().stream().map(NamedEntity::getName).collect(Collectors.toList());
            final String namesString = names.toString();
            return new SimpleStringProperty(namesString.substring(1, namesString.length()-1));
        });

        Scanner scanner = new Scanner(System.in);
        Category[] categories = Main.getCategoryInputs(scanner);
        final List<Category> categoriesList = new ArrayList<>(Arrays.asList(categories));
        Set<Item> items = Main.getItemInputs(scanner, categoriesList);
        Factory[] factories = Main.getFactoryInputs(scanner, items);

        factoryList.clear();
        factoryList.addAll(Arrays.asList(factories));

        factoryTableView.setItems(FXCollections.observableList(factoryList));
    }

    public void search() {
        String factoryName = factoryNameTextField.getText();

        List<Factory> results =
                factoryList
                        .stream()
                        .filter(factory -> factory.getName().toLowerCase().contains(factoryName.toLowerCase()))
                        .collect(Collectors.toList());

        factoryTableView.setItems(FXCollections.observableList(results));
    }
}