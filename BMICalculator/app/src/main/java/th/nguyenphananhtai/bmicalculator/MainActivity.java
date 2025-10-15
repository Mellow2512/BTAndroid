package th.nguyenphananhtai.bmicalculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText etHeight, etWeight;
    private Button btnCalculate, btnReset;
    private TextView tvResult, tvCategory, tvAdvice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khởi tạo các view
        etHeight = findViewById(R.id.etHeight);
        etWeight = findViewById(R.id.etWeight);
        btnCalculate = findViewById(R.id.btnCalculate);
        btnReset = findViewById(R.id.btnReset);
        tvResult = findViewById(R.id.tvResult);
        tvCategory = findViewById(R.id.tvCategory);
        tvAdvice = findViewById(R.id.tvAdvice);

        // Xử lý sự kiện nút Tính BMI
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateBMI();
            }
        });

        // Xử lý sự kiện nút Reset
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFields();
            }
        });
    }

    private void calculateBMI() {
        String heightStr = etHeight.getText().toString().trim();
        String weightStr = etWeight.getText().toString().trim();

        // Kiểm tra dữ liệu đầu vào
        if (heightStr.isEmpty() || weightStr.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            float height = Float.parseFloat(heightStr);
            float weight = Float.parseFloat(weightStr);

            // Kiểm tra giá trị hợp lệ
            if (height <= 0 || weight <= 0) {
                Toast.makeText(this, "Chiều cao và cân nặng phải lớn hơn 0!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Chuyển đổi chiều cao từ cm sang m
            float heightInMeters = height / 100;

            // Tính BMI
            float bmi = weight / (heightInMeters * heightInMeters);

            // Hiển thị kết quả
            tvResult.setText(String.format("BMI của bạn: %.2f", bmi));
            tvResult.setVisibility(View.VISIBLE);

            // Phân loại BMI
            String category;
            String advice;
            int color;

            if (bmi < 18.5) {
                category = "Thiếu cân";
                advice = "Bạn nên tăng cân bằng chế độ ăn uống hợp lý và tập luyện.";
                color = getResources().getColor(android.R.color.holo_blue_light);
            } else if (bmi >= 18.5 && bmi < 24.9) {
                category = "Bình thường";
                advice = "Chúc mừng! Cân nặng của bạn rất lý tưởng. Hãy duy trì!";
                color = getResources().getColor(android.R.color.holo_green_dark);
            } else if (bmi >= 25 && bmi < 29.9) {
                category = "Thừa cân";
                advice = "Bạn nên chú ý đến chế độ ăn uống và tăng cường vận động.";
                color = getResources().getColor(android.R.color.holo_orange_light);
            } else {
                category = "Béo phì";
                advice = "Bạn nên tham khảo ý kiến bác sĩ để có chế độ giảm cân phù hợp.";
                color = getResources().getColor(android.R.color.holo_red_light);
            }

            tvCategory.setText("Phân loại: " + category);
            tvCategory.setTextColor(color);
            tvCategory.setVisibility(View.VISIBLE);

            tvAdvice.setText(advice);
            tvAdvice.setVisibility(View.VISIBLE);

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Vui lòng nhập số hợp lệ!", Toast.LENGTH_SHORT).show();
        }
    }

    private void resetFields() {
        etHeight.setText("");
        etWeight.setText("");
        tvResult.setVisibility(View.GONE);
        tvCategory.setVisibility(View.GONE);
        tvAdvice.setVisibility(View.GONE);
        etHeight.requestFocus();
    }
}