package th.nguyenphananhtai.thithugk2;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ActivityCN4 extends AppCompatActivity {
    RecyclerView rcCN4;
    HoatDongAdapter adapter;
    ArrayList<DSHoatDong> dsHoatDong;
    ImageButton ibtnBack;

    void QuayLai() {
        ibtnBack = findViewById(R.id.btnBack);
        ibtnBack.setOnClickListener(v -> finish());
    }

    void TimDieuKhien() {
        rcCN4 = findViewById(R.id.rcCN4);
    }

    ArrayList<DSHoatDong> KhoiTao() {
        ArrayList<DSHoatDong> arrHoatDong = new ArrayList<>();

        // Hoạt động với thông tin đầy đủ (mẫu)
        arrHoatDong.add(new DSHoatDong(
                "Ngày hội STEM năm 2025",
                "09/03/2025",
                "Hội trường A - Trường Đại học Nha Trang",
                "Phòng Công tác Chính trị - Sinh viên",
                "Toàn thể sinh viên",
                "Ngày hội STEM là sự kiện thường niên nhằm khuyến khích tinh thần học tập, nghiên cứu khoa học và đổi mới sáng tạo trong các lĩnh vực Khoa học, Công nghệ, Kỹ thuật và Toán học.",
                "• Triển lãm các đề tài nghiên cứu khoa học\n• Hội thảo về xu hướng công nghệ mới\n• Cuộc thi sáng tạo robot\n• Workshop về AI và Machine Learning\n• Giao lưu với các chuyên gia trong ngành\n• Trao giải thưởng cho các đề tài xuất sắc",
                "• Được cộng điểm rèn luyện\n• Cơ hội giao lưu và học hỏi từ chuyên gia\n• Mở rộng kiến thức về công nghệ hiện đại\n• Kết nối với các bạn sinh viên cùng đam mê\n• Nhận chứng nhận tham gia"
        ));

        arrHoatDong.add(new DSHoatDong(
                "Ngày mở trường (Open Day) năm 2016",
                "15/05/2016",
                "Khuôn viên Trường Đại học Nha Trang",
                "Ban Giám hiệu",
                "Học sinh THPT, phụ huynh và sinh viên",
                "Ngày hội tư vấn tuyển sinh, giới thiệu các ngành học và cơ hội nghề nghiệp cho học sinh THPT trên địa bàn tỉnh Khánh Hòa và các tỉnh lân cận.",
                "• Tư vấn tuyển sinh và giới thiệu ngành học\n• Tham quan cơ sở vật chất, phòng thí nghiệm\n• Giao lưu với sinh viên và giảng viên\n• Biểu diễn văn nghệ và các hoạt động ngoại khóa\n• Trưng bày sản phẩm nghiên cứu của sinh viên",
                "• Hiểu rõ về các ngành đào tạo\n• Tham quan cơ sở vật chất hiện đại\n• Nhận tư vấn trực tiếp từ giảng viên\n• Được tặng quà lưu niệm\n• Cơ hội nhận học bổng cho thí sinh xuất sắc"
        ));

        arrHoatDong.add(new DSHoatDong(
                "Ngày Chủ nhật xanh (vệ sinh môi trường)",
                "01/03/2020",
                "Khuôn viên trường và bãi biển Trần Phú",
                "Đoàn Thanh niên - Hội Sinh viên",
                "Toàn thể sinh viên, giảng viên",
                "Chiến dịch vệ sinh môi trường nhằm nâng cao ý thức bảo vệ môi trường trong cộng đồng sinh viên và góp phần xây dựng thành phố Nha Trang xanh - sạch - đẹp.",
                "• Vệ sinh khuôn viên trường học\n• Dọn rác tại bãi biển Trần Phú\n• Trồng cây xanh trong khuôn viên\n• Tuyên truyền về bảo vệ môi trường\n• Phân loại rác thải tái chế\n• Tổ chức các trò chơi về môi trường",
                "• Được cộng điểm rèn luyện\n• Góp phần bảo vệ môi trường\n• Rèn luyện tinh thần tập thể\n• Nâng cao ý thức trách nhiệm xã hội\n• Nhận áo và dụng cụ vệ sinh"
        ));

        arrHoatDong.add(new DSHoatDong(
                "Chính thức đổi tên thành Trường Đại Học Nha Trang",
                "25/07/2006",
                "Trụ sở Trường Đại học Nha Trang",
                "Ban Giám hiệu",
                "Toàn thể cán bộ, giảng viên, sinh viên",
                "Lễ công bố quyết định đổi tên từ Trường Đại học Thủy sản Nha Trang thành Trường Đại học Nha Trang, đánh dấu bước phát triển mới trong lịch sử nhà trường.",
                "• Lễ công bố quyết định chính thức\n• Phát biểu của lãnh đạo Bộ và địa phương\n• Đón nhận bằng khen của Thủ tướng.",
                "• Khẳng định vị thế và tầm vóc mới của nhà trường\n• Thúc đẩy quá trình phát triển toàn diện\n• Tăng cường tinh thần tự hào và đoàn kết trong tập thể\n• Được tặng quà lưu niệm"
        ));

        // Các hoạt động còn lại dùng constructor cũ "
        return arrHoatDong;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cn4);

        dsHoatDong = KhoiTao();
        TimDieuKhien();
        QuayLai();
        rcCN4.setLayoutManager(new LinearLayoutManager(this));

        adapter = new HoatDongAdapter(this, dsHoatDong);
        rcCN4.setAdapter(adapter);

    }
}