package com.ashwin.android.mvirxjava.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.ashwin.android.mvirxjava.R;
import com.ashwin.android.mvirxjava.app.home.HomeActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean isLoggedIn = true;
        if (isLoggedIn) {
            openHome();
        } else {
            openLogin();
        }
    }

    private void openHome() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    private void openLogin() {
        // TODO
    }
}
