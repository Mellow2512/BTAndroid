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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.ChipGroup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReadingHistoryActivity extends AppCompatActivity
        implements ReadingHistoryAdapter.OnHistoryClickListener {

    private ImageView btnBack, btnClearHistory;
    private RecyclerView recyclerViewHistory;
    private TextView tvTotalBooks, tvReadingBooks, tvCompletedBooks;
    private LinearLayout emptyState;

    private ChipGroup chipGroupHistory;

    private ReadingHistoryAdapter adapter;
    private List<ReadingHistory> historyList;

    private DatabaseReference databaseReference;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_history);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        userId = prefs.getString("userId", "guest_" + System.currentTimeMillis());

        initViews();
        setupRecyclerView();
        loadReadingHistory();
        setupClickListeners();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        btnClearHistory = findViewById(R.id.btnClearHistory);
        recyclerViewHistory = findViewById(R.id.recyclerViewHistory);
        tvTotalBooks = findViewById(R.id.tvTotalBooks);
        tvReadingBooks = findViewById(R.id.tvReadingBooks);
        tvCompletedBooks = findViewById(R.id.tvCompletedBooks);
        emptyState = findViewById(R.id.emptyState);

        // Ánh xạ ChipGroup
        chipGroupHistory = findViewById(R.id.chipGroupHistory);
    }

    private void setupRecyclerView() {
        historyList = new ArrayList<>();
        adapter = new ReadingHistoryAdapter(this, historyList, this);
        recyclerViewHistory.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewHistory.setAdapter(adapter);
    }

    private void loadReadingHistory() {
        databaseReference.child("reading_history").child(userId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        historyList.clear();
                        for (DataSnapshot historySnapshot : snapshot.getChildren()) {
                            ReadingHistory history = historySnapshot.getValue(ReadingHistory.class);
                            if (history != null) {
                                historyList.add(history);
                            }
                        }
                        // Tạo bản sao để tránh xung đột
                        adapter.updateHistoryList(new ArrayList<>(historyList));
                        updateStats();

                        // Lọc lại dữ liệu dựa trên chip đang chọn (để giữ trạng thái lọc khi data thay đổi)
                        int checkedId = chipGroupHistory.getCheckedChipId();
                        filterByChipId(checkedId);

                        if (historyList.isEmpty()) {
                            showEmptyState();
                        } else {
                            hideEmptyState();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(ReadingHistoryActivity.this, "Lỗi: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        showEmptyState();
                    }
                });
    }

    private void updateStats() {
        int total = historyList.size();
        int reading = 0;
        int completed = 0;

        for (ReadingHistory history : historyList) {
            if (history.getProgress() >= 100) {
                completed++;
            } else if (history.getProgress() > 0) {
                reading++;
            }
        }

        tvTotalBooks.setText(String.valueOf(total));
        tvReadingBooks.setText(String.valueOf(reading));
        tvCompletedBooks.setText(String.valueOf(completed));
    }

    private void setupClickListeners() {
        btnBack.setOnClickListener(v -> finish());

        btnClearHistory.setOnClickListener(v -> showClearHistoryDialog());

        // --- SỬA: Lắng nghe sự kiện trên ChipGroup ---
        chipGroupHistory.setOnCheckedChangeListener((group, checkedId) -> {
            filterByChipId(checkedId);
        });
    }

    // Hàm phụ trợ để lọc dữ liệu
    private void filterByChipId(int checkedId) {
        if (checkedId == R.id.chipAll) {
            adapter.filterByStatus("Tất cả");
        } else if (checkedId == R.id.chipReading) {
            adapter.filterByStatus("Đang đọc");
        } else if (checkedId == R.id.chipCompleted) {
            adapter.filterByStatus("Hoàn thành");
        } else {
            adapter.filterByStatus("Tất cả"); // Mặc định
        }
    }

    private void showClearHistoryDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Xóa lịch sử")
                .setMessage("Bạn có chắc muốn xóa toàn bộ lịch sử đọc?")
                .setPositiveButton("Xóa", (dialog, which) -> clearAllHistory())
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void clearAllHistory() {
        databaseReference.child("reading_history").child(userId)
                .removeValue()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(ReadingHistoryActivity.this, "Đã xóa lịch sử", Toast.LENGTH_SHORT).show();
                    // Không cần update manual vì addValueEventListener sẽ tự chạy
                })
                .addOnFailureListener(e -> Toast.makeText(ReadingHistoryActivity.this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void showEmptyState() {
        emptyState.setVisibility(View.VISIBLE);
        recyclerViewHistory.setVisibility(View.GONE);
    }

    private void hideEmptyState() {
        emptyState.setVisibility(View.GONE);
        recyclerViewHistory.setVisibility(View.VISIBLE);
    }

    @Override
    public void onHistoryClick(ReadingHistory history) {
        Intent intent = new Intent(this, BookDetailActivity.class);
        intent.putExtra("bookId", history.getBookId());
        startActivity(intent);
    }

    @Override
    public void onContinueClick(ReadingHistory history) {
        Intent intent = new Intent(this, ReadBookActivity.class);
        intent.putExtra("bookId", history.getBookId());
        startActivity(intent);
    }
}