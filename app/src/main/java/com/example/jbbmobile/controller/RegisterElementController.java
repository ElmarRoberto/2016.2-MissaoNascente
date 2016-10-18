package com.example.jbbmobile.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.util.Log;

import com.example.jbbmobile.dao.ElementDAO;
import com.example.jbbmobile.model.Element;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RegisterElementController {
    private String currentPhotoPath = EMPTY_STRING;
    private Element element;
    private ElementDAO elementDAO;
    private String email;
    private String date;

    private static final String EMPTY_STRING = "";

    private final String TAG = "RegisterElement";

    public void associateElementbyQrCode(String code, Context context) throws SQLException,IllegalArgumentException{
        int currentBookPeriod, currentBook;

        int qrCodeNumber = Integer.parseInt(code);

        elementDAO = new ElementDAO(context);
        element = elementDAO.findElementByQrCode(qrCodeNumber);

        String catchCurrentDate = getCurrentDate();
        currentBook = element.getIdBook();

        LoginController loginController = new LoginController();
        loginController.loadFile(context);
        email = loginController.getExplorer().getEmail();
        date = catchCurrentDate;

        currentBookPeriod = BooksController.currentPeriod;

        if(currentBook == currentBookPeriod ) {
            try {
                elementDAO.insertElementExplorer(email, catchCurrentDate, qrCodeNumber, EMPTY_STRING);
            }catch (SQLException sqlException){
                currentPhotoPath = findImagePathByAssociation();
                throw sqlException;
            }
        }else{
            throw new IllegalArgumentException("Periodo Inválido");
        }
    }

    public File createImageFile(File storageDirectory) throws IOException {
        File image;

        if(currentPhotoPath.equals(EMPTY_STRING)){
            String imageFileName = "USER_ELEMENT_ID_" + Integer.toString(element.getIdElement()) + "_";
            image = File.createTempFile(imageFileName, ".jpg", storageDirectory);

            Log.d(TAG, "[" + imageFileName+ "]" + "[" + Integer.toString(element.getIdElement()) + "]");
        }else{
            image = new File(currentPhotoPath);
        }

        currentPhotoPath = image.getAbsolutePath();

        Log.d(TAG, "[" + currentPhotoPath + "]");

        return image;
    }

    public String getCurrentPhotoPath() {
        return currentPhotoPath;
    }

    public void setCurrentPhotoPath(String currentPhotoPath) {
        this.currentPhotoPath = currentPhotoPath;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public void updateElementImage() {
        int result = elementDAO.updateElementExplorer(element.getIdElement(), email, date, currentPhotoPath);
    }


    private String getCurrentDate(){
        DateFormat formatBR = DateFormat.getDateInstance(DateFormat.LONG, new Locale("pt", "BR"));
        Date today = Calendar.getInstance().getTime();

        return formatBR.format(today);
    }

    public String findImagePathByAssociation(){
        Element element = elementDAO.findElementFromRelationTable(this.element.getIdElement(), email);
        return (element.getUserImage() == null) ? "" : element.getUserImage();
    }
}