package visual.camp.sample.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import visual.camp.sample.app.R;

public class ResultActivity extends AppCompatActivity {
    private TextView txt_result;
    private TextView user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        txt_result = findViewById(R.id.txt_score);
        user = findViewById(R.id.user);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras(); // bundle을 통해 Extra들을 모두 가져온다
        Long result =  bundle.getLong("sec");
        String nick = bundle.getString("nN");
        user.setText(nick+" ");
        txt_result.setText(result.toString());
    }
}
