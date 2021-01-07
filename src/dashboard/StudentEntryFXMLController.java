package dashboard;

import authentication.LoginController;
import authentication.RegisterFXMLController;
import database.DataBaseHelper;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import information.SubjectList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class StudentEntryFXMLController {

    @FXML
    private TextField name_stuEntry, mothername_stuEntry, fathername_stuEntry,
            usn_stuEntry,
            pho_stuEntry,
            branch_stuEntry,
            scheme_stuEntry,
            year_stuEntry;
    @FXML
    private Label label_stuEntry;

    @FXML
    protected void onSave() throws SQLException, IOException {
        DataBaseHelper db = new DataBaseHelper();
        createTableForStudentDetails(db);
        enterDataInStudentDetails(db);
    }

    public void createStudentEntryTable(DataBaseHelper db) throws SQLException {
        if (db == null) {
            System.out.println("Error In Table Creation Of Student Entry");
        } else {
            createTableForStudentDetails(db);
        }
    }

    private void createTableForStudentDetails(DataBaseHelper db) throws SQLException {
        db.useDataBase("RegisterPortal");
        db.createTable("STUDENT_INFO", "SL INT AUTO_INCREMENT PRIMARY KEY,"
                + "STUDENT_NAME VARCHAR(20),FATHER_NAME VARCHAR(20),MOTHER_NAME VARCHAR(20), USN VARCHAR(20), BRANCH VARCHAR(20), PHONE_NUMBER VARCHAR(20)"
                + ",ADMISSION_YEAR VARCHAR(5), LINK_ID INT, SCHEME INT, FOREIGN KEY(LINK_ID) REFERENCES data(ID)");
        db.createTable("Subject_List", "SUBJECT_CODE VARCHAR(10) PRIMARY KEY,"+"SUBJECT_NAME VARCHAR(40), BRANCH VARCHAR(15), SCHEME INT");
        db.createTable("InternalMarksTable ", "Marks_ID INT PRIMARY KEY AUTO_INCREMENT ," +
                "IAT1 INT DEFAULT 0, IAT2 INT DEFAULT 0 , IAT3 INT DEFAULT 0, ASSIGNMENT_MARKS INT DEFAULT 0, TOTAL " +
                "INT DEFAULT 0 , AVERAGE DECIMAL(4,2) DEFAULT 0, PASS_FAIL_STATUS VARCHAR(6), LINK_ID INT, SUBJECT_CODE VARCHAR(10)," +
                " FOREIGN KEY(LINK_ID) REFERENCES STUDENT_INFO(LINK_ID), FOREIGN KEY(SUBJECT_CODE) REFERENCES SUBJECT_LIST(SUBJECT_CODE)");
        db.createTable("MOOC_LIST","MOOC_ID INT PRIMARY KEY ,"+" COURSE VARCHAR(30), AGENCY VARCHAR(15), NO_OF_WEEKS VARCHAR(2)");

    }

    private boolean enterDataInStudentDetails(DataBaseHelper db) throws IOException {
        try {
            db.useDataBase("RegisterPortal");
        } catch (SQLException ex) {
            System.out.println("Could Not Use StudentDetails DataBase");
            return false;
        }
        try {
            db.getStatement().executeUpdate("UPDATE STUDENT_INFO SET STUDENT_NAME = '" + name_stuEntry.getText()+ "',FATHER_NAME ='"
                    + fathername_stuEntry.getText() + "',MOTHER_NAME ='"
                    + mothername_stuEntry.getText()+ "',USN ='"
                    + usn_stuEntry.getText() +  "',SCHEME ='"
                    + scheme_stuEntry.getText() +"', BRANCH ='" + branch_stuEntry.getText() + "',PHONE_NUMBER='" + pho_stuEntry.getText()
                    + "', ADMISSION_YEAR='" + year_stuEntry.getText() + "' WHERE LINK_ID=" + new RegisterFXMLController().findID(db, LoginController.getEmaiL()));
            ResultSet set = db.getStatement().executeQuery("SELECT SUBJECT_CODE, SUBJECT_NAME, BRANCH, SCHEME FROM SUBJECT_LIST");
            ObservableList<SubjectList> subjectOptions = FXCollections.observableArrayList();
            while (set.next())
                subjectOptions.add(new SubjectList(set.getString(1),set.getString(2),set.getString(3),set.getInt(4)));
            for(SubjectList subjectList : subjectOptions)
                db.getStatement().executeUpdate("Insert into InternalMarksTable(SUBJECT_CODE,Link_ID) values('" +subjectList.getSubjectCode()+"',"+ new RegisterFXMLController().findID(db, LoginController.getEmaiL()) + ");");
        } catch (SQLException ex) {
            System.out.println(ex);
            label_stuEntry.setText("Could Not Store Data!");
            return false;
        }
        label_stuEntry.setText("Saved Data!");
        Stage stage = (Stage) label_stuEntry.getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxmlPackage/LoginFXML.fxml")));
        stage.close();
        stage.setScene(scene);
        stage.setTitle("Login Portal!");
        stage.setResizable(false);
        stage.show();
        return true;
    }

}
