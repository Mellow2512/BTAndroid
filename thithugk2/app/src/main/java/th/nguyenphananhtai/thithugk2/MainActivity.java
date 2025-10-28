package th.nguyenphananhtai.thithugk2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button btnCN2;
    Button btnCN3;
    Button btnCN4;
    Button btnAboutMe;
    Button btnPuzzle;


    void TimDieuKhien(){
        btnCN2 = findViewById(R.id.mbtnCN2);
        btnCN3 = findViewById(R.id.mbtnCN3);
        btnCN4 = findViewById(R.id.mbtnCN4);
        btnAboutMe = findViewById(R.id.mbtnAboutMe);
        btnPuzzle = findViewById(R.id.mbtnPuzzle);
    }

    void SuKien(){
        btnCN2.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ActivityCN2.class));
        });

        btnCN3.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ActivityCN3.class));
        });

        btnCN4.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ActivityCN4.class));
        });

        btnAboutMe.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ActivityAboutMe.class));
        });

        btnPuzzle.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ActivityPuzzle.class));
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EdgeToEdge.enable(this);
        TimDieuKhien();
        SuKien();

    }


}