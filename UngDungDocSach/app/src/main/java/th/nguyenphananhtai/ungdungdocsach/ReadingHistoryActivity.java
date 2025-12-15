package th.nguyenphananhtai.ungdungdocsach;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class ReadingHistoryActivity extends AppCompatActivity
        implements ReadingHistoryAdapter.OnHistoryClickListener {

    private ImageView btnBack, btnClearHistory;
    private RecyclerView recyclerViewHistory;
    private TextView tvTotalBooks, tvReadingBooks, tvCompletedBooks;
    private LinearLayout emptyState;
    private Chip chipAll, chipReading, chipCompleted;

    private ReadingHistoryAdapter adapter;
    private List<ReadingHistory> historyList;

    private FirebaseManager firebaseManager;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_history);

        // Initialize Firebase
        firebaseManager = FirebaseManager.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            userId = currentUser.getUid();
        } else {
            SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            userId = prefs.getString("userId", "guest_" + System.currentTimeMillis());
        }

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

        chipAll = findViewById(R.id.chipAll);
        chipReading = findViewById(R.id.chipReading);
        chipCompleted = findViewById(R.id.chipCompleted);
    }

    private void setupRecyclerView() {
        historyList = new ArrayList<>();
        adapter = new ReadingHistoryAdapter(this, historyList, this);
        recyclerViewHistory.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewHistory.setAdapter(adapter);
    }

    private void loadReadingHistory() {
        firebaseManager.getReadingHistory(userId, new FirebaseManager.OnHistoryLoadedListener() {
            @Override
            public void onHistoryLoaded(List<ReadingHistory> histories) {
                historyList.clear();
                historyList.addAll(histories);
                adapter.updateHistoryList(histories);
                updateStats();

                if (histories.isEmpty()) {
                    showEmptyState();
                } else {
                    hideEmptyState();
                }
            }

            @Override
            public void onError(String error) {
                Toast.makeText(ReadingHistoryActivity.this,
                        "Lỗi: " + error,
                        Toast.LENGTH_SHORT).show();
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

        chipAll.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                adapter.filterByStatus("Tất cả");
            }
        });

        chipReading.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                adapter.filterByStatus("Đang đọc");
            }
        });

        chipCompleted.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                adapter.filterByStatus("Hoàn thành");
            }
        });
    }

    private void showClearHistoryDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Xóa lịch sử")
                .setMessage("Bạn có chắc muốn xóa toàn bộ lịch sử đọc?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    clearAllHistory();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void clearAllHistory() {
        firebaseManager.clearReadingHistory(userId, new FirebaseManager.OnOperationListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(ReadingHistoryActivity.this,
                        "Đã xóa lịch sử",
                        Toast.LENGTH_SHORT).show();
                loadReadingHistory();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(ReadingHistoryActivity.this,
                        "Lỗi: " + error,
                        Toast.LENGTH_SHORT).show();
            }
        });
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
        // Mở chi tiết sách
        Intent intent = new Intent(this, BookDetailActivity.class);
        intent.putExtra("bookId", history.getBookId());
        startActivity(intent);
    }

    @Override
    public void onContinueClick(ReadingHistory history) {
        // TODO: Mở reader activity và tiếp tục đọc
        Toast.makeText(this,
                "Tiếp tục đọc: " + history.getBookTitle(),
                Toast.LENGTH_SHORT).show();

        // Intent intent = new Intent(this, ReaderActivity.class);
        // intent.putExtra("bookId", history.getBookId());
        // intent.putExtra("currentPage", history.getCurrentPage());
        // startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadReadingHistory();
    }
}