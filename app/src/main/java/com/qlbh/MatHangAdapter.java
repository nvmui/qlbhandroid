package com.qlbh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class MatHangAdapter extends BaseAdapter {
    private ActivityBanhang context;
    private int layout;
    private List<Mathang> listmathangs;

    public MatHangAdapter(ActivityBanhang context, int layout, List<Mathang> mathangs) {
        this.context = context;
        this.layout = layout;
        this.listmathangs = mathangs;
    }

    @Override
    public int getCount() {
        return listmathangs.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    private class ViewHolder {
        TextView txtTenHang, txtDVT, txtDonGia;
        EditText editSL;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.txtTenHang = (TextView) view.findViewById(R.id.textTenHang);
            holder.txtDVT = (TextView) view.findViewById(R.id.textDVT);
            holder.editSL = (EditText) view.findViewById(R.id.editSL);
            holder.txtDonGia = (TextView) view.findViewById(R.id.textDonGia);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        Mathang mathang = listmathangs.get(i);
        holder.txtTenHang.setText(mathang.TenMH);
        holder.txtDVT.setText(mathang.DVT);
        holder.editSL.setText("1");
        holder.txtDonGia.setText("" + mathang.DonGia);
        return view;
    }
}
