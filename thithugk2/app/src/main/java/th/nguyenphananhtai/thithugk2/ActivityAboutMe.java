package th.nguyenphananhtai.thithugk2;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.appbar.MaterialToolbar;

public class ActivityAboutMe extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me); // Đổi tên layout nếu cần

        // Setup toolbar back button
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        // Setup buttons
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
    }
}