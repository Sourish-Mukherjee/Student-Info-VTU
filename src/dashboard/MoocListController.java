package dashboard;

import database.DataBaseHelper;
import information.MoocCourses;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MoocListController implements Initializable {

    @FXML
    private TableView<MoocCourses> moocListTable;

    @FXML
    private TableColumn<MoocCourses, String> moocListTableMoocID, moocListTableCourse, moocListTableAgency, moocListTableWeeks;

    @FXML
    private TextField mlistTFMoocID, mlistTFMoocAgency, mlistTFMoocCourse, mlistTFMoocWeek;

    private final ObservableList<MoocCourses> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillTable();
    }

    private void fillTable() {
        try {
            DataBaseHelper db = new DataBaseHelper();
            db.useDataBase("RegisterPortal");
            list.clear();
            ResultSet set = db.getStatement().executeQuery("SELECT MOOC_ID, COURSE, AGENCY, NO_OF_WEEKS FROM MOOC_LIST");
            while (set.next())
                list.add(new MoocCourses(set.getInt(1),set.getString(2),set.getString(3),set.getString(4)));
            moocListTableMoocID.setCellValueFactory(new PropertyValueFactory<>("mooc_id"));
            moocListTableCourse.setCellValueFactory(new PropertyValueFactory<>("courses"));
            moocListTableAgency.setCellValueFactory(new PropertyValueFactory<>("agency"));
            moocListTableWeeks.setCellValueFactory(new PropertyValueFactory<>("no_of_weeks"));
            moocListTable.setItems(list);
            moocListTable.refresh();
            moocListTable.setOnMouseClicked((MouseEvent event) -> {
                if (event.getClickCount() > 1) {
                    onEdit();
                }
            });
        } catch (
                SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // After Double Clicking on any of the row this function gets called for editing the data
    private void onEdit() {
        if (moocListTable.getSelectionModel().getSelectedItem() != null) {
            MoocCourses moocCourses = moocListTable.getSelectionModel().getSelectedItem();
            mlistTFMoocID.setText(String.valueOf(moocCourses.getMooc_id()));
            mlistTFMoocCourse.setText(moocCourses.getCourses());
            mlistTFMoocWeek.setText((moocCourses.getNo_of_weeks()));
            mlistTFMoocAgency.setText(moocCourses.getAgency());

        }
    }


    @FXML
    protected void addCourseButton() {
        try {
            DataBaseHelper db = new DataBaseHelper();
            String mooc_id = mlistTFMoocID.getText();
            String course = mlistTFMoocCourse.getText();
            String agency = mlistTFMoocAgency.getText();
            String nofweeks = mlistTFMoocWeek.getText();
            list.clear();
            db.useDataBase("registerportal");
            db.getStatement().executeUpdate("INSERT INTO MOOC_LIST(MOOC_ID,COURSE,AGENCY,NO_OF_WEEKS) VALUES('"+mooc_id+"', '"+course+"' ,'"+
                    agency+"', '"+nofweeks+"');");
            ResultSet set = db.getStatement().executeQuery("SELECT * FROM MOOC_LIST");
            while (set.next())
                list.add(new MoocCourses(set.getInt(1),set.getString(2),set.getString(3),set.getString(4)));
            moocListTableMoocID.setCellValueFactory(new PropertyValueFactory<>("mooc_id"));
            moocListTableCourse.setCellValueFactory(new PropertyValueFactory<>("courses"));
            moocListTableAgency.setCellValueFactory(new PropertyValueFactory<>("agency"));
            moocListTableWeeks.setCellValueFactory(new PropertyValueFactory<>("no_of_weeks"));
            moocListTable.setItems(list);
            moocListTable.refresh();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
