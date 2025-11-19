package th.nguyenphananhtai.vdlamviecsqlite;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ArrayList<String> booksNameList = getBooksData();

        ListView listView = findViewById(R.id.lvBooksList);
        ArrayAdapter<String> adapterBooksName = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, booksNameList);
        listView.setAdapter(adapterBooksName);

        Button bThem = findViewById(R.id.bThem);
        bThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edtText = findViewById(R.id.edtTenSach);
                String title = edtText.getText().toString();

                EditText edtPage = findViewById(R.id.edtSoTrang);
                int page = Integer.parseInt(edtPage.getText().toString());

                EditText edtPrice = findViewById(R.id.edtGia);
                float price = Float.parseFloat(edtPrice.getText().toString());

                EditText edtDescription = findViewById(R.id.edtMoTa);
                String description = edtDescription.getText().toString();

                ContentValues row = new ContentValues();
                row.put("title", title);
                row.put("page", page);
                row.put("price", price);
                row.put("description", description);

                SQLiteDatabase db = openOrCreateDatabase("books.db", MODE_PRIVATE, null);
                db.insert("books", null, row);

                adapterBooksName.notifyDataSetChanged();

                db.close();
            }
        });
    }

    ArrayList<String> getBooksData(){
        SQLiteDatabase db = openOrCreateDatabase("books.db", MODE_PRIVATE, null);

        String sqlSelectAll = "SELECT * FROM books";
        Cursor cursor = db.rawQuery(sqlSelectAll, null);
        ArrayList<Books> booksList = new ArrayList<Books>();
        cursor.moveToFirst();
        while(true){
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            int page = cursor.getInt(2);
            float price = cursor.getFloat(3);
            String description = cursor.getString(4);

            Books b = new Books(id, title, page, price, description);
            booksList.add(b);

            if (cursor.moveToNext() == false) break;
        }
        db.close();
        return getBooksData();
    }
}