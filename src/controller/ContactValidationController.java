package controller;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class ContactValidationController implements javafx.beans.value.ChangeListener<String> {


    private int maxLength;
    private TextField phone;


    public ContactValidationController(TextField phone, int maxLength) {
        this.phone= phone;
        this.maxLength = maxLength;
    }


    public int getMaxLength() {
        return maxLength;
    }


    @Override
    public void changed(ObservableValue<? extends String> ov, String oldValue,
            String newValue) {


        if (newValue == null) {
            return;
        }


        if (newValue.length() > maxLength) {
        	phone.setText(oldValue);
        } else {
        	phone.setText(newValue);
        }
    }


}// End of Class