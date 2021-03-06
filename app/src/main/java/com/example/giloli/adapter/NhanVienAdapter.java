package com.example.giloli.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.giloli.R;
import com.example.giloli.dao.NhanVien_DAO;
import com.example.giloli.model.NhanVien;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class NhanVienAdapter extends BaseAdapter {

    public Activity context;
        List<NhanVien> arrnhanvien;
        ArrayList<NhanVien> list;
        NhanVien_DAO dao;
    private Filter NhanvienFilter;
    public LayoutInflater inflater;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    public NhanVienAdapter(Activity context, ArrayList<NhanVien> list){
        super();
        this.context = context;
        this.arrnhanvien = list;
        this.list = list;
        this.inflater =
                (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        dao = new NhanVien_DAO(context);

}

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public static class ViewHolder {
        ImageView img;
        TextView txtMa;
        TextView txtTen;
        TextView txtNgaysinh;
        TextView txtngayvaolam;
        TextView txtSDT;
        TextView txtLuongcoban;
        ImageView imgDelete;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null)
        {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_list_nhanvien, null);
            holder.img = (ImageView) convertView.findViewById(R.id.ivIcon);
            holder.txtMa = (TextView) convertView.findViewById(R.id.tvMa);
            holder.txtTen = (TextView) convertView.findViewById(R.id.tvTen);
            holder.txtNgaysinh = (TextView) convertView.findViewById(R.id.tvNgaysinh);
            holder.txtngayvaolam = (TextView) convertView.findViewById(R.id.tvNgayvaolam);
            holder.txtSDT = (TextView) convertView.findViewById(R.id.tvSDT);
            holder.txtLuongcoban = (TextView) convertView.findViewById(R.id.tvLuongcoban);
            holder.imgDelete = (ImageView)convertView.findViewById(R.id.ivDelete);
            holder.imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dao.deleteNhanvienByID(list.get(position).getMaNV());
                    list.remove(position);
                    notifyDataSetChanged();
                }
            });
            convertView.setTag(holder);
        }
        else
            holder=(ViewHolder)convertView.getTag();
        NhanVien _entry = (NhanVien) list.get(position);
        if (position % 3 ==0) {
            holder.img.setImageResource(R.drawable.userr);
        }else if (position % 3 == 1){
            holder.img.setImageResource(R.drawable.userr);
        }else {
            holder.img.setImageResource(R.drawable.userr);
        }
        holder.txtMa.setText("Mã : "+_entry.getMaNV());
        holder.txtTen.setText("Tên : "+_entry.getTenNV());
        holder.txtNgaysinh.setText("Ngày sinh: "+_entry.getNgaySinh());
        holder.txtngayvaolam.setText("vào làm: "+sdf.format(_entry.getNgayVaoLam()));
        holder.txtSDT.setText("SDT: "+_entry.getSDT());
        holder.txtLuongcoban.setText("cơ bản: " + _entry.getLuongCoBan()+" VND");
        return convertView;
    }@Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
    public void changeDataset(ArrayList<NhanVien> items){
        this.list = items;
        notifyDataSetChanged();
    }
    public void resetData() {
        arrnhanvien = list;
    }
    public Filter getFilter() {
        if (NhanvienFilter == null)
            NhanvienFilter = new CustomFilter();
        return NhanvienFilter;
    }
    private class CustomFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            // We implement here the filter logic
            if (constraint == null || constraint.length() == 0) {
                results.values = list;
                results.count = list.size();
            }
            else {
                List<NhanVien> lsNhanvien = new ArrayList<NhanVien>();
                for ( NhanVien p : arrnhanvien) {
                    if
                    (p.getMaNV().toUpperCase().startsWith(constraint.toString().toUpperCase()))
                        lsNhanvien.add(p);
                }
                results.values = lsNhanvien;
                results.count = lsNhanvien.size();
            }
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            if (results.count == 0)
                notifyDataSetInvalidated();
            else {
                arrnhanvien = (List<NhanVien>) results.values;
                notifyDataSetChanged();
            }
        }
    }

}
