package visual.camp.sample.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import visual.camp.sample.app.R;

public class LoginActivity extends AppCompatActivity {
    EditText et_nickName;
    Button btn_play;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_play = (Button) findViewById(R.id.btn_play);

        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gamePlay();
            }
        });

    }

    void gamePlay(){
        et_nickName = (EditText) findViewById(R.id.et_nickName);
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("nickName",et_nickName.getText().toString());
        startActivity(intent);
    }
}
