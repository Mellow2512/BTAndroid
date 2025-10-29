package thigk1.nguyenphananhtai.quanlyvatlieu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class DienTichActivity extends AppCompatActivity {

    TextInputEditText tieCD;
    TextInputEditText tieCR;
    TextView tvResult;
    Button btnTinhDienTich;

    ImageButton ibtnBack;

    void QuayLai(){
        ibtnBack = findViewById(R.id.btnBack);

        ibtnBack.setOnClickListener(v -> {
            finish();
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dien_tich);

        // Gọi hàm khởi tạo sự kiện
        TinhDiemTB();
        QuayLai();
    }

    void TinhDiemTB() {
        tieCD = findViewById(R.id.tieCD);
        tieCR = findViewById(R.id.tieCR);
        tvResult = findViewById(R.id.tvResult);
        btnTinhDienTich = findViewById(R.id.btnTinhDienTich);

        btnTinhDienTich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy giá trị khi bấm nút, không lấy trước đó
                String gkStr = tieCD.getText().toString().trim();
                String ckStr = tieCR.getText().toString().trim();

                if (gkStr.isEmpty() || ckStr.isEmpty()) {
                    tvResult.setText("Vui lòng nhập đủ số liệu!");
                    return;
                }

                try {
                    float CD = Float.parseFloat(gkStr);
                    float CR = Float.parseFloat(ckStr);

                    float dienTich = CD * CR;

                    tvResult.setText("Diện tích: " + dienTich);
                } catch (NumberFormatException e) {
                    tvResult.setText("Số liệu nhập không hợp lệ!");
                }
            }
        });
    }
}
