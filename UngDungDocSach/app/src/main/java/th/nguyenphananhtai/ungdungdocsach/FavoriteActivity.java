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
    private RecyclerView recyclerViewFavorites;
    private TextView tvFavoriteCount;
    private LinearLayout emptyState;

    private BookAdapter bookAdapter;
    private List<Book> favoriteBooks;

    private DatabaseReference databaseReference;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        // Initialize Firebase
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

        btnBack.setOnClickListener(v -> finish());
    }

    private void setupRecyclerView() {
        favoriteBooks = new ArrayList<>();
        bookAdapter = new BookAdapter(this, favoriteBooks, this);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerViewFavorites.setLayoutManager(layoutManager);
        recyclerViewFavorites.setAdapter(bookAdapter);
    }

    private void loadFavoriteBooks() {
        databaseReference.child("favorites").child(userId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        favoriteBooks.clear();
                        for (DataSnapshot bookSnapshot : snapshot.getChildren()) {
                            Book book = bookSnapshot.getValue(Book.class);
                            if (book != null) {
                                favoriteBooks.add(book);
                            }
                        }
                        bookAdapter.updateBookList(favoriteBooks);
                        updateFavoriteCount();

                        if (favoriteBooks.isEmpty()) {
                            showEmptyState();
                        } else {
                            hideEmptyState();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(FavoriteActivity.this,
                                "Lỗi: " + error.getMessage(),
                                Toast.LENGTH_SHORT).show();
                        showEmptyState();
                    }
                });
    }

    private void updateFavoriteCount() {
        int count = favoriteBooks.size();
        tvFavoriteCount.setText("Bạn có " + count + " sách yêu thích");
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
        // Xóa khỏi favorites
        databaseReference.child("favorites")
                .child(userId)
                .child(book.getId())
                .removeValue()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(FavoriteActivity.this,
                            "Đã xóa khỏi yêu thích",
                            Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(FavoriteActivity.this,
                            "Lỗi: " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                });
    }
}