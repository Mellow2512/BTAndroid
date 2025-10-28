package th.nguyenphananhtai.thithugk2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class ActivityCN2 extends AppCompatActivity {

    TextInputEditText tieDTGK;
    TextInputEditText tieDTCK;
    TextView tvResult;
    Button btnTinhDiem;

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
        setContentView(R.layout.activity_cn2);

        // Gọi hàm khởi tạo sự kiện
        TinhDiemTB();
        QuayLai();
    }

    void TinhDiemTB() {
        tieDTGK = findViewById(R.id.tieDTGK);
        tieDTCK = findViewById(R.id.tieDTCK);
        tvResult = findViewById(R.id.tvResult);
        btnTinhDiem = findViewById(R.id.btnTinhDiem);

        btnTinhDiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy giá trị khi bấm nút, không lấy trước đó
                String gkStr = tieDTGK.getText().toString().trim();
                String ckStr = tieDTCK.getText().toString().trim();

                if (gkStr.isEmpty() || ckStr.isEmpty()) {
                    tvResult.setText("Vui lòng nhập đủ điểm!");
                    return;
                }

                try {
                    float diemGK = Float.parseFloat(gkStr);
                    float diemCK = Float.parseFloat(ckStr);

                    float diemTB = (diemGK + diemCK) / 2;

                    if (diemTB >= 5) {
                        tvResult.setText("Điểm TB: " + diemTB + "\nĐậu 🎉");
                    } else {
                        tvResult.setText("Điểm TB: " + diemTB + "\nRớt ❌");
                    }
                } catch (NumberFormatException e) {
                    tvResult.setText("Điểm nhập không hợp lệ!");
                }
            }
        });
    }
}
