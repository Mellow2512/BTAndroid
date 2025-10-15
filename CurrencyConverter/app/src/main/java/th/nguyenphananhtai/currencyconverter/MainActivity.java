package th.nguyenphananhtai.currencyconverter;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText edtUSD, edtVND;
    private Button btnConvert, btnSwap;
    private TextView tvRate;
    private static final double USD_TO_VND = 25450.0; // 1 USD ≈ 25,450 VND
    private boolean isUpdating = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtUSD = findViewById(R.id.edtUSD);
        edtVND = findViewById(R.id.edtINR); // dùng lại id cũ cho VND
        btnConvert = findViewById(R.id.btnConvert);
        btnSwap = findViewById(R.id.btnSwap);
        tvRate = findViewById(R.id.tvRate);

        // hiển thị tỷ giá
        tvRate.setText("25.450 VND");

        btnConvert.setOnClickListener(v -> convertCurrency());
        btnSwap.setOnClickListener(v -> swapCurrencies());

        edtUSD.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (!isUpdating && !s.toString().isEmpty()) {
                    isUpdating = true;
                    try {
                        double usd = Double.parseDouble(s.toString());
                        double vnd = usd * USD_TO_VND;
                        edtVND.setText(String.format("%.0f", vnd));
                    } catch (NumberFormatException e) {
                        edtVND.setText("");
                    }
                    isUpdating = false;
                }
            }
        });

        edtVND.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (!isUpdating && !s.toString().isEmpty()) {
                    isUpdating = true;
                    try {
                        double vnd = Double.parseDouble(s.toString());
                        double usd = vnd / USD_TO_VND;
                        edtUSD.setText(String.format("%.2f", usd));
                    } catch (NumberFormatException e) {
                        edtUSD.setText("");
                    }
                    isUpdating = false;
                }
            }
        });
    }

    private void convertCurrency() {
        String usdText = edtUSD.getText().toString().trim();
        String vndText = edtVND.getText().toString().trim();

        if (usdText.isEmpty() && vndText.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập giá trị tiền tệ", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            if (!usdText.isEmpty()) {
                double usd = Double.parseDouble(usdText);
                double vnd = usd * USD_TO_VND;
                edtVND.setText(String.format("%.0f", vnd));
                Toast.makeText(this, "✓ Chuyển đổi thành công", Toast.LENGTH_SHORT).show();
            } else if (!vndText.isEmpty()) {
                double vnd = Double.parseDouble(vndText);
                double usd = vnd / USD_TO_VND;
                edtUSD.setText(String.format("%.2f", usd));
                Toast.makeText(this, "✓ Chuyển đổi thành công", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "❌ Vui lòng nhập số hợp lệ", Toast.LENGTH_SHORT).show();
        }
    }

    private void swapCurrencies() {
        String usd = edtUSD.getText().toString();
        String vnd = edtVND.getText().toString();

        edtUSD.setText(vnd);
        edtVND.setText(usd);
    }
}
