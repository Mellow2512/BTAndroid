package th.nguyenphananhtai.ungdungdocsach;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView; // Import CardView

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity {

    private ImageView btnBack;
    private EditText edtName, edtEmail;
    private TextView tvProfileName;

    // SỬA: Thay Button bằng CardView
    private CardView btnSave, btnLogout;

    private SharedPreferences prefs;
    private DatabaseReference databaseReference;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        try {
            databaseReference = FirebaseDatabase.getInstance().getReference("users");
        } catch (Exception e) {
            e.printStackTrace();
        }

        prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        userId = prefs.getString("userId", null);

        initViews();
        loadUserData();

        // Xử lý sự kiện click
        btnBack.setOnClickListener(v -> finish());
        btnSave.setOnClickListener(v -> updateProfile());
        btnLogout.setOnClickListener(v -> showLogoutDialog());
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        edtName = findViewById(R.id.edtEditName);
        edtEmail = findViewById(R.id.edtEditEmail);
        tvProfileName = findViewById(R.id.tvProfileName);

        // SỬA: Ánh xạ đúng với CardView
        btnSave = findViewById(R.id.btnSaveProfile);
        btnLogout = findViewById(R.id.btnLogout);
    }

    private void loadUserData() {
        String name = prefs.getString("fullName", "Khách");
        String email = prefs.getString("email", "");

        tvProfileName.setText(name);
        edtName.setText(name);
        edtEmail.setText(email);
    }

    private void updateProfile() {
        String newName = edtName.getText().toString().trim();
        if (newName.isEmpty()) {
            Toast.makeText(this, "Tên không được để trống", Toast.LENGTH_SHORT).show();
            return;
        }

        if (userId != null && !userId.startsWith("guest")) {
            databaseReference.child(userId).child("fullName").setValue(newName)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                        prefs.edit().putString("fullName", newName).apply();
                        tvProfileName.setText(newName);
                    })
                    .addOnFailureListener(e -> Toast.makeText(this, "Lỗi cập nhật", Toast.LENGTH_SHORT).show());
        } else {
            Toast.makeText(this, "Tài khoản khách không thể cập nhật", Toast.LENGTH_SHORT).show();
        }
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
        editor.remove("fullName");
        editor.remove("email");
        editor.remove("userId");
        editor.apply();

        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}