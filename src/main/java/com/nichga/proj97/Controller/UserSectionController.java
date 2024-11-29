package com.nichga.proj97.Controller;

import com.nichga.proj97.Database.DatabaseConnector;
import com.nichga.proj97.Model.DisplayUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class UserSectionController {
    @FXML
    private Button edit;

    @FXML
    private TableView<DisplayUser> tableView;

    private final String getUserData =
            """
            SELECT account_id, member_id, username, fullname,
                   phone, email, join_date, status
            FROM useraccounts
            JOIN members USING (member_id)
            """;

    private final String[] label = {
            "account_id", "member_id", "username", "fullname",
            "phone", "email", "joined_date", "status"
    };

    public void refreshData() {
        ObservableList<TableColumn<DisplayUser, ?>> columns = tableView.getColumns();
        for (int i = 0; i < columns.size(); i++) {
            columns.get(i).setCellValueFactory(new PropertyValueFactory<>(label[i]));
        }
        ObservableList<DisplayUser> data = FXCollections.observableArrayList();
        try (Connection connection = DatabaseConnector.getInstance().getConnection();
             PreparedStatement statement = Objects.requireNonNull(connection).prepareStatement(getUserData);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                data.add(new DisplayUser(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8)
                ));
            }
            tableView.setItems(data);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void initTableView() {
        refreshData();
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tableView.getSelectionModel().selectedItemProperty().addListener((_, _, newValue) -> {
            if (newValue != null) {
                System.out.println("Selected User: " + newValue.getUsername());
            }
        });
        tableView.getSelectionModel().selectedIndexProperty().addListener((_, _, _) -> {
            TableColumn<DisplayUser, String> usernameColumn = (TableColumn<DisplayUser, String>) tableView.getColumns().get(2); // Cột "Username" có index = 2
            usernameColumn.setCellFactory(_ -> {
                TableCell<DisplayUser, String> cell = new TableCell<>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty) {
                            setText(item);
                            if (getTableRow().isSelected()) {
                                setTextFill(Color.WHITE);
                                setStyle("-fx-background-color: #4C566A;");
                            } else {
                                setTextFill(Color.BLACK);
                                setStyle("-fx-background-color: transparent;");
                            }
                        } else {
                            setText(null);
                            setStyle("-fx-background-color: transparent;");
                        }
                    }
                };
                return cell;
            });
        });
    }

    private void initButton() {
        edit.setOnAction(_ -> {
            DisplayUser userSelected = tableView.getSelectionModel().getSelectedItem();
            if (userSelected == null) {
                return;
            }
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(
                        "/com/nichga/proj97/EditUserInfo.fxml"
                ));
                Parent popupMenu = loader.load();
                EditUserInfoController controller = loader.getController();
                controller.initAccountID(userSelected.getAccount_id(), userSelected.getMember_id());
                Stage popupStage = new Stage();
                popupStage.initModality(Modality.APPLICATION_MODAL);
                popupStage.setTitle("Edit user info");
                popupStage.setScene(new Scene(popupMenu));
                popupStage.setResizable(false);
                popupStage.showAndWait();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }

    @FXML
    private void initialize() {
        initTableView();
        initButton();
    }

}
