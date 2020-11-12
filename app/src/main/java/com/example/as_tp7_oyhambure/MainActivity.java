package com.example.as_tp7_oyhambure;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.WindowManager;

import org.cocos2d.opengl.CCGLSurfaceView;

public class MainActivity extends AppCompatActivity {

    CCGLSurfaceView VistaPrincipal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        VistaPrincipal = new CCGLSurfaceView(this);
        setContentView(VistaPrincipal);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public void onStart(){
        super.onStart();
        clsJuego miGenialJuego;
        miGenialJuego = new clsJuego(VistaPrincipal);
        miGenialJuego.ComenzarJuego();
    }
}