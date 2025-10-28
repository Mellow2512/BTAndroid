package th.nguyenphananhtai.recyclerview;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    LandScapeAdapter landScapeAdapter;

    ArrayList<LandScape> lstData;

    RecyclerView recyclerLand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lstData = getData();
        landScapeAdapter = new LandScapeAdapter(this,lstData);

        recyclerLand = findViewById(R.id.recyclerLand);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        recyclerLand.setLayoutManager(layoutManager);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
//        recyclerLand.setLayoutManager(layoutManager);

        recyclerLand.setAdapter(landScapeAdapter);
    }

    ArrayList<LandScape> getData(){
        ArrayList<LandScape> lstData = new ArrayList<>();
        lstData.add(new LandScape("land1","Page 1"));
        lstData.add(new LandScape("land2","Page 2"));
        lstData.add(new LandScape("land3","Page 3"));
        lstData.add(new LandScape("land4","Page 4"));
        lstData.add(new LandScape("land5","Page 5"));
        return lstData;
    }
}