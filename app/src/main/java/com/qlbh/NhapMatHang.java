package com.qlbh;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class NhapMatHang extends BaseAdapter {
    private ActivityNhapMathang context;
    private int layout;
    private List<Mathang> listmathangs;

    public NhapMatHang(ActivityNhapMathang context, int layout, List<Mathang> listmathangs) {
        this.context = context;
        this.layout = layout;
        this.listmathangs = listmathangs;
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
        TextView txtTenHang, txtDVT, txtDonGia, tvSL;
        EditText editSL;
        ImageView imgEdit, imgDelete;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        NhapMatHang.ViewHolder holder;
        if (view == null) {
            holder = new NhapMatHang.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.txtTenHang = (TextView) view.findViewById(R.id.tvTenHang);
            holder.tvSL = (TextView) view.findViewById(R.id.tvSL);
            holder.txtDonGia = (TextView) view.findViewById(R.id.tvDG);
            holder.imgEdit = (ImageView) view.findViewById(R.id.im_edit);
            holder.imgDelete = (ImageView) view.findViewById(R.id.im_delete);
            view.setTag(holder);
        } else {
            holder = (NhapMatHang.ViewHolder) view.getTag();
        }
        final Mathang mathang = listmathangs.get(i);
        holder.txtTenHang.setText(mathang.TenMH);
        holder.tvSL.setText(mathang.SL + "");
        holder.txtDonGia.setText(mathang.DonGia);
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.editMaMH.setText(mathang.MaMH);
                context.editTenhang.setText(mathang.TenMH);
                context.editSoluong.setText(mathang.SL + "");
                context.editDongia.setText(mathang.DonGia);
                context.editGhichu.setText(mathang.GhiChu);
            }
        });
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                XacNhanXoa(mathang.TenMH, mathang.MaMH);
            }
        });
        return view;
    }

    //Xác nhận xoá mặt hàng
    private void XacNhanXoa(String ten, final String mamh) {
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(context);
        dialogXoa.setMessage("Bạn có muốn xoá bản ghi " + ten + " và có mã là " + mamh + " Không?");
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String kt = String.valueOf(new DeleteMatHang(mamh).execute(new urlconfig().url + "deleteMatHang.php"));
                if (kt.equals("success")) {
                    Toast.makeText(context, "" + kt, Toast.LENGTH_LONG).show();
                    context.getMatHang();
                } else {
                    Toast.makeText(context, "" + kt, Toast.LENGTH_LONG).show();
                }
            }
        });
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialogXoa.show();
    }
}
