package th.nguyenphananhtai.vdfragmentreplace;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class FooterFragment extends Fragment {



    public FooterFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_footer, container, false);

        Button btnOne = v.findViewById(R.id.btn1);
        Button btnTwo = v.findViewById(R.id.btn2);
        Button btnThree = v.findViewById(R.id.btn3);

        FragmentManager fragmentManager = getParentFragmentManager();

        btnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction()
                        .replace(R.id.contentFragment_container, new Fragment1())
                        .commit();
            }
        });

        btnTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction()
                        .replace(R.id.contentFragment_container, new Fragment2())
                        .commit();
            }
        });

        btnThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction()
                        .replace(R.id.contentFragment_container, new Fragment3())
                        .commit();
            }
        });

        return v;
    }
}