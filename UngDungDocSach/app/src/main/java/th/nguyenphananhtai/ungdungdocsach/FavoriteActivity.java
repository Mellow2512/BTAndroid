package th.nguyenphananhtai.ungdungdocsach;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity implements BookAdapter.OnBookClickListener {

    private ImageView btnBack;
    private TextView tvViewMode; // Nút chuyển đổi chế độ
    private RecyclerView recyclerViewFavorites;
    private TextView tvFavoriteCount;
    private LinearLayout emptyState;
    private View btnGoToLibrary;

    private BookAdapter bookAdapter;
    private List<Book> favoriteBooks;

    // Biến kiểm soát chế độ hiển thị
    private boolean isGridMode = false; // Mặc định là Danh sách (List)

    private DatabaseReference databaseReference;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        userId = prefs.getString("userId", "guest_" + System.currentTimeMillis());

        initViews();
        setupRecyclerView();
        loadFavoriteBooks();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        recyclerViewFavorites = findViewById(R.id.recyclerViewFavorites);
        tvFavoriteCount = findViewById(R.id.tvFavoriteCount);
        emptyState = findViewById(R.id.emptyState);
        btnGoToLibrary = findViewById(R.id.btnGoToLibrary);

        // Quan trọng: Phải đặt ID cho nút chuyển đổi trong XML
        tvViewMode = findViewById(R.id.tvViewMode);

        btnBack.setOnClickListener(v -> finish());

        // Nút Khám phá thư viện
        btnGoToLibrary.setOnClickListener(v -> {
            Intent intent = new Intent(FavoriteActivity.this, LibraryActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        // --- XỬ LÝ CHUYỂN ĐỔI LIST / GRID ---
        tvViewMode.setOnClickListener(v -> toggleViewMode());
    }

    private void setupRecyclerView() {
        favoriteBooks = new ArrayList<>();
        bookAdapter = new BookAdapter(this, favoriteBooks, this);

        // Mặc định ban đầu set là List (LinearLayoutManager)
        recyclerViewFavorites.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewFavorites.setAdapter(bookAdapter);
    }

    private void toggleViewMode() {
        isGridMode = !isGridMode; // Đảo ngược trạng thái

        if (isGridMode) {
            // Chuyển sang Grid 2 cột
            recyclerViewFavorites.setLayoutManager(new GridLayoutManager(this, 2));
            tvViewMode.setText("Danh sách"); // Đổi chữ thành Danh sách
            tvViewMode.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_list, 0, 0, 0); // Đổi icon (cần có icon list)
        } else {
            // Chuyển về List dọc
            recyclerViewFavorites.setLayoutManager(new LinearLayoutManager(this));
            tvViewMode.setText("Lưới"); // Đổi chữ thành Lưới
            tvViewMode.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_grid, 0, 0, 0); // Đổi icon grid
        }

        // Báo Adapter thay đổi giao diện item
        bookAdapter.setGridMode(isGridMode);
    }

    private void loadFavoriteBooks() {
        databaseReference.child("favorites").child(userId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        favoriteBooks.clear();
                        for (DataSnapshot bookSnapshot : snapshot.getChildren()) {
                            try {
                                Book book = bookSnapshot.getValue(Book.class);
                                if (book != null) {
                                    book.setFavorite(true);
                                    favoriteBooks.add(book);
                                }
                            } catch (Exception e) { e.printStackTrace(); }
                        }
                        // Tạo bản sao mới để tránh lỗi
                        bookAdapter.updateBookList(new ArrayList<>(favoriteBooks));

                        int count = favoriteBooks.size();
                        tvFavoriteCount.setText("Bạn có " + count + " sách yêu thích");

                        if (favoriteBooks.isEmpty()) {
                            showEmptyState();
                        } else {
                            hideEmptyState();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(FavoriteActivity.this, "Lỗi: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        showEmptyState();
                    }
                });
    }

    private void showEmptyState() {
        emptyState.setVisibility(View.VISIBLE);
        recyclerViewFavorites.setVisibility(View.GONE);
    }

    private void hideEmptyState() {
        emptyState.setVisibility(View.GONE);
        recyclerViewFavorites.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBookClick(Book book) {
        Intent intent = new Intent(this, BookDetailActivity.class);
        intent.putExtra("bookId", book.getId());
        startActivity(intent);
    }

    @Override
    public void onFavoriteClick(Book book, int position) {
        databaseReference.child("favorites").child(userId).child(book.getId())
                .removeValue()
                .addOnSuccessListener(aVoid ->
                        Toast.makeText(FavoriteActivity.this, "Đã xóa khỏi yêu thích", Toast.LENGTH_SHORT).show());
    }
}