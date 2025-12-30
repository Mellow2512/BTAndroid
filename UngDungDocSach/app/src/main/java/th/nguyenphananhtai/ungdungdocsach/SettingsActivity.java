package th.nguyenphananhtai.ungdungdocsach;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView; // Import CardView

import com.bumptech.glide.Glide;
import com.google.firebase.appcheck.interop.BuildConfig;

public class SettingsActivity extends AppCompatActivity {

    private ImageView btnBack;
    private LinearLayout btnEditProfile, btnChangePassword, btnClearCache;
    private CardView btnLogout; // Sửa thành CardView
    private Switch switchNotification, switchDarkMode;
    private TextView tvVersion;

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);

        initViews();
        loadSettings();
        setupListeners();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);

        // Ánh xạ LinearLayout cho các mục settings
        btnEditProfile = findViewById(R.id.btnEditProfile);
        btnChangePassword = findViewById(R.id.btnChangePassword);
        btnClearCache = findViewById(R.id.btnClearCache);

        // Nút Logout là CardView
        btnLogout = findViewById(R.id.btnLogout);

        switchNotification = findViewById(R.id.switchNotification);
        switchDarkMode = findViewById(R.id.switchDarkMode);
        tvVersion = findViewById(R.id.tvVersion);

        // Hiển thị phiên bản
        tvVersion.setText("Phiên bản " + BuildConfig.VERSION_NAME);
    }

    private void loadSettings() {
        boolean isNotiEnabled = prefs.getBoolean("setting_notification", true);
        boolean isDarkMode = prefs.getBoolean("setting_darkmode", false);

        switchNotification.setChecked(isNotiEnabled);
        switchDarkMode.setChecked(isDarkMode);
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> finish());

        // 1. Chuyển sang Profile
        btnEditProfile.setOnClickListener(v -> {
            startActivity(new Intent(SettingsActivity.this, ProfileActivity.class));
        });

        // 2. Đổi mật khẩu
        btnChangePassword.setOnClickListener(v -> {
            Toast.makeText(this, "Tính năng đang phát triển", Toast.LENGTH_SHORT).show();
        });

        // 3. Xử lý Switch
        switchNotification.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefs.edit().putBoolean("setting_notification", isChecked).apply();
            Toast.makeText(SettingsActivity.this, isChecked ? "Đã bật thông báo" : "Đã tắt thông báo", Toast.LENGTH_SHORT).show();
        });

        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefs.edit().putBoolean("setting_darkmode", isChecked).apply();
            Toast.makeText(SettingsActivity.this, "Cài đặt sẽ áp dụng khi khởi động lại", Toast.LENGTH_SHORT).show();
        });

        // 4. Xóa Cache
        btnClearCache.setOnClickListener(v -> showClearCacheDialog());

        // 5. Đăng xuất
        btnLogout.setOnClickListener(v -> showLogoutDialog());
    }

    private void showClearCacheDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Xóa bộ nhớ đệm")
                .setMessage("Xóa cache hình ảnh giúp giải phóng dung lượng máy.")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    new Thread(() -> {
                        Glide.get(getApplicationContext()).clearDiskCache();
                        runOnUiThread(() -> {
                            Glide.get(getApplicationContext()).clearMemory();
                            Toast.makeText(SettingsActivity.this, "Đã dọn dẹp bộ nhớ!", Toast.LENGTH_SHORT).show();
                        });
                    }).start();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void showLogoutDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Đăng xuất")
                .setMessage("Bạn có chắc chắn muốn đăng xuất?")
                .setPositiveButton("Đăng xuất", (dialog, which) -> performLogout())
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void performLogout() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("isLoggedIn", false);
        editor.remove("userId");
        editor.remove("fullName");
        editor.remove("email");
        editor.apply();

        Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}