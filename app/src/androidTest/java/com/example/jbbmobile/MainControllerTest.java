package com.example.jbbmobile;

import android.content.Context;
import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import com.example.jbbmobile.controller.MainController;
import com.example.jbbmobile.model.Element;
import com.example.jbbmobile.view.MainScreenActivity;

import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class MainControllerTest {
    @Rule
    public final ActivityTestRule<MainScreenActivity> mainScreen;

    private Context context;
    private MainController mainController;

    public MainControllerTest(){
        mainScreen = new ActivityTestRule<>(MainScreenActivity.class);
        mainScreen.launchActivity(new Intent());
        context = mainScreen.getActivity();
        mainController = new MainController();
    }

    @Test
    public void testIfValidQRCodeReturnsID() throws Exception{
        String qrCode = "1";
        Element element = mainController.getElementByQRCode(qrCode, context);
        int idElement = element.getIdElement();
        assertEquals(idElement, 1);
    }

    @Test(expected = Exception.class)
    public void testIfQRCodeOutOfBoundsGeneratesException() throws Exception{
        String qrCode = "1000";
        Element element = mainController.getElementByQRCode(qrCode, context);
    }

    @Test(expected = Exception.class)
    public void testIfInvalidQRCodeGeneratesException() throws Exception{
        String qrCode = "testInvalid";
        Element element = mainController.getElementByQRCode(qrCode, context);
    }
}

