package com.example.assessment4;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.assessment4.Models.DataServices;
import com.example.assessment4.Models.Student;
import com.example.assessment4.databinding.FragmentStudentsBinding;

import java.util.ArrayList;


public class StudentsFragment extends Fragment {

    public StudentsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //Step 1: binding & inflate
    FragmentStudentsBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentStudentsBinding.inflate(inflater, container, false);
        return binding.getRoot();
        //return inflater.inflate(R.layout.fragment_students, container, false);
    }

    //Step 2: initialize the adapter & arrayList
    ArrayAdapter<Student> adapter;
    ArrayList<Student> students = new ArrayList<>();

    //Step 3: onViewCreated
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Students");

        students.clear();
        students.addAll(DataServices.getStudents());
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, students);
        binding.listView.setAdapter(adapter);

        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Student student = students.get(position);
                mListener.sendSelectedStudent(student);
            }
        });
  }

    //Step 4: Use onAttach() and interfese
    // to go to next fragment upon click
    StudentsListener mListener;
//
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (StudentsListener) context;
    }

    interface StudentsListener{
        void sendSelectedStudent(Student student);
    }
}