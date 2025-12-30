package th.nguyenphananhtai.ungdungdocsach;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ViewPager2 viewPager;
    CarouselAdapter adapter;
    List<Integer> images;
    LinearLayout indicatorLayout;

    CardView cardLibrary, cardCategory, cardFavorite, cardSettings;

    CardView imgProfile;
    TextView tvUsername;

    Handler sliderHandler = new Handler();
    Runnable sliderRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // --- 0. QUAN TRỌNG: KHỞI TẠO USER ID NGAY KHI MỞ APP ---
        // Đoạn này đảm bảo ID luôn tồn tại trước khi vào các màn hình khác
        setupUserId();

        // --- 1. SETUP HEADER (PROFILE) ---
        imgProfile = findViewById(R.id.imgProfile);
        tvUsername = findViewById(R.id.tvUsername);

        updateUIBasedOnLoginStatus();

        imgProfile.setOnClickListener(v -> {
            if (isUserLoggedIn()) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            } else {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        // --- 2. SETUP CAROUSEL với Page Indicator ---
        viewPager = findViewById(R.id.viewPager);
        indicatorLayout = findViewById(R.id.indicatorLayout);

        images = new ArrayList<>();
        images.add(R.drawable.book1);
        images.add(R.drawable.book2);
        images.add(R.drawable.book3);

        adapter = new CarouselAdapter(this, images);
        viewPager.setAdapter(adapter);

        setupViewPagerTransformer();
        setupIndicators(images.size());
        setCurrentIndicator(0);

        // Auto slide
        sliderRunnable = new Runnable() {
            @Override
            public void run() {
                int current = viewPager.getCurrentItem();
                int total = adapter.getItemCount();
                if (total > 0) {
                    if (current < total - 1) {
                        viewPager.setCurrentItem(current + 1);
                    } else {
                        viewPager.setCurrentItem(0);
                    }
                    sliderHandler.postDelayed(this, 3500);
                }
            }
        };
        sliderHandler.postDelayed(sliderRunnable, 3500);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                setCurrentIndicator(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 3500);
                super.onPageSelected(position);
            }
        });

        // --- 3. NAVIGATION CARDS ---
        cardLibrary = findViewById(R.id.cardLibrary);
        cardCategory = findViewById(R.id.cardCategory);
        cardFavorite = findViewById(R.id.cardFavorite);
        cardSettings = findViewById(R.id.cardSettings);

        cardLibrary.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, LibraryActivity.class)));

        cardCategory.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, ReadingHistoryActivity.class)));

        cardFavorite.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, FavoriteActivity.class)));

        cardSettings.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, SettingsActivity.class)));
    }

    // --- HÀM MỚI: Tách logic tạo ID ra riêng để gọi trong onCreate ---
    private void setupUserId() {
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String userId = prefs.getString("userId", null);

        if (userId == null) {
            // Nếu chưa có ID, tạo mới và LƯU LẠI NGAY LẬP TỨC
            userId = "guest_" + System.currentTimeMillis();
            prefs.edit().putString("userId", userId).apply();
        }
    }

    // --- Cập nhật lại hàm này: Chỉ kiểm tra login, không tạo ID nữa ---
    private boolean isUserLoggedIn() {
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return prefs.getBoolean("isLoggedIn", false);
    }

    // --- Các hàm hỗ trợ giao diện khác giữ nguyên ---
    private void setupViewPagerTransformer() {
        CompositePageTransformer transformer = new CompositePageTransformer();
        transformer.addTransformer(new MarginPageTransformer(40));
        transformer.addTransformer((page, position) -> {
            float absPosition = Math.abs(position);
            if (absPosition >= 1) {
                page.setAlpha(0f);
            } else {
                page.setAlpha(1f);
                float scale = 0.85f + (1 - absPosition) * 0.15f;
                page.setScaleY(scale);
            }
        });
        viewPager.setPageTransformer(transformer);
        viewPager.setOffscreenPageLimit(3);
    }

    private void setupIndicators(int count) {
        indicatorLayout.removeAllViews();
        ImageView[] indicators = new ImageView[count];
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(8, 0, 8, 0);
        for (int i = 0; i < count; i++) {
            indicators[i] = new ImageView(this);
            indicators[i].setImageDrawable(getDrawable(R.drawable.indicator_inactive));
            indicators[i].setLayoutParams(params);
            indicatorLayout.addView(indicators[i]);
        }
    }

    private void setCurrentIndicator(int position) {
        int childCount = indicatorLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) indicatorLayout.getChildAt(i);
            if (i == position) {
                imageView.setImageDrawable(getDrawable(R.drawable.indicator_active));
            } else {
                imageView.setImageDrawable(getDrawable(R.drawable.indicator_inactive));
            }
        }
    }

    private void updateUIBasedOnLoginStatus() {
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        boolean isLoggedIn = prefs.getBoolean("isLoggedIn", false);
        String name = prefs.getString("fullName", "Khách");

        if (isLoggedIn) {
            tvUsername.setText(name);
        } else {
            tvUsername.setText("Đăng nhập ngay");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunnable, 3500);
        updateUIBasedOnLoginStatus();
    }

    @Override
    protected void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);
    }
}