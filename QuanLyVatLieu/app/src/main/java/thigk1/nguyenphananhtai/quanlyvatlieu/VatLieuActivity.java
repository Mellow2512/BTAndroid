package thigk1.nguyenphananhtai.quanlyvatlieu;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VatLieuActivity extends AppCompatActivity {
    ArrayList<DSVatLieu> dsVatLieu;
    RecyclerView rcCN3;
    VatLieuAdapter adapter;
    ImageButton ibtnBack;

    void QuayLai() {
        ibtnBack = findViewById(R.id.btnBack);
        ibtnBack.setOnClickListener(v -> finish());
    }

    void TimDieuKhien() {
        rcCN3 = findViewById(R.id.rcCN3);
    }

    ArrayList<DSVatLieu> KhoiTao() {
        ArrayList<DSVatLieu> arrVatLieu = new ArrayList<>();

        // Vật liệu có thông tin chi tiết
        arrVatLieu.add(new DSVatLieu(
                "Xi măng Holcim PCB40",
                "XM001",
                "Bao (50kg)",
                "Công ty Xi măng Holcim Việt Nam",
                "Xi măng Holcim PCB40 có cường độ nén cao, thời gian đông kết hợp lý, thích hợp cho các công trình dân dụng và công nghiệp.",
                "• Dùng để trộn bê tông, vữa xây, trát.\n• Tăng độ bền và khả năng chống thấm cho kết cấu.\n• Ứng dụng trong các công trình dân dụng, cầu đường."
        ));

        arrVatLieu.add(new DSVatLieu(
                "Thép Việt Nhật D16",
                "ST016",
                "Kg",
                "Công ty Thép Việt Nhật",
                "Thép Việt Nhật D16 là loại thép thanh vằn có khả năng chịu lực cao, độ dẻo tốt, phù hợp cho các kết cấu chịu tải trọng lớn.",
                "• Dùng làm cốt thép cho cột, dầm, sàn.\n• Gia cố kết cấu bê tông cốt thép.\n• Đảm bảo an toàn và tuổi thọ công trình."
        ));

        arrVatLieu.add(new DSVatLieu(
                "Gạch block bê tông",
                "GB001",
                "Viên",
                "Công ty VLXD Bình Minh",
                "Gạch block được sản xuất từ xi măng, cát và đá mạt, có độ bền cao, kích thước đồng nhất và thân thiện môi trường.",
                "• Dùng xây tường bao, tường ngăn.\n• Cách âm, cách nhiệt tốt.\n• Giảm chi phí xây dựng và tăng tiến độ thi công."
        ));

        arrVatLieu.add(new DSVatLieu(
                "Sơn nước Jotun Majestic",
                "SN002",
                "Thùng (5L)",
                "Công ty Sơn Jotun Việt Nam",
                "Sơn Jotun Majestic có độ bám dính cao, chống nấm mốc, màu sắc bền lâu và thân thiện môi trường.",
                "• Trang trí và bảo vệ bề mặt tường.\n• Chống ẩm mốc, bạc màu.\n• Tạo tính thẩm mỹ cho công trình."
        ));

        arrVatLieu.add(new DSVatLieu(
                "Cát xây tô hạt mịn",
                "CT003",
                "Khối",
                "Công ty VLXD Nam Trung",
                "Cát xây tô sạch, không lẫn tạp chất, có độ mịn cao giúp bề mặt tường nhẵn và bền vững.",
                "• Dùng để trộn vữa xây, vữa tô.\n• Giúp tăng độ kết dính của xi măng.\n• Tạo bề mặt mịn và giảm rạn nứt."
        ));

        return arrVatLieu;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vat_lieu);

        dsVatLieu = KhoiTao();
        TimDieuKhien();
        QuayLai();
        rcCN3.setLayoutManager(new LinearLayoutManager(this));

        adapter = new VatLieuAdapter(this, dsVatLieu);
        rcCN3.setAdapter(adapter);
    }
}
