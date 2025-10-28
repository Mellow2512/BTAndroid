package th.nguyenphananhtai.thithugk2;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ChiTietHoatDongActivity extends AppCompatActivity {

    TextView tvTenHoatDong, tvThoiGian, tvDiaDiem, tvDonVi, tvDoiTuong, tvMoTa, tvNoiDung, tvLoiIch;
    ImageButton ibtnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_hoat_dong);

        // Ánh xạ các view
        tvTenHoatDong = findViewById(R.id.tvTenHoatDong);
        tvThoiGian = findViewById(R.id.tvThoiGian);
        tvDiaDiem = findViewById(R.id.tvDiaDiem);
        tvDonVi = findViewById(R.id.tvDonVi);
        tvDoiTuong = findViewById(R.id.tvDoiTuong);
        tvMoTa = findViewById(R.id.tvMoTa);
        tvNoiDung = findViewById(R.id.tvNoiDung);
        tvLoiIch = findViewById(R.id.tvLoiIch);
        ibtnBack = findViewById(R.id.btnBack);

        // Xử lý nút back
        ibtnBack.setOnClickListener(v -> finish());

        // Nhận dữ liệu từ Intent
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String tenHoatDong = bundle.getString("tenHoatDong", "");
            String thoiGian = bundle.getString("thoiGian", "");
            String diaDiem = bundle.getString("diaDiem", "");
            String donVi = bundle.getString("donVi", "");
            String doiTuong = bundle.getString("doiTuong", "");
            String moTa = bundle.getString("moTa", "");
            String noiDung = bundle.getString("noiDung", "");
            String loiIch = bundle.getString("loiIch", "");

            // Hiển thị dữ liệu
            tvTenHoatDong.setText(tenHoatDong);
            tvThoiGian.setText(thoiGian);
            tvDiaDiem.setText(diaDiem);
            tvDonVi.setText(donVi);
            tvDoiTuong.setText(doiTuong);
            tvMoTa.setText(moTa);
            tvNoiDung.setText(noiDung);
            tvLoiIch.setText(loiIch);
        }
    }
}