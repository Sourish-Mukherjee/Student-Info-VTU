package dashboard;

import authentication.LoginController;
import authentication.RegisterFXMLController;
import database.DataBaseHelper;
import information.SubjectList;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class StudentMarksController implements Initializable {

    @FXML
    private Label stuMarksIAT1;

    @FXML
    private Label stuMarksIAT2;

    @FXML
    private Label stuMarksIAT3;

    @FXML
    private Label stuMarksTotal;

    @FXML
    private Label stuMarksRank;

    @FXML
    private ComboBox<String> stuMarks_Options;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            DataBaseHelper db = new DataBaseHelper();
            db.useDataBase("RegisterPortal");
            ResultSet set = db.getStatement().executeQuery("SELECT SUBJECT_CODE, SUBJECT_NAME, BRANCH, SCHEME FROM SUBJECT_LIST");
            ObservableList<SubjectList> subjectOptions = FXCollections.observableArrayList();
            while (set.next())
                subjectOptions.add(new SubjectList(set.getString(1), set.getString(2), set.getString(3), set.getInt(4)));
            ObservableList<String> subjectOptionsMenu = FXCollections.observableArrayList();
            for (SubjectList subjectList : subjectOptions)
                subjectOptionsMenu.add(subjectList.getSubjectCode());
            stuMarks_Options.setItems(subjectOptionsMenu);
            stuMarks_Options.setValue("Select");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    protected void onChosenSubject() {
        try {
            DataBaseHelper db = new DataBaseHelper();
            db.useDataBase("registerportal");
            String chosenSubject = stuMarks_Options.getSelectionModel().getSelectedItem();
            String email = LoginController.getEmaiL();
            int findID = new RegisterFXMLController().findID(db, email);
            ResultSet set = db.getStatement().executeQuery("Select Iat1,Iat2,Iat3,Total,PASS_FAIL_STATUS from internalmarkstable" +
                    " where link_id ='" + findID + "' and INTERNALMARKSTABLE.SUBJECT_CODE = '" + chosenSubject + "';");
            if (set.next()) {
                stuMarksIAT1.setText("" + set.getInt(1));
                stuMarksIAT2.setText("" + set.getInt(2));
                stuMarksIAT3.setText("" + set.getInt(3));
                stuMarksTotal.setText("" + set.getInt(4));
                stuMarksRank.setText(""+set.getString(5));
            } else
                throw new SQLException("ResultSet - False, fillMarks method");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
