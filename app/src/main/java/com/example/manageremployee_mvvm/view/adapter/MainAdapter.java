package com.example.manageremployee_mvvm.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.manageremployee_mvvm.R;
import com.example.manageremployee_mvvm.model.Employee;
import com.example.manageremployee_mvvm.view.main.IOnClickListeners;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {
    private Context mContext;
    private List<Employee> employerList;
    private IOnClickListeners<Employee> mListener;

    public MainAdapter(Context mContext, List<Employee> employerList) {
        this.mContext = mContext;
        this.employerList = employerList;
        if (mContext instanceof IOnClickListeners) {
            this.mListener = (IOnClickListeners) mContext;
        }
    }

    public void setListEmployer(List<Employee> employerList) {
        this.employerList = employerList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MainAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_employee, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Employee employer = employerList.get(position);
        holder.tvName.setText(employer.getEmployeeName());
        holder.tvAge.setText(employer.getEmployAge());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClick(employer);
            }
        });
    }

    public void setEmployeelList(List<Employee> employers) {
        if (employerList == null) {
            employerList = new ArrayList<>();
            employerList.addAll(employers);
            notifyDataSetChanged();
        } else {
            employerList.clear();
            employerList.addAll(employers);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return employerList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvAge;
        CircleImageView imgEmployee;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.txt_name_employee);
            tvAge = itemView.findViewById(R.id.txt_age_employee);
            imgEmployee = itemView.findViewById(R.id.img_employee);

        }
    }

}
