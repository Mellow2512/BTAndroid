package th.nguyenphananhtai.simplemath;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class MainActivity extends AppCompatActivity {

    EditText edtA, edtB;
    Spinner spinnerOperation;
    Button btnCalculate;
    TextView txtResult;

    String[] operations = {"+", "-", "*", "/"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtA = findViewById(R.id.edtA);
        edtB = findViewById(R.id.edtB);
        spinnerOperation = findViewById(R.id.spinnerOperation);
        btnCalculate = findViewById(R.id.btnCalculate);
        txtResult = findViewById(R.id.txtResult);

        // Đổ dữ liệu vào Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, operations);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOperation.setAdapter(adapter);

        // Xử lý khi bấm nút
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    double a = Double.parseDouble(edtA.getText().toString());
                    double b = Double.parseDouble(edtB.getText().toString());
                    String op = spinnerOperation.getSelectedItem().toString();

                    double result = 0;
                    switch (op) {
                        case "+": result = a + b; break;
                        case "-": result = a - b; break;
                        case "*": result = a * b; break;
                        case "/":
                            if (b == 0) {
                                txtResult.setText("Lỗi: chia cho 0");
                                return;
                            }
                            result = a / b; break;
                    }

                    txtResult.setText("Kết quả: " + result);
                } catch (Exception e) {
                    txtResult.setText("Vui lòng nhập số hợp lệ");
                }
            }
        });
    }
}
