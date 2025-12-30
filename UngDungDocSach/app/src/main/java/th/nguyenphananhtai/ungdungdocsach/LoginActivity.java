package th.nguyenphananhtai.ungdungdocsach;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView; // Import CardView

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmail, edtPassword;

    // SỬA: Thay Button bằng CardView
    private CardView btnLogin;

    private LinearLayout tvRegister;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        try {
            databaseReference = FirebaseDatabase.getInstance().getReference("users");
        } catch (Exception e) {
            Toast.makeText(this, "Lỗi kết nối Firebase", Toast.LENGTH_SHORT).show();
        }

        initViews();

        btnLogin.setOnClickListener(v -> loginUser());

        tvRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
            finish();
        });
    }

    private void initViews() {
        edtEmail = findViewById(R.id.edtEmailLogin);
        edtPassword = findViewById(R.id.edtPasswordLogin);

        // SỬA: Ánh xạ đúng với CardView
        btnLogin = findViewById(R.id.btnLogin);

        tvRegister = findViewById(R.id.tvRegister);
    }

    private void loginUser() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "Đang kiểm tra...", Toast.LENGTH_SHORT).show();
        btnLogin.setEnabled(false);

        databaseReference.orderByChild("email").equalTo(email)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            boolean isPasswordCorrect = false;
                            for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                                User user = userSnapshot.getValue(User.class);
                                if (user != null && user.getPassword().equals(password)) {
                                    isPasswordCorrect = true;
                                    loginSuccess(user);
                                    return;
                                }
                            }
                            if (!isPasswordCorrect) {
                                btnLogin.setEnabled(true);
                                Toast.makeText(LoginActivity.this, "Sai mật khẩu!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            btnLogin.setEnabled(true);
                            Toast.makeText(LoginActivity.this, "Tài khoản không tồn tại!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        btnLogin.setEnabled(true);
                        Toast.makeText(LoginActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loginSuccess(User user) {
        Toast.makeText(LoginActivity.this, "Xin chào " + user.getFullName(), Toast.LENGTH_SHORT).show();
        saveLoginSession(user.getId(), user.getFullName(), user.getEmail());

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
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