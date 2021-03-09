package sample;

import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.stage.StageStyle;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Calendar;
import java.util.TimeZone;
import java.time.LocalDate;

public class QuotationController implements Initializable{

    @FXML
    private TextField companynameTextField;

    @FXML
    private TextField companyaddressTextField;

    @FXML
    private TextField companyphnumberTextField;

    @FXML
    private TextField companyemailTextField;

    @FXML
    private TextField customernameTextField;

    @FXML
    private TextField customeraddressTextField;

    @FXML
    private TextField customerphnumberTextField;

    @FXML
    private TextField customeremailTextField;

    @FXML
    private Label saveMessageLabel;

    @FXML
    private Label quotationIDLabel;

    @FXML
    private Button closeButton;

    @FXML
    private Button printButton;

    @FXML
    private Button addButton;

    @FXML
    private TableView<Product> quotationTable;
    @FXML
    private TableColumn<Product, String> NoColumn;

    @FXML
    private TableColumn<Product, String> productNameColumn;

    @FXML
    private TableColumn<Product, String> descriptionColumn;

    @FXML
    private TableColumn<Product, String> unitColumn;

    @FXML
    private TableColumn<Product, String> quantityColumn;

    @FXML
    private TableColumn<Product, String> ppuColumn;

    @FXML
    private TextField noTextField;

    @FXML
    private TextField productnameTextField;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private TextField unitTextField;

    @FXML
    private TextField quantityTextField;

    @FXML
    private TextField ppuTextField;

    @FXML
    private Label quotationMessageLabel;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        NoColumn.setCellValueFactory(new PropertyValueFactory<>("no"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("product"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        unitColumn.setCellValueFactory(new PropertyValueFactory<>("unit"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        ppuColumn.setCellValueFactory(new PropertyValueFactory<>("priceperunit"));
        quotationTable.setItems(getProductList());
    }
    ObservableList<Product> getProductList(){
        ObservableList<Product> products = FXCollections.observableArrayList();
//        products.add(new Product(1,"CPU","Computer",1,1,3500));
        return products;
    }

    public void addButtonOnAction(ActionEvent event){

        Product items = new Product();
        items.setNo(Integer.parseInt(noTextField.getText()));
        items.setProduct(productnameTextField.getText());
        items.setDescription(descriptionTextField.getText());
        items.setUnit(Integer.parseInt(unitTextField.getText()));
        items.setQuantity(Integer.parseInt(quantityTextField.getText()));
        items.setPriceperunit(Integer.parseInt(ppuTextField.getText()));
        quotationTable.getItems().add(items);
        noTextField.clear();
        productnameTextField.clear();
        descriptionTextField.clear();
        unitTextField.clear();
        quantityTextField.clear();
        ppuTextField.clear();


    }

    public void addToDatabaseOnAction(ActionEvent event){
        product();
    }

    public void closeButtonOnAction(ActionEvent event){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
        Platform.exit();
    }

    public void saveButtonOnAction(ActionEvent event){
        if (companyphnumberTextField.getText().isBlank() || companyphnumberTextField.getText().length() != 10 || customerphnumberTextField.getText().isBlank() || customerphnumberTextField.getText().length() != 10 ){
            quotationMessageLabel.setText("Please try again!");
        } else {
            try{
                int i = Integer.parseInt(companyphnumberTextField.getText());
                int j = Integer.parseInt(customerphnumberTextField.getText());
                quotationForm();
                quotationMessageLabel.setText("");

            } catch(Exception e){

                quotationMessageLabel.setText("phone number cannot be text");
            }

        }


//        stage.close();
//        Platform.exit();
    }
    public void printButtonOnAction(ActionEvent event){

        Stage stage = (Stage) printButton.getScene().getWindow();
    }

    public void product(){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String product_name = productnameTextField.getText();
        String description = descriptionTextField.getText();
        int unit = Integer.parseInt(unitTextField.getText());
        int quantity = Integer.parseInt(quantityTextField.getText());
        int price = Integer.parseInt(ppuTextField.getText());

        String query = String.format("INSERT INTO product(product_name, description, unit, quantity, price) VALUES ('%s', '%s', '%s', '%s', '%s')", product_name, description, unit, quantity, price);

        try {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(query);

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }



    }
    public void quotationForm(){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection conn = connectNow.getConnection();

        String company_name = companynameTextField.getText();
        String company_address = companyaddressTextField.getText();
        String company_phone = companyphnumberTextField.getText();
        String company_email = companyemailTextField.getText();
        String customer_name = customernameTextField.getText();
        String customer_address = customeraddressTextField.getText();
        String customer_phone = customerphnumberTextField.getText();
        String customer_email = customeremailTextField.getText();
        LocalDate currentDate = LocalDate.now();

        int year = currentDate.getYear();
        int month = currentDate.getMonthValue();
        int day = currentDate.getDayOfMonth();


        String query =  String.format("INSERT INTO quotation(company_name, company_address, company_phone, company_email, customer_name, customer_address, customer_phone, customer_email) VALUES ('%s','%s','%s', '%s', '%s', '%s', '%s', '%s')",company_name,company_address, company_phone, company_email, customer_name, customer_address, customer_phone, customer_email);
        try {
            PreparedStatement ps = conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            ps.execute();
            ResultSet res = ps.getGeneratedKeys();
            String id = "";
            while(res.next()){
                System.out.println("ID:" + res.getString(1));
                id = res.getString(1);
            }

            String quotation_id = "QT" + year + month + day + id;
            String quotation_id_query = String.format("UPDATE quotation SET quotation_id='%s' WHERE id='%s'",quotation_id,id);
            ps = conn.prepareStatement(quotation_id_query);
            ps.executeUpdate();
            quotationIDLabel.setText(quotation_id);
            saveMessageLabel.setText("Quotation has saved.");

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();

        }

    }

}
