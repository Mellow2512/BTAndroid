package thigk1.nguyenphananhtai.quanlyvatlieu;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ChiTietCongTrinhActivity extends AppCompatActivity {

    TextView tvTenCongTrinh, tvNgayKhoiCong, tvDiaDiem, tvDonViThiCong, tvChuDauTu, tvMoTa, tvQuyMo, tvLoiIch;
    ImageButton ibtnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_cong_trinh);

        // Ánh xạ các view
        tvTenCongTrinh = findViewById(R.id.tvTenCongTrinh);
        tvNgayKhoiCong = findViewById(R.id.tvThoiGian);
        tvDiaDiem = findViewById(R.id.tvDiaDiem);
        tvDonViThiCong = findViewById(R.id.tvDonVi);
        tvChuDauTu = findViewById(R.id.tvDoiTuong);
        tvMoTa = findViewById(R.id.tvMoTa);
        tvQuyMo = findViewById(R.id.tvNoiDung);
        tvLoiIch = findViewById(R.id.tvLoiIch);
        ibtnBack = findViewById(R.id.btnBack);

        // Nút quay lại
        ibtnBack.setOnClickListener(v -> finish());

        // Nhận dữ liệu từ Intent
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String tenCongTrinh = bundle.getString("tenCongTrinh", "");
            String ngayKhoiCong = bundle.getString("ngayKhoiCong", "");
            String diaDiem = bundle.getString("diaDiem", "");
            String donViThiCong = bundle.getString("donViThiCong", "");
            String chuDauTu = bundle.getString("chuDauTu", "");
            String moTa = bundle.getString("moTa", "");
            String quyMo = bundle.getString("quyMo", "");
            String loiIch = bundle.getString("loiIch", "");

            // Hiển thị dữ liệu
            tvTenCongTrinh.setText(tenCongTrinh);
            tvNgayKhoiCong.setText(ngayKhoiCong);
            tvDiaDiem.setText(diaDiem);
            tvDonViThiCong.setText(donViThiCong);
            tvChuDauTu.setText(chuDauTu);
            tvMoTa.setText(moTa);
            tvQuyMo.setText(quyMo);
            tvLoiIch.setText(loiIch);
        }
    }
}
