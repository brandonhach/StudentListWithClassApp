package com.example.assessment4;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.assessment4.Models.CourseHistory;
import com.example.assessment4.Models.Student;
import com.example.assessment4.databinding.FragmentStudentHistoryBinding;
import com.example.assessment4.databinding.HistoryRowItemBinding;

import java.util.ArrayList;
import java.util.List;

public class StudentHistoryFragment extends Fragment {
    private static final String ARG_PARAM_STUDENT = "ARG_PARAM_STUDENT";

    private Student mStudent;

    public StudentHistoryFragment() {
        // Required empty public constructor
    }

    /*
    *   create new instance of StudentHistoryFragment that contains the student obj passed as a parameter.
    *   Student object is now retrievable using onCreate()
    *   obj used to display student history
    * */
    public static StudentHistoryFragment newInstance(Student student) {
        StudentHistoryFragment fragment = new StudentHistoryFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_STUDENT, student);
        fragment.setArguments(args);
        return fragment;
    }

    /*
    *   Student object is retrieved from the arguments and assigned to the mStudent field.
    *   serial used to pass data
    * */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mStudent = (Student) getArguments().getSerializable(ARG_PARAM_STUDENT);
        }
    }

    // binding
    FragmentStudentHistoryBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentStudentHistoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    ArrayList<CourseHistory> courseHistories;
    //adapter
    CourseHistoryAdapter adapter;

    /*
    * the TextView for the student's name is set to the name of the Student object.
    * the ListView for the course histories is set up with a custom ArrayAdapter.
    *
    * */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Student History");

        binding.textViewStudentName.setText(mStudent.getName());

        courseHistories = mStudent.getCourses();

        adapter = new CourseHistoryAdapter(getActivity(), R.layout.history_row_item, courseHistories);
        binding.listView.setAdapter(adapter);
    }

    //custom adapter
    /*
    *   a custom ArrayAdapter class for displaying the course histories in a ListView.
    *
    * */
    class CourseHistoryAdapter extends ArrayAdapter<CourseHistory> {
        public CourseHistoryAdapter(@NonNull Context context, int resource, @NonNull List<CourseHistory> objects) {
            super(context, resource, objects);
        }


        /*
        * get view for each item in the ListView.
        * HistoryRowItemBinding object is initialized by inflating the layout for each row item.
        * TextViews in each row item are then set to the details of the CourseHistory object at the current position from the list.
        * THen returns the convertView which is the view for the current row item.
        * */
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            HistoryRowItemBinding itemBinding;
            if (convertView == null) {
                itemBinding = HistoryRowItemBinding.inflate(getLayoutInflater(), parent, false);
                convertView = itemBinding.getRoot();
                convertView.setTag(itemBinding);
            } else {
                itemBinding = (HistoryRowItemBinding) convertView.getTag();
            }

            CourseHistory courseHistory = getItem(position);

            itemBinding.textViewCourseName.setText(courseHistory.getName());
            itemBinding.textViewCourseNumber.setText(courseHistory.getNumber());
            itemBinding.textViewCourseLetterGrade.setText(courseHistory.getLetterGrade());
            itemBinding.textViewCourseHours.setText(String.valueOf(courseHistory.getHours()) + " Hours");

            return convertView;
        }
    }
}