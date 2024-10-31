
package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class StudentGradingSystemController {

	@FXML
	private TextField rollNumberField;
	@FXML
	private TextField gradeField1;
	@FXML
	private TextField gradeField2;
	@FXML
	private TextField gradeField3;
	@FXML
	private TextField gradeField4;
	@FXML
	private TextField gradeField5;
	@FXML
	private TextField gradeField6;
	@FXML
	private TextField gradeField7;
	@FXML
	private TextField gradeField8;
	@FXML
	private TextField gradeField9;
	@FXML
	private TextField creditPointField1;
	@FXML
	private TextField creditPointField2;
	@FXML
	private TextField creditPointField3;
	@FXML
	private TextField creditPointField4;
	@FXML
	private TextField creditPointField5;
	@FXML
	private TextField creditPointField6;
	@FXML
	private TextField creditPointField7;
	@FXML
	private TextField creditPointField8;
	@FXML
	private TextField creditPointField9;
	@FXML
	private TextField numberOfStudentsTextField;

	@FXML
	private Button getMarksButton;
	@FXML
	private Button getCGPAButton;
	@FXML
	private Button getAverageButton;
	@FXML
	private Button saveButton;
	
	@FXML
	private Button clearButton;
	@FXML
	private Button clearGradesButton;

	@FXML
	private Button selectFileButton;
	@FXML
	private ListView<String> studentListView;
	
	
	@FXML
	private TextField selectedFileTextField;

	private File selectedFile;
	private static class StudentCGPA {
		private String rollNumber;
		private double cgpa;

		public StudentCGPA(String rollNumber, double cgpa) {
			this.rollNumber = rollNumber;
			this.cgpa = cgpa;
		}

		public String getRollNumber() {
			return rollNumber;
		}

		public double getCgpa() {
			return cgpa;
		}
	}
	@FXML
	public void initialize() {
		    studentListView.setVisible(false);
            numberOfStudentsTextField.setEditable(false);
		    selectedFileTextField.setEditable(false);
		
	}
		

	@FXML
	private void onSaveButtonClicked(ActionEvent event) {
		if (selectedFile == null) {
			showWarning("No File Selected", "Please select a file first.");
			return;
		}

		String rollNumber = rollNumberField.getText();
		if (rollNumber.isEmpty()) {
			showWarning("Missing Information", "Please enter the Roll Number.");
			return;
		}
		if( !isVaildRollnumber(rollNumber)) {
			showWarning("Invaild Roll Number", "Roll number should not contain commas.");
			return;
		}
		
		
		if (isDuplicateRollNumber(rollNumber)) {
			showWarning("Duplicate Roll Number", "Roll Number " + rollNumber + " is already saved.");
			return;
		}

		if (!areFieldsValid()) {
			showWarning("Invalid Input", "Please enter valid grades and credit points.");
			return;
		}

		try (FileWriter writer = new FileWriter(selectedFile, true)) {
			writer.write(rollNumber + ",");
			writer.write(gradeField1.getText() + ",");
			writer.write(gradeField2.getText() + ",");
			writer.write(gradeField3.getText() + ",");
			writer.write(gradeField4.getText() + ",");
			writer.write(gradeField5.getText() + ",");
			writer.write(gradeField6.getText() + ",");
			writer.write(gradeField7.getText() + ",");
			writer.write(gradeField8.getText() + ",");
			writer.write(gradeField9.getText() + ",");
			writer.write(creditPointField1.getText() + ",");
			writer.write(creditPointField2.getText() + ",");
			writer.write(creditPointField3.getText() + ",");
			writer.write(creditPointField4.getText() + ",");
			writer.write(creditPointField5.getText() + ",");
			writer.write(creditPointField6.getText() + ",");
			writer.write(creditPointField7.getText() + ",");
			writer.write(creditPointField8.getText() + ",");
			writer.write(creditPointField9.getText() + ",");
			writer.write(System.lineSeparator());
             updateNumberOfStudents();
			showInformation("Data Saved", "Student data has been saved successfully.");	
		} catch (IOException e) {
			showError("Error Saving Data", "An error occurred while saving data to the file.");
		}
	}

	private boolean isDuplicateRollNumber(String rollNumber) {
		try (Scanner scanner = new Scanner(selectedFile)) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] data = line.split(",");
				if (data.length >= 1 && data[0].equals(rollNumber)) {
					return true;
				}
			}
		} catch (FileNotFoundException e) {
			showError("File Not Found", "The selected file does not exist or could not be found.");
		}
		return false;
	}
	private boolean isVaildRollnumber(String rollNo) {
	    return !rollNo.contains(",");
	}

	private boolean areFieldsValid() {
		return areGradesValid() && areCreditPointsValid() && !isEmptyFields();
	}

	private boolean isEmptyFields() {
		return rollNumberField.getText().isEmpty() || gradeField1.getText().isEmpty() || gradeField2.getText().isEmpty()
				|| gradeField3.getText().isEmpty() || gradeField4.getText().isEmpty() || gradeField5.getText().isEmpty()
				|| gradeField6.getText().isEmpty() || gradeField7.getText().isEmpty() || gradeField8.getText().isEmpty()
				|| gradeField9.getText().isEmpty() || creditPointField1.getText().isEmpty()
				|| creditPointField2.getText().isEmpty() || creditPointField3.getText().isEmpty()
				|| creditPointField4.getText().isEmpty() || creditPointField5.getText().isEmpty()
				|| creditPointField6.getText().isEmpty() || creditPointField7.getText().isEmpty()
				|| creditPointField8.getText().isEmpty() || creditPointField9.getText().isEmpty();
	}

	private boolean isGradeValid(String grade) {
		return grade.isEmpty() || grade.matches("[OABCUN]|A\\+|B\\+");
	}

	private boolean isCreditPointValid(String creditPoint) {
		try {
			double cp = Double.parseDouble(creditPoint);
			return cp >= 0 && cp <= 10;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private boolean areGradesValid() {
		return isGradeValid(gradeField1.getText()) && isGradeValid(gradeField2.getText())
				&& isGradeValid(gradeField3.getText()) && isGradeValid(gradeField4.getText())
				&& isGradeValid(gradeField5.getText()) && isGradeValid(gradeField6.getText())
				&& isGradeValid(gradeField7.getText()) && isGradeValid(gradeField8.getText())
				&& isGradeValid(gradeField9.getText());
	}

	private boolean areCreditPointsValid() {
		return isCreditPointValid(creditPointField1.getText()) && isCreditPointValid(creditPointField2.getText())
				&& isCreditPointValid(creditPointField3.getText()) && isCreditPointValid(creditPointField4.getText())
				&& isCreditPointValid(creditPointField5.getText()) && isCreditPointValid(creditPointField6.getText())
				&& isCreditPointValid(creditPointField7.getText()) && isCreditPointValid(creditPointField8.getText())
				&& isCreditPointValid(creditPointField9.getText());
	}

	@FXML
	private void onClearButtonClicked(ActionEvent event) {
		rollNumberField.clear();
		gradeField1.clear();
		gradeField2.clear();
		gradeField3.clear();
		gradeField4.clear();
		gradeField5.clear();
		gradeField6.clear();
		gradeField7.clear();
		gradeField8.clear();
		gradeField9.clear();
		creditPointField1.clear();
		creditPointField2.clear();
		creditPointField3.clear();
		creditPointField4.clear();
		creditPointField5.clear();
		creditPointField6.clear();
		creditPointField7.clear();
		creditPointField8.clear();
		creditPointField9.clear();
	}
	@FXML
	private void onClearGradesButtonClicked(ActionEvent event) {
		rollNumberField.clear();
		gradeField1.clear();
		gradeField2.clear();
		gradeField3.clear();
		gradeField4.clear();
		gradeField5.clear();
		gradeField6.clear();
		gradeField7.clear();
		gradeField8.clear();
		gradeField9.clear();
	}

	@FXML
	private void updateSelectedFileTextField() {
		if (selectedFile != null) {
			selectedFileTextField.setText(selectedFile.getName());
			selectedFileTextField.setEditable(false);

		} else {
			selectedFileTextField.clear();
		}
	}

	@FXML
	private void onSelectFileButtonClicked(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
		Stage stage = (Stage) selectFileButton.getScene().getWindow();
		selectedFile = fileChooser.showSaveDialog(stage);
		updateSelectedFileTextField();
		 updateNumberOfStudents();
	}

	@FXML
	private void onGetCGPAButtonClicked(ActionEvent event) {
		 if (selectedFile == null) {
		        showWarning("No File Selected", "Please select a file first.");
		        return;
		    }
		String rollNumber = promptRollNumber();
		if (rollNumber == null) {
			return;
		}

		double cgpa = calculateCGPA(rollNumber);
		if (cgpa > 0.0) {
			showInformation("CGPA Calculation", "CGPA for Roll Number " + rollNumber + " is: " + cgpa);
		} else {
			showInformation("No Data Found", "No data found for Roll Number " + rollNumber);
		}
	}

	@FXML
	private void onGetMarksButtonClicked(ActionEvent event) {
		 if (selectedFile == null) {
		        showWarning("No File Selected", "Please select a file first.");
		        return;
		    }
		String rollNumber = promptRollNumber();
		if (rollNumber == null) {
			// User canceled entering roll number, do nothing.
			return;
		}

		// Search for the student's data in the loaded data
		String[] studentData = findStudentDataByRollNumber(rollNumber);
		if (studentData == null) {
			// No data found for the entered roll number
			showInformation("No Data Found", "No data found for Roll Number " + rollNumber);
			return;
		}
		try {

		// Extract the grades from the student's data
		String grade1 = studentData[1];
		String grade2 = studentData[2];
		String grade3 = studentData[3];
		String grade4 = studentData[4];
		String grade5 = studentData[5];
		String grade6 = studentData[6];
		String grade7 = studentData[7];
		String grade8 = studentData[8];
		String grade9 = studentData[9];
		  // Extract the credit points from the student's data
				String creditPoint1 = studentData[10];
				String creditPoint2 = studentData[11];
				String creditPoint3 = studentData[12];
				String creditPoint4 = studentData[13];
				String creditPoint5 = studentData[14];
				String creditPoint6 = studentData[15];
				String creditPoint7 = studentData[16];
				String creditPoint8 = studentData[17];
				String creditPoint9 = studentData[18];

				// Create the message to display in the dialog box
				String message = "Grades for Roll Number " + rollNumber + ":\n";
				message += "Subject 1: " + grade1 + ", Credit Point : " + creditPoint1 + "\n";
				message += "Subject 2: " + grade2 + ", Credit Point : " + creditPoint2 + "\n";
				message += "Subject 3: " + grade3 + ", Credit Point : " + creditPoint3 + "\n";
				message += "Subject 4: " + grade4 + ", Credit Point : " + creditPoint4 + "\n";
				message += "Subject 5: " + grade5 + ", Credit Point : " + creditPoint5 + "\n";
				message += "Subject 6: " + grade6 + ", Credit Point : " + creditPoint6 + "\n";
				message += "Subject 7: " + grade7 + ", Credit Point : " + creditPoint7 + "\n";
				message += "Subject 8: " + grade8 + ", Credit Point : " + creditPoint8 + "\n";
				message += "Subject 9: " + grade9 + ", Credit Point : " + creditPoint9 + "\n";

				// Show the dialog box with the message
				showInformation("Grades for Roll Number " + rollNumber, message);
		}
		
		catch (ArrayIndexOutOfBoundsException e) {
		    showError("Invalid Data", "There is missing data in a file .");
		}
	
		catch (Exception e) {
			showError("Invaild data","Invaild Data found in a file");
		}
	}

	
	// Helper method for GetMarks
	private String[] findStudentDataByRollNumber(String rollNumber) {
		try (Scanner scanner = new Scanner(selectedFile)) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] data = line.split(",");
				if (data.length >= 11 && data[0].equals(rollNumber)) {
					return data;
				}
			}
		} catch (FileNotFoundException e) {
			showError("File Not Found", "The selected file does not exist or could not be found.");
		}
		return null;
	}private double calculateCGPA(String rollNumber) {
	    double totalCredits = 0.0;
	    double totalGradePoints = 0.0;
	    int numOfSubjects = 0;

	    try (Scanner scanner = new Scanner(selectedFile)) {
	        while (scanner.hasNextLine()) {
	            String line = scanner.nextLine();
	            String[] data = line.split(",");
	            if (data.length >= 19 && data[0].equals(rollNumber)) {
	                for (int i = 1; i <= 9; i++) {
	                    String grade = data[i];
	                    if (!grade.equals("N")) {
	                        double creditPoint = Double.parseDouble(data[i + 9]);
	                        totalGradePoints += creditPoint * convertGradeToValue(grade);
	                        totalCredits += creditPoint;
	                        numOfSubjects++;
	                    }
	                }
	                break;
	            }
	        }
	    } catch (FileNotFoundException e) {
	        showError("File Not Found", "The selected file does not exist or could not be found.");
	    }
	    catch (Exception e) {
			showError("Invaild data","Invaild Data found in a file");
		}

	    if (numOfSubjects > 0) {
	        return Math.round((totalGradePoints / totalCredits) * 100.0) / 100.0; // Round to two decimal places
	    } else {
	        return 0.0;
	    }
	}


	@FXML
	private void onGetAverageButtonClicked(ActionEvent event) {
		 if (selectedFile == null) {
		        showWarning("No File Selected", "Please select a file first.");
		        return;
		    }
		String rollNumber = promptRollNumber();
		if (rollNumber == null || rollNumber.isEmpty()) {
			showWarning("Missing Information", "Please enter the Roll Number.");
			return;
		}

		double averageMarks = calculateAverageMarks(rollNumber);
		if (averageMarks == 0) {
			showWarning("Roll Number Not Found", "The entered Roll Number was not found in the file.");
		} else {
			showInformation("Average Marks", " Average for Roll Number " + rollNumber + " are: " + averageMarks);
		}
	}

	private double calculateAverageMarks(String rollNumber) {
	    int totalMarks = 0;
	    int numOfSubjects = 0;

	    try (Scanner scanner = new Scanner(selectedFile)) {
	        while (scanner.hasNextLine()) {
	            String line = scanner.nextLine();
	            String[] data = line.split(",");
	            if (data.length >= 11 && data[0].equals(rollNumber)) {
	                for (int i = 1; i <= 9; i++) {
	                    if (!data[i].equals("N")) {
	                        totalMarks += convertGradeToValue1(data[i]);
	                        numOfSubjects++;
	                    }
	                }
	                break;
	            }
	        }
	    } catch (FileNotFoundException e) {
	        showError("File Not Found", "The selected file does not exist or could not be found.");
	        return -1;
	    }
	    catch (Exception e) {
			showError("Invaild data","Invaild Data found in a file");
		}

	    if (numOfSubjects > 0) {
	        return (double) totalMarks / numOfSubjects;
	    } else {
	        return 0.0;
	    }
	}


	@FXML
	private void onShowListButtonClicked(ActionEvent event) {
		if (selectedFile == null) {
			showWarning("No File Selected", "Please select a file first.");
			return;
		}

		ObservableList<String> studentInfoList = getStudentRollNumbersOrderedByCGPA();
		studentListView.setItems(studentInfoList);
		studentListView.setVisible(true);
		studentListView.setVisible(true);

	}
	private int calculateNumberOfStudentData() {
		int count = 0;
		try (Scanner scanner = new Scanner(selectedFile)) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (!line.trim().isEmpty()) {
					count++;
				}
			}
		} catch (FileNotFoundException e) {
			showError("File Not Found", "The selected file does not exist or could not be found.");
		}
		catch (Exception e) {
			showError("File Not Found", "Check you Select the correct file.");
	    }
		return count;
	}
	private void updateNumberOfStudents() {
		int numberOfStudents = calculateNumberOfStudentData();
		numberOfStudentsTextField.setText(String.valueOf(numberOfStudents));
	}

	private ObservableList<String> getStudentRollNumbersOrderedByCGPA() {
		List<StudentCGPA> studentCGPAs = new ArrayList<>();

		try (Scanner scanner = new Scanner(selectedFile)) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] data = line.split(",");
				if (data.length >= 11) {
					String rollNumber = data[0];
					double cgpa = calculateCGPA(rollNumber);
					studentCGPAs.add(new StudentCGPA(rollNumber, cgpa));
				}
			}
		} catch (FileNotFoundException e) {
			showError("File Not Found", "The selected file does not exist or could not be found.");
		}

		studentCGPAs.sort(Comparator.comparingDouble(StudentCGPA::getCgpa).reversed());

		ObservableList<String> studentInfoList = FXCollections.observableArrayList();
		int serialNumber = 1; // Initialize serial number
		for (StudentCGPA student : studentCGPAs) {
			studentInfoList.add(serialNumber + "." + student.getRollNumber() + " - CGPA: " + student.getCgpa());
			serialNumber++;
		}

		return studentInfoList;
	}

	// method for Average
	private int convertGradeToValue1(String grade) {
		switch (grade) {
		case "O":
			return 100;
		case "A+":
			return 90;
		case "A":
			return 80;
		case "B+":
			return 70;
		case "B":
			return 60;
		case "C":
			return 50;
		default:
			return 0;
		}
	}

	// method foe CGPA
	private double convertGradeToValue(String grade) {
		switch (grade) {
		case "O":
			return 10;
		case "A+":
			return 9;
		case "A":
			return 8;
		case "B+":
			return 7;
		case "B":
			return 6;
		case "C":
			return 5;
		default:
			return 0;
		}
	}
	private String promptRollNumber() {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Enter Roll Number");
		dialog.setHeaderText(null);
		dialog.setContentText("Please enter the Roll Number:");
		Optional<String> result = dialog.showAndWait();
		return result.orElse(null);
	}

	private void showWarning(String title, String content) {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle(title);
		alert.setContentText(content);
		alert.showAndWait();
	}

	private void showInformation(String title, String content) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setContentText(content);
		alert.showAndWait();
	}

	private void showError(String title, String content) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(title);
		alert.setContentText(content);
		alert.showAndWait();
	}
}
