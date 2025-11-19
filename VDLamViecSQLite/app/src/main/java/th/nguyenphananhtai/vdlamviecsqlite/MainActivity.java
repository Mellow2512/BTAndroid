package th.nguyenphananhtai.vdlamviecsqlite;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        SQLiteDatabase db = openOrCreateDatabase("books.db", MODE_PRIVATE, null);

        String sqlTaoBang = "CREATE TABLE BOOKS(id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, author TEXT, page INTEGER, price FLOAT, description TEXT)";

        String sqlXoaBang = "DROP TABLE IF EXISTS books";

        db.execSQL(sqlXoaBang);
        db.execSQL(sqlTaoBang);

        db.close();
    }
}