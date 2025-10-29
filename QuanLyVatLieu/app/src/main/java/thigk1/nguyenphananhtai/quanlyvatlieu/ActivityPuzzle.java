package thigk1.nguyenphananhtai.quanlyvatlieu;

import android.os.Bundle;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;

public class ActivityPuzzle extends AppCompatActivity {

    GridLayout gridLayout;
    ImageButton btnBack;
    Button btnRestart;
    ArrayList<Integer> pieces;  // Danh sách mảnh ghép
    ImageView firstSelected = null;
    int firstIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);

        gridLayout = findViewById(R.id.gridLayoutPuzzle);
        btnBack = findViewById(R.id.btnBack);
        btnRestart = findViewById(R.id.btnRestart);

        // Quay lại Activity trước
        btnBack.setOnClickListener(v -> onBackPressed());

        // Chơi lại (xáo trộn lại puzzle)
        btnRestart.setOnClickListener(v -> resetPuzzle());

        setupPuzzle();
    }

    /**
     * Tạo puzzle mới và hiển thị lên GridLayout
     */
    private void setupPuzzle() {
        pieces = new ArrayList<>();

        // Thêm các mảnh ảnh vào danh sách (9 mảnh)
        pieces.add(R.drawable.manh1);
        pieces.add(R.drawable.manh2);
        pieces.add(R.drawable.manh3);
        pieces.add(R.drawable.manh4);
        pieces.add(R.drawable.manh5);
        pieces.add(R.drawable.manh6);
        pieces.add(R.drawable.manh7);
        pieces.add(R.drawable.manh8);
        pieces.add(R.drawable.manh9);

        // Xáo trộn ngẫu nhiên
        Collections.shuffle(pieces);

        // Xóa toàn bộ view cũ trước khi thêm lại (nếu có)
        gridLayout.removeAllViews();

        // Tạo lưới 3x3
        for (int i = 0; i < pieces.size(); i++) {
            ImageView img = new ImageView(this);
            img.setImageResource(pieces.get(i));

            // Kích thước từng mảnh (Grid 3x3 trong khung 330dp)
            int pieceSize = (int) (330 / 3 * getResources().getDisplayMetrics().density); // chia 3 hàng
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = pieceSize;
            params.height = pieceSize;
            params.setMargins(4, 4, 4, 4);

            img.setLayoutParams(params);
            img.setScaleType(ImageView.ScaleType.CENTER_CROP); // cắt vừa ô, không kéo giãn
            img.setBackgroundResource(R.drawable.puzzle_piece_border); // viền phân biệt

            int index = i;
            img.setOnClickListener(v -> onPieceClick(index, img));

            gridLayout.addView(img);
        }


    }

    /**
     * Xử lý khi người chơi nhấn vào 1 mảnh ghép
     */
    private void onPieceClick(int index, ImageView img) {
        if (firstSelected == null) {
            // Lần đầu chọn
            firstSelected = img;
            firstIndex = index;
            img.setAlpha(0.5f); // làm mờ để biết đang chọn
        } else {
            // Lần thứ 2 => đổi chỗ
            ImageView secondSelected = img;
            int secondIndex = index;

            // Hoán đổi trong danh sách
            int temp = pieces.get(firstIndex);
            pieces.set(firstIndex, pieces.get(secondIndex));
            pieces.set(secondIndex, temp);

            // Cập nhật lại hình ảnh
            firstSelected.setImageResource(pieces.get(firstIndex));
            secondSelected.setImageResource(pieces.get(secondIndex));

            // Reset chọn
            firstSelected.setAlpha(1.0f);
            firstSelected = null;
            firstIndex = -1;

            // Kiểm tra chiến thắng
            if (checkWin()) {
                Toast.makeText(this, "🎉 Bạn đã hoàn thành logo NTU!", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * Kiểm tra người chơi đã ghép đúng chưa
     */
    private boolean checkWin() {
        for (int i = 0; i < pieces.size(); i++) {
            int correctId = getResources().getIdentifier("manh" + (i + 1), "drawable", getPackageName());
            if (pieces.get(i) != correctId) {
                return false;
            }
        }
        return true;
    }

    /**
     * Reset lại trò chơi (xáo lại ảnh và hiển thị lại)
     */
    private void resetPuzzle() {
        Toast.makeText(this, "🔄 Trò chơi đã được làm mới!", Toast.LENGTH_SHORT).show();

        firstSelected = null;
        firstIndex = -1;

        // Xáo trộn lại danh sách
        Collections.shuffle(pieces);

        // Xóa view cũ
        gridLayout.removeAllViews();

        // Hiển thị lại các mảnh mới
        for (int i = 0; i < pieces.size(); i++) {
            ImageView img = new ImageView(this);
            img.setImageResource(pieces.get(i));

            // Kích thước từng mảnh (Grid 3x3 trong khung 330dp)
            int pieceSize = (int) (330 / 3 * getResources().getDisplayMetrics().density); // chia 3 hàng
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = pieceSize;
            params.height = pieceSize;
            params.setMargins(4, 4, 4, 4);

            img.setLayoutParams(params);
            img.setScaleType(ImageView.ScaleType.CENTER_CROP); // cắt vừa ô, không kéo giãn
            img.setBackgroundResource(R.drawable.puzzle_piece_border); // viền phân biệt

            int index = i;
            img.setOnClickListener(v -> onPieceClick(index, img));

            gridLayout.addView(img);
        }
    }
}
