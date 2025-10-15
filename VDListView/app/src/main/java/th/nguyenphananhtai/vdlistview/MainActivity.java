package th.nguyenphananhtai.vdlistview;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lvGame;

    void TimDK(){
        lvGame = findViewById(R.id.lvGame);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        TimDK();

        ArrayList<String> arrGame = new ArrayList<>();
        arrGame = getData();

        ArrayAdapter<String> gameAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrGame);

        lvGame.setAdapter(gameAdapter);

        lvGame.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String gameClicked;
                gameClicked = gameAdapter.getItem(position).toString();

                String thongBao = "Bạn chọn chơi game " + gameClicked;
                Toast.makeText(MainActivity.this, thongBao, Toast.LENGTH_LONG).show();
            }
        });
        }
        ArrayList<String> getData() {
            ArrayList<String> arr = new ArrayList<String>();
            arr.add("PUBG");
            arr.add("Valorant");
            arr.add("League of Legends");
            arr.add("Teamfight tactics");
            arr.add("League of Runeterra");
            return arr;
    }
}