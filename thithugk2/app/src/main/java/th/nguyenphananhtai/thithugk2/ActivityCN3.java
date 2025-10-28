package th.nguyenphananhtai.thithugk2;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ActivityCN3 extends AppCompatActivity {
    ArrayList<DSMon> dsMon;
    RecyclerView rcCN3;
    MonHocAdapter adapter;
    ImageButton ibtnBack;

    void QuayLai() {
        ibtnBack = findViewById(R.id.btnBack);
        ibtnBack.setOnClickListener(v -> finish());
    }

    void TimDieuKhien() {
        rcCN3 = findViewById(R.id.rcCN3);
    }

    ArrayList<DSMon> KhoiTao() {
        ArrayList<DSMon> arrMonHoc = new ArrayList<>();

        // Môn học với thông tin đầy đủ (mẫu)
        arrMonHoc.add(new DSMon(
                "Lập trình Android",
                "IT4785",
                3,
                "TS. Nguyễn Văn A",
                "Môn học cung cấp kiến thức cơ bản về lập trình ứng dụng di động trên nền tảng Android. Sinh viên sẽ học cách xây dựng giao diện, xử lý sự kiện, làm việc với cơ sở dữ liệu và các tính năng nâng cao khác.",
                "• Activity và Intent\n• Layout và View\n• RecyclerView và Adapter\n• SQLite Database\n• Networking và API\n• Notifications\n• Services và Broadcast Receivers"
        ));

        arrMonHoc.add(new DSMon(
                "Lập trình Java",
                "IT3100",
                4,
                "TS. Trần Thị B",
                "Môn học giới thiệu ngôn ngữ lập trình Java, bao gồm cú pháp cơ bản, lập trình hướng đối tượng, xử lý ngoại lệ, collections framework và các kỹ thuật lập trình nâng cao.",
                "• Cú pháp Java cơ bản\n• OOP trong Java\n• Collections Framework\n• Exception Handling\n• File I/O\n• Multithreading\n• JDBC và Database"
        ));

        arrMonHoc.add(new DSMon(
                "Lập trình Web",
                "IT4552",
                3,
                "ThS. Lê Văn C",
                "Môn học cung cấp kiến thức về phát triển ứng dụng web, bao gồm HTML, CSS, JavaScript, các framework phổ biến và kỹ thuật xây dựng website động.",
                "• HTML5 và CSS3\n• JavaScript và DOM\n• React/Vue.js\n• Node.js và Express\n• RESTful API\n• Database Integration\n• Authentication và Security"
        ));

        // Các môn còn lại dùng constructor đơn giản
        arrMonHoc.add(new DSMon("Tin học đại cương"));
        arrMonHoc.add(new DSMon("Lập trình C++"));
        arrMonHoc.add(new DSMon("Lập trình C#"));

        return arrMonHoc;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cn3);

        dsMon = KhoiTao();
        TimDieuKhien();
        QuayLai();
        rcCN3.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MonHocAdapter(this, dsMon);
        rcCN3.setAdapter(adapter);
    }
}