package com.example.shutda.view;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.shutda.R;
import com.example.shutda.view.background.BackPressCloseHandler;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MenuActivity extends AppCompatActivity{

    private Button gameStartButton;
    private Button scoreboardButton;
    private Button ruleButton;
    private Button leaveButton;
    private Button jogboButton;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore mFirestore;
    private String mUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        final View decorView = getWindow().getDecorView();
        final int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN;

        decorView.setSystemUiVisibility(uiOptions);


        gameStartButton = findViewById(R.id.play_button);
        scoreboardButton = findViewById(R.id.rank_button);
        ruleButton = findViewById(R.id.rule_button);
        jogboButton = findViewById(R.id.answer_button);
        leaveButton = findViewById(R.id.logout_button);
        firebaseAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        if (firebaseAuth.getCurrentUser() == null) {

            sendBack();

        } else {

            mUserId = firebaseAuth.getCurrentUser().getUid();
        }

        gameStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go2Game =  new Intent(MenuActivity.this, MainActivity.class);
                startActivity(go2Game);
            }
        });
        scoreboardButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent go2Rankboard = new Intent(MenuActivity.this, RankActivity.class);
                startActivity(go2Rankboard);

            }
        });

        ruleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO rule intent

            }
        });

        jogboButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go2jogbo = new Intent(MenuActivity.this, JogboActivity.class);
                startActivity(go2jogbo);
            }
        });

        leaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mFirestore.collection("Users").document(firebaseAuth.getCurrentUser().getUid()).update("token_id", "").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Intent quit = new Intent(MenuActivity.this, LoginActivity.class);
                        firebaseAuth.signOut();
                        startActivity(quit);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("로그아웃 실패", "오류 로그: "+e);
                        Toast.makeText(MenuActivity.this, "로그아웃 실패", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void sendBack() {
        Intent loginIntent = new Intent(MenuActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }

    @Override
    public void onBackPressed() {



    }



  public void BackPressed2Login() {

          long backKeyClickTime = 0;

        if (System.currentTimeMillis() > backKeyClickTime + 2000) { backKeyClickTime = System.currentTimeMillis();
            Toast.makeText(MenuActivity.this, "뒤로가기 버튼을 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
            return; }
        if (System.currentTimeMillis() <= backKeyClickTime + 2000) {

            mFirestore.collection("Users").document(firebaseAuth.getCurrentUser().getUid()).update("token_id", "").addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Intent quit = new Intent(MenuActivity.this, LoginActivity.class);
                    firebaseAuth.signOut();
                    startActivity(quit);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e("로그아웃 실패", "오류 로그: "+e);
                    Toast.makeText(MenuActivity.this, "로그아웃 실패", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

}

