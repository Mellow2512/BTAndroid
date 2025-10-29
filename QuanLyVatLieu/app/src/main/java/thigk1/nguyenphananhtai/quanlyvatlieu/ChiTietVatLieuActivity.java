package thigk1.nguyenphananhtai.quanlyvatlieu;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ChiTietVatLieuActivity extends AppCompatActivity {

    TextView tvTenVatLieu, tvMaVatLieu, tvSoLuong, tvXuatXu, tvMoTa, tvThongTinChiTiet;
    ImageButton ibtnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_vat_lieu);

        // Ánh xạ các view
        tvTenVatLieu = findViewById(R.id.tvTenVatLieu);
        tvMaVatLieu = findViewById(R.id.tvMaVatLieu);
        tvSoLuong = findViewById(R.id.tvSoLuong);
        tvXuatXu = findViewById(R.id.tvXuatXu);
        tvMoTa = findViewById(R.id.tvMoTa);
        tvThongTinChiTiet = findViewById(R.id.tvThongTinChiTiet);
        ibtnBack = findViewById(R.id.btnBack);

        // Xử lý nút back
        ibtnBack.setOnClickListener(v -> finish());

        // Nhận dữ liệu từ Intent
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String tenVatLieu = bundle.getString("tenVatLieu", "");
            String maVatLieu = bundle.getString("maVatLieu", "");
            int soLuong = bundle.getInt("soLuong", 0);
            String xuatXu = bundle.getString("xuatXu", "");
            String moTa = bundle.getString("moTa", "");
            String thongTinChiTiet = bundle.getString("thongTinChiTiet", "");

            // Hiển thị dữ liệu
            tvTenVatLieu.setText(tenVatLieu);
            tvMaVatLieu.setText(maVatLieu);
            tvSoLuong.setText(String.valueOf(soLuong));
            tvXuatXu.setText(xuatXu);
            tvMoTa.setText(moTa);
            tvThongTinChiTiet.setText(thongTinChiTiet);
        }
    }
}
