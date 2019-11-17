package project;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;

import java.text.SimpleDateFormat;
import java.util.Date;

// A util class for checking all the fields' validity //

class TestingInputs {

    TestingInputs() {
    }

    boolean isAlphanumeric(String theString){
        for (int i = 0; i < theString.length(); i++){
            char c = theString.charAt(i);
            if(!Character.isLetterOrDigit(c)){
                return false;
            }
        } return true;
    }

    boolean isNumerical(String theString){
        for (int i = 0; i <theString.length(); i++){
            char c = theString.charAt(i);
            if(!Character.isDigit(c)){
                return false;
            }
        } return true;
    }

    // This just here for if I need to make floats later on. For now it seems like they're not really necessary for final product //
    boolean isFloat(String theString) {
        return isNumerical(theString);
    }

    boolean isValidTime(String string){
        if(string.length() != 8){
            return false;
        } else {
            System.out.println(string.substring(0, 2) + string.substring(3, 5) + string.substring(6, 8));
            if (!string.matches("^(\\d\\d:\\d\\d:\\d\\d)")) {
                return false;
            } else
                return Integer.parseInt(string.substring(0, 2)) <= 24 && Integer.parseInt(string.substring(3, 5)) <= 59 && Integer.parseInt(string.substring(6, 8)) <= 59;
        }
    }

    boolean isChoiceBoxSelected(ChoiceBox choiceBox){
        return choiceBox.getValue() != null;
    }

    boolean isComboBoxSelected(ComboBox comboBox){
        if(comboBox.getValue().toString().contains("-- *")){
            return false;
        }
        return comboBox.getValue() != null;
    }

    boolean isExistingDate(String theString){
        if(!isNumerical(theString)){
            System.out.println(theString + "not numerical date.");
            return false;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String currentDate = simpleDateFormat.format(date);

        int currentYear = Integer.parseInt(currentDate.substring(0, 4));
        int currentMonth = Integer.parseInt(currentDate.substring(4, 6));
        int currentDay = Integer.parseInt(currentDate.substring(6, 8));
        System.out.println(String.valueOf(currentYear) + String.valueOf(currentMonth) + String.valueOf(currentDay) + " is the current date");
        int yearInput = Integer.parseInt(theString.substring(0, 4));
        int monthInput = Integer.parseInt(theString.substring(4, 6));
        int dayInput = Integer.parseInt(theString.substring(6, 8));
        System.out.println(String.valueOf(yearInput) + String.valueOf(monthInput) + String.valueOf(dayInput) + " is the INPUT date");

        if(currentYear == yearInput){
            if(monthInput > currentMonth){
                return false;
            } else if (monthInput == currentMonth) {
                if(dayInput > currentDay){
                    return false;
                }
            }
        }
        return currentYear >= yearInput && monthInput <= 12 && dayInput <= 31;
    }
}
