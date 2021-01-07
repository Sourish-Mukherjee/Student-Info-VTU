package dashboard;

import database.DataBaseHelper;
import information.InternalMarks;
import information.SubjectList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TeacherSubjectList implements Initializable {

    @FXML
    private TableView<SubjectList> subject_Table;
    @FXML
    private TableColumn<SubjectList, String> subTable_Code, subTable_Name, subTable_Branch;
    @FXML
    private TextField subject_Code, subject_Name, scheme_SubList, subject_branch;

    private final ObservableList<SubjectList> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillTable();
    }

    private void fillTable() {
        DataBaseHelper db = new DataBaseHelper();
        try {
            db.useDataBase("RegisterPortal");
            ResultSet set = db.getStatement().executeQuery("select SUBJECT_CODE, SUBJECT_NAME, BRANCH, SCHEME " +
                    "from SUBJECT_LIST");
            while (set.next())
                list.add(new SubjectList(set.getString(1), set.getString(2),set.getString(3),set.getInt(4)));
            subTable_Code.setCellValueFactory(new PropertyValueFactory<>("subjectCode"));
            subTable_Name.setCellValueFactory(new PropertyValueFactory<>("subjectName"));
            subTable_Branch.setCellValueFactory(new PropertyValueFactory<>("subjectBranch"));
            subject_Table.setItems(list);
            subject_Table.setOnMouseClicked((MouseEvent event) -> {
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
        if (subject_Table.getSelectionModel().getSelectedItem() != null) {
            SubjectList subjectList = subject_Table.getSelectionModel().getSelectedItem();
            subject_Code.setText(String.valueOf(subjectList.getSubjectCode()));
            subject_Name.setText(String.valueOf(subjectList.getSubjectName()));
            subject_branch.setText(String.valueOf(subjectList.getSubjectBranch()));
            scheme_SubList.setText(String.valueOf(subjectList.getScheme()));
        }
    }

    @FXML
    protected void onAddSubjectClick() {
        try {
            DataBaseHelper db = new DataBaseHelper();
            db.useDataBase("registerportal");
            db.getStatement().executeUpdate("INSERT INTO SUBJECT_LIST(SUBJECT_CODE, SUBJECT_NAME, BRANCH, SCHEME) VALUES(\""+subject_Code.getText()+"\", \""+subject_Name.getText()+"\" ,\""+subject_branch.getText()+"\",\""+scheme_SubList.getText()+"\");");
            ResultSet set = db.getStatement().executeQuery("select SUBJECT_CODE, SUBJECT_NAME,BRANCH, SCHEME " +
                    "from SUBJECT_LIST");
            list.clear();
            while (set.next())
                list.add(new SubjectList(set.getString(1), set.getString(2), set.getString(3),set.getInt(4)));
            subTable_Code.setCellValueFactory(new PropertyValueFactory<>("subjectCode"));
            subTable_Name.setCellValueFactory(new PropertyValueFactory<>("subjectName"));
            subTable_Branch.setCellValueFactory(new PropertyValueFactory<>("subjectBranch"));
            subject_Table.setItems(list);
            subject_Table.refresh();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
