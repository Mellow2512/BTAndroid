package th.nguyenphananhtai.thithugk2;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ChiTietMonHocActivity extends AppCompatActivity {

    TextView tvTenMonHoc, tvMaMon, tvSoTinChi, tvGiangVien, tvMoTa, tvNoiDung;
    ImageButton ibtnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_mon_hoc);

        // Ánh xạ các view
        tvTenMonHoc = findViewById(R.id.tvTenMonHoc);
        tvMaMon = findViewById(R.id.tvMaMon);
        tvSoTinChi = findViewById(R.id.tvSoTinChi);
        tvGiangVien = findViewById(R.id.tvGiangVien);
        tvMoTa = findViewById(R.id.tvMoTa);
        tvNoiDung = findViewById(R.id.tvNoiDung);
        ibtnBack = findViewById(R.id.btnBack);

        // Xử lý nút back
        ibtnBack.setOnClickListener(v -> finish());

        // Nhận dữ liệu từ Intent
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String tenMon = bundle.getString("tenMon", "");
            String maMon = bundle.getString("maMon", "");
            int soTinChi = bundle.getInt("soTinChi", 0);
            String giangVien = bundle.getString("giangVien", "");
            String moTa = bundle.getString("moTa", "");
            String noiDung = bundle.getString("noiDung", "");

            // Hiển thị dữ liệu
            tvTenMonHoc.setText(tenMon);
            tvMaMon.setText(maMon);
            tvSoTinChi.setText(String.valueOf(soTinChi));
            tvGiangVien.setText(giangVien);
            tvMoTa.setText(moTa);
            tvNoiDung.setText(noiDung);
        }
    }
}