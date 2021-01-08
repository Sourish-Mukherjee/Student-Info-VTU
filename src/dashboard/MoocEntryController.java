package dashboard;

import database.DataBaseHelper;
import information.InternalMarks;
import information.MoocCourses;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MoocEntryController  implements Initializable {

    @FXML
    private TableView<MoocCourses> moocEntry_Table;

    @FXML
    private TableColumn<MoocCourses, String> moocEntry_Name,moocEntry_USN,moocEntry_Course,moocEntry_Agency,moocEntry_Week,moocEntry_Status,moocEntry_Marks;

    @FXML
    private TextField moocEntry_updateStatus,moocEntry_updateMarks;

    @FXML
    private ComboBox<String> moocIDMenu, studentMenu;

    private final ObservableList<String> studentOptions = FXCollections.observableArrayList();
    private final ObservableList<String> optionList = FXCollections.observableArrayList();
    private final ObservableList<MoocCourses> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            DataBaseHelper db = new DataBaseHelper();
            db.useDataBase("RegisterPortal");
            ResultSet set = db.getStatement().executeQuery("SELECT MOOC_ID FROM MOOC_LIST");
            while(set.next())
                optionList.add(set.getString(1));
            moocIDMenu.setItems(optionList);
            set = db.getStatement().executeQuery("Select SL FROM STUDENT_INFO,MOOC_ENTRY where STUDENT_NAME IS NOT NULL AND MOOC_ID IS NULL;");
            while (set.next()) {
                studentOptions.add(set.getString(1));
            }
            studentMenu.setItems(studentOptions);
            moocIDMenu.setValue("MOOC_ID List");
            studentMenu.setValue("STUDENT_ID List");

        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        fillTable();
    }

    private void fillTable() {
        try{
            DataBaseHelper db = new DataBaseHelper();
            db.useDataBase("RegisterPortal");
            ResultSet set = db.getStatement().executeQuery("Select STUDENT_NAME,USN,COURSE,AGENCY,NO_OF_WEEKS,PASS_FAIL_STATUS,FINAL_MARKS FROM STUDENT_INFO,MOOC_LIST,MOOC_ENTRY"+
                    " WHERE MOOC_ENTRY.STUDENT_ID = STUDENT_INFO.SL AND MOOC_ENTRY.MOOC_ID = MOOC_LIST.MOOC_ID");
            while (set.next()){
                    list.add(new MoocCourses(set.getString(1),set.getString(2),set.getString(3),set.getString(4),
                            set.getString(5),set.getString(6),set.getInt(7)));
            }
            moocEntry_Name.setCellValueFactory(new PropertyValueFactory<>("name"));
            moocEntry_USN.setCellValueFactory(new PropertyValueFactory<>("usn"));
            moocEntry_Course.setCellValueFactory(new PropertyValueFactory<>("courses"));
            moocEntry_Agency.setCellValueFactory(new PropertyValueFactory<>("agency"));
            moocEntry_Week.setCellValueFactory(new PropertyValueFactory<>("no_of_weeks"));
            moocEntry_Status.setCellValueFactory(new PropertyValueFactory<>("status"));
            moocEntry_Marks.setCellValueFactory(new PropertyValueFactory<>("marks"));
            moocEntry_Table.setItems(list);
            moocEntry_Table.refresh();
            moocEntry_Table.setOnMouseClicked((MouseEvent event) -> {
                if (event.getClickCount() > 1) {
                    onEdit();
                }
            });
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    // After Double Clicking on any of the row this function gets called for editing the data
    private void onEdit() {
        if (moocEntry_Table.getSelectionModel().getSelectedItem() != null) {
            MoocCourses moocCourses = moocEntry_Table.getSelectionModel().getSelectedItem();
            moocEntry_updateMarks.setText(String.valueOf(moocCourses.getMarks()));
            moocEntry_updateStatus.setText(moocCourses.getStatus());
        }
    }

    @FXML
    protected void updateInfo() throws SQLException{
        DataBaseHelper db = new DataBaseHelper();
        db.useDataBase("RegisterPortal");
        String oldUSN = moocEntry_Table.getSelectionModel().getSelectedItem().getUsn();
        String newMarks = moocEntry_updateMarks.getText();
        String newStatus = moocEntry_updateStatus.getText();
        db.getStatement().executeUpdate("UPDATE MOOC_ENTRY SET FINAL_MARKS = '"+newMarks+"', PASS_FAIL_STATUS = '"+newStatus+"' WHERE"+
                " STUDENT_ID = '"+findUSNID(db,oldUSN)+"';");
        list.clear();
        ResultSet set = db.getStatement().executeQuery("Select STUDENT_NAME,USN,COURSE,AGENCY,NO_OF_WEEKS,PASS_FAIL_STATUS,FINAL_MARKS FROM STUDENT_INFO,MOOC_LIST,MOOC_ENTRY"+
                " WHERE MOOC_ENTRY.STUDENT_ID = STUDENT_INFO.SL AND MOOC_ENTRY.MOOC_ID = MOOC_LIST.MOOC_ID");
        while (set.next()){
            list.add(new MoocCourses(set.getString(1),set.getString(2),set.getString(3),set.getString(4),
                    set.getString(5),set.getString(6),set.getInt(7)));
        }
        moocEntry_Table.setItems(list);
        moocEntry_Table.refresh();
    }

    @FXML
    protected void updateTableOption(){

    }

    @FXML
    protected void addTableOption(){
        try{
            DataBaseHelper db = new DataBaseHelper();
            db.useDataBase("RegisterPortal");
            String chosenMoocID = moocIDMenu.getSelectionModel().getSelectedItem().toString();
            String chosenStudentID = studentMenu.getSelectionModel().getSelectedItem().toString();
            db.getStatement().executeUpdate("UPDATE MOOC_ENTRY SET MOOC_ID = '"+chosenMoocID+"' WHERE MOOC_ENTRY.STUDENT_ID ='"+chosenStudentID+"';");
            list.clear();
            ResultSet set = db.getStatement().executeQuery("Select STUDENT_NAME,USN,COURSE,AGENCY,NO_OF_WEEKS,PASS_FAIL_STATUS,FINAL_MARKS FROM STUDENT_INFO,MOOC_LIST,MOOC_ENTRY"+
                    " WHERE MOOC_ENTRY.STUDENT_ID = STUDENT_INFO.SL AND MOOC_ENTRY.MOOC_ID = MOOC_LIST.MOOC_ID");
            while (set.next()){
                list.add(new MoocCourses(set.getString(1),set.getString(2),set.getString(3),set.getString(4),
                        set.getString(5),set.getString(6),set.getInt(7)));
            }
            moocEntry_Table.setItems(list);
            moocEntry_Table.refresh();

        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    protected int findUSNID(DataBaseHelper db, String usn) throws SQLException {
        ResultSet set = db.getStatement().executeQuery("Select Link_ID from STUDENT_INFO where usn ='" + usn + "';");
        if (set.next())
            return set.getInt(1);
        return -1;
    }

}
