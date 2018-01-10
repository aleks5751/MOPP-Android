package ee.ria.DigiDoc.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import ee.ria.DigiDoc.R;
import ee.ria.DigiDoc.android.main.home.HomeScreen;
import ee.ria.DigiDoc.android.utils.conductor.ConductorNavigator;

public class Activity extends AppCompatActivity {

    @Inject ConductorNavigator conductorNavigator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.Theme_Application);
        super.onCreate(savedInstanceState);
        Application.component(this).inject(this);

        conductorNavigator.attach(this, android.R.id.content, savedInstanceState);
        if (!conductorNavigator.hasRootScreen()) {
            conductorNavigator.setRootScreen(HomeScreen.create());
        }
    }

    @Override
    public void onBackPressed() {
        if (!conductorNavigator.handleBack()) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        conductorNavigator.onActivityResult(requestCode, resultCode, data);
    }
}
