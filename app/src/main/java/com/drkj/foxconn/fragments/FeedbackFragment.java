package com.drkj.foxconn.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.drkj.foxconn.R;
import com.drkj.foxconn.activties.CheckActivity;
import com.drkj.foxconn.activties.FeedbackActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

public class FeedbackFragment extends Fragment {
    @BindView(R.id.listview_feedback)
    ListView listView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @OnClick(R.id.image_create_feedback)
    void createFeedback(){
        startActivity(new Intent(getContext(), FeedbackActivity.class));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MyAdapter adapter = new MyAdapter();
        listView.setAdapter(adapter);
    }

    @OnItemClick(R.id.listview_feedback)
    void itemClick(int postion){
        startActivity(new Intent(getActivity(), FeedbackActivity.class));
    }
    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MyViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_feedback_list, null);
                holder = new MyViewHolder();
                holder.equipmentLocationText = convertView.findViewById(R.id.text_equipment_location);
                holder.equipmentNameText = convertView.findViewById(R.id.text_equipment_name);
                holder.layout = convertView.findViewById(R.id.layout_item);
                convertView.setTag(holder);
            }else {
                holder = (MyViewHolder) convertView.getTag();
            }
            switch (position%3){
                case 0:
                    holder.layout.setBackgroundResource(R.drawable.ic_orange_bg);
                    break;
                case 1:
                    holder.layout.setBackgroundResource(R.drawable.ic_green_bg);
                    break;
                case 2:
                    holder.layout.setBackgroundResource(R.drawable.ic_blue_bg);
                    break;
            }
            return convertView;
        }
    }

    class MyViewHolder {
        public TextView equipmentLocationText;
        public TextView equipmentNameText;
        public LinearLayout layout;
    }
}
