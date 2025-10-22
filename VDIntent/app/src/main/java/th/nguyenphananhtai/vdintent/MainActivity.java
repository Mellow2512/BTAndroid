package th.nguyenphananhtai.vdintent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button nutChuyen = findViewById(R.id.btnSend);
        nutChuyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iChuyen = new Intent(MainActivity.this, SecondActivity.class);
                EditText edtHoTen = findViewById(R.id.edtHoTen);
                String data = edtHoTen.getText().toString();
                iChuyen.putExtra("HovaTen", data);
                iChuyen.putExtra("data2", "HongTruong");
                startActivity(iChuyen);
            }
        });
    }
}
