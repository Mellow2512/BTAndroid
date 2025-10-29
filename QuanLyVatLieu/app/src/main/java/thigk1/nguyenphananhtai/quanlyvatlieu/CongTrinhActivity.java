package thigk1.nguyenphananhtai.quanlyvatlieu;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CongTrinhActivity extends AppCompatActivity {
    RecyclerView rcCN4;
    CongTrinhAdapter adapter;
    ArrayList<DSCongTrinh> dsCongTrinh;
    ImageButton ibtnBack;

    void QuayLai() {
        ibtnBack = findViewById(R.id.btnBack);
        ibtnBack.setOnClickListener(v -> finish());
    }

    void TimDieuKhien() {
        rcCN4 = findViewById(R.id.rcCN4);
    }

    ArrayList<DSCongTrinh> KhoiTao() {
        ArrayList<DSCongTrinh> arrCongTrinh = new ArrayList<>();

        // 🏛️ Công trình 1
        arrCongTrinh.add(new DSCongTrinh(
                "Thư viện trung tâm",
                "Khánh thành: 15/08/2018",
                "Khu trung tâm Trường Đại học Nha Trang",
                "Phòng Quản lý Cơ sở vật chất",
                "Sinh viên, giảng viên, cán bộ",
                "Thư viện trung tâm là nơi học tập, nghiên cứu và tra cứu tài liệu hiện đại của trường. Với không gian rộng rãi, wifi tốc độ cao và hơn 20.000 đầu sách, đây là nơi lý tưởng cho sinh viên học nhóm và tự học.",
                "• 5 tầng với sức chứa hơn 500 người\n• Khu đọc mở và khu tài liệu số\n• Phòng học nhóm, phòng máy tính\n• Quầy mượn - trả sách tự động\n• Hệ thống mượn sách trực tuyến",
                "• Tăng khả năng tự học và nghiên cứu\n• Cải thiện môi trường học tập hiện đại\n• Tiện ích tra cứu nhanh chóng\n• Tạo không gian học tập thân thiện"
        ));

        // 🏗️ Công trình 2
        arrCongTrinh.add(new DSCongTrinh(
                "Nhà học A – Khoa Công nghệ Thông tin",
                "Khánh thành: 12/03/2021",
                "Khu giảng đường phía Đông",
                "Ban Quản lý Dự án Xây dựng",
                "Sinh viên, giảng viên Khoa CNTT",
                "Tòa nhà A gồm 5 tầng với nhiều phòng học lý thuyết và phòng thực hành máy tính hiện đại, được đầu tư theo tiêu chuẩn quốc tế nhằm phục vụ đào tạo ngành Công nghệ Thông tin.",
                "• 20 phòng học lý thuyết\n• 10 phòng thực hành máy tính\n• Hệ thống điều hòa, máy chiếu, camera giám sát\n• Thang máy và hệ thống PCCC hiện đại",
                "• Tăng năng suất giảng dạy và học tập\n• Đảm bảo môi trường học an toàn, hiện đại\n• Tạo điều kiện tốt cho các hoạt động nghiên cứu CNTT"
        ));

        // 🏢 Công trình 3
        arrCongTrinh.add(new DSCongTrinh(
                "Ký túc xá sinh viên khu B",
                "Khánh thành: 05/06/2017",
                "Khu phía Bắc Trường Đại học Nha Trang",
                "Trung tâm Quản lý Ký túc xá",
                "Sinh viên nội trú",
                "Ký túc xá khu B là khu nhà ở sinh viên có sức chứa hơn 1000 sinh viên, đầy đủ tiện nghi, an ninh và môi trường sống thân thiện.",
                "• 8 dãy nhà, 4 tầng, tổng 250 phòng\n• Phòng học tự quản và khu sinh hoạt chung\n• Wifi miễn phí, camera an ninh 24/7\n• Căng tin và khu dịch vụ sinh viên",
                "• Đáp ứng nhu cầu chỗ ở cho sinh viên xa nhà\n• Môi trường sống văn minh, an toàn\n• Hỗ trợ tốt cho sinh viên trong học tập và sinh hoạt"
        ));

        // 🧪 Công trình 4
        arrCongTrinh.add(new DSCongTrinh(
                "Trung tâm Thí nghiệm Thủy sản",
                "Khánh thành: 10/09/2014",
                "Khu nghiên cứu phía Tây",
                "Viện Nghiên cứu và Ứng dụng Thủy sản",
                "Giảng viên, sinh viên, nhà nghiên cứu",
                "Trung tâm Thí nghiệm Thủy sản được xây dựng nhằm phục vụ công tác nghiên cứu, giảng dạy và chuyển giao công nghệ trong lĩnh vực nuôi trồng và chế biến thủy sản.",
                "• Phòng phân tích mẫu và xét nghiệm\n• Khu nuôi trồng thực nghiệm\n• Thiết bị phân tích nước, sinh học, hóa học hiện đại\n• Phòng bảo quản mẫu lạnh sâu",
                "• Tăng năng lực nghiên cứu của trường\n• Hỗ trợ giảng dạy thực hành\n• Góp phần phát triển ngành thủy sản bền vững"
        ));

        // 🏟️ Công trình 5
        arrCongTrinh.add(new DSCongTrinh(
                "Nhà thi đấu đa năng",
                "Khánh thành: 01/02/2019",
                "Khu thể thao Trường Đại học Nha Trang",
                "Phòng Giáo dục Thể chất",
                "Sinh viên toàn trường",
                "Nhà thi đấu đa năng được thiết kế hiện đại, phục vụ luyện tập và tổ chức các giải thể thao cấp trường và khu vực.",
                "• Sức chứa 1.500 người\n• Sàn gỗ tiêu chuẩn quốc tế\n• Trang thiết bị thi đấu và âm thanh ánh sáng hiện đại\n• Phòng thay đồ, y tế, và khu khán đài riêng",
                "• Nâng cao đời sống tinh thần cho sinh viên\n• Phát triển phong trào thể dục thể thao\n• Là nơi tổ chức nhiều sự kiện lớn của trường"
        ));

        return arrCongTrinh;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cong_trinh);

        dsCongTrinh = KhoiTao();
        TimDieuKhien();
        QuayLai();
        rcCN4.setLayoutManager(new LinearLayoutManager(this));

        adapter = new CongTrinhAdapter(this, dsCongTrinh);
        rcCN4.setAdapter(adapter);
    }
}
