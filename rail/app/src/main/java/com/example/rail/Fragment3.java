//package com.example.cat_caring;
//import android.content.Intent;
//import android.os.Bundle;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.Toast;
//import androidx.fragment.app.Fragment;
//
//
//
//public class Fragment3 extends Fragment {
//    private TextView textView;
//    private Button button;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
//        View view=inflater.inflate(R.layout.fragment3,container,false);
//        return view;
//    }
//
//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        textView=(TextView)getActivity().findViewById(R.id.textView3);
//        button=(Button)getActivity().findViewById(R.id.button3);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getActivity(),"Fragment1",Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//    }
//}