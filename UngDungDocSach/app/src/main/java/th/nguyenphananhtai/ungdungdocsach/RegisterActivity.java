package th.nguyenphananhtai.ungdungdocsach;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout; // Import LinearLayout
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView; // Import CardView

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtFullName, edtEmail, edtPassword;
    private CardView btnRegister; // Sửa thành CardView
    private LinearLayout tvLogin; // Sửa thành LinearLayout
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        try {
            databaseReference = FirebaseDatabase.getInstance().getReference("users");
        } catch (Exception e) {
            Toast.makeText(this, "Lỗi kết nối Firebase", Toast.LENGTH_SHORT).show();
        }

        initViews();

        // Sự kiện Click Đăng ký
        btnRegister.setOnClickListener(v -> {
            registerUser();
        });

        // Sự kiện Click chuyển sang Đăng nhập
        tvLogin.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    private void initViews() {
        edtFullName = findViewById(R.id.edtFullName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);

        // Ánh xạ đúng kiểu dữ liệu trong XML mới
        btnRegister = findViewById(R.id.btnRegister);
        tvLogin = findViewById(R.id.tvLogin);
    }

    private void registerUser() {
        String name = edtFullName.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            edtFullName.setError("Vui lòng nhập tên");
            edtFullName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            edtEmail.setError("Vui lòng nhập email");
            edtEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            edtPassword.setError("Vui lòng nhập mật khẩu");
            edtPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            edtPassword.setError("Mật khẩu quá ngắn (tối thiểu 6 ký tự)");
            edtPassword.requestFocus();
            return;
        }

        Toast.makeText(this, "Đang xử lý...", Toast.LENGTH_SHORT).show();
        btnRegister.setEnabled(false); // Khóa nút tạm thời

        // Kiểm tra email trùng
        databaseReference.orderByChild("email").equalTo(email)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            btnRegister.setEnabled(true);
                            Toast.makeText(RegisterActivity.this, "Email này đã được sử dụng!", Toast.LENGTH_SHORT).show();
                        } else {
                            createNewUser(name, email, password);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        btnRegister.setEnabled(true);
                        Toast.makeText(RegisterActivity.this, "Lỗi: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void createNewUser(String name, String email, String password) {
        String userId = databaseReference.push().getKey();
        User user = new User(userId, name, email, password);

        if (userId != null) {
            databaseReference.child(userId).setValue(user)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(RegisterActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                        saveLoginSession(userId, name, email);

                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        btnRegister.setEnabled(true);
                        Toast.makeText(RegisterActivity.this, "Lỗi tạo tài khoản", Toast.LENGTH_SHORT).show();
                    });
        }
    }

    private void saveLoginSession(String userId, String name, String email) {
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("isLoggedIn", true);
        editor.putString("userId", userId);
        editor.putString("fullName", name);
        editor.putString("email", email);
        editor.apply();
    }
}