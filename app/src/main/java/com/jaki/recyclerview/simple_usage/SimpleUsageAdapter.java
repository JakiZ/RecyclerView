package com.jaki.recyclerview.simple_usage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.jaki.recyclerview.R;

import java.util.List;

/**
 * usage: 奇数序号的显示成绩，不显示性别更改按钮，偶数相反
 * created at 2018/6/27 下午3:04 by Jaki
 * email 654641423@qq.com
 */
public class SimpleUsageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = SimpleUsageAdapter.class.getSimpleName();
    private static final int TYPE_ONE = 0;
    private static final int TYPE_TWO = 1;
    private Context context;
    private List<SimpleUsageBean> list;

    public SimpleUsageAdapter(Context context, List<SimpleUsageBean> list) {
        this.context = context;
        this.list = list;
    }

    /**
     * 创建子布局的样式，并返回对应的ViewHolder
     * @param parent 就是RecyclerView
     * @param viewType 子布局类型,参见方法 getItemViewType(int position)
     * @return
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ONE){
            final View view = LayoutInflater.from(context).inflate( R.layout.adapter_simple_usage_1, parent,false);
            return new SimpleUsageViewHolder(view);
        }else if (viewType == TYPE_TWO){
            View view = LayoutInflater.from(context).inflate( R.layout.adapter_simple_usage_2, parent,false);
            return new SimpleUsageViewHolder2(view);
        }
        return null;
    }




    /**
     * 获取对应position位置的子布局的样式
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return position % 2 == 0 ? TYPE_ONE : TYPE_TWO;
    }

    /**
     * 数据绑定ViewHolder
     * @param holder 对应的viewholder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        SimpleUsageBean bean = list.get(position);
        setRecyclerViewClick(holder.itemView,position);
        if (bean != null){
            if (holder instanceof SimpleUsageViewHolder){
                ((SimpleUsageViewHolder) holder).tv.setText("name=" + bean.getName() + ",score=" + bean.getScore());
            }else if (holder instanceof SimpleUsageViewHolder2){
                ((SimpleUsageViewHolder2) holder).male.setOnCheckedChangeListener(null);
                ((SimpleUsageViewHolder2) holder).female.setOnCheckedChangeListener(null);

                ((SimpleUsageViewHolder2) holder).tv.setText("name=" +bean.getName());
                boolean gender = bean.isGender();
                if (gender){
                    ((SimpleUsageViewHolder2) holder).female.setChecked(gender);
                }else {
                    ((SimpleUsageViewHolder2) holder).male.setChecked(gender);
                }

                ((SimpleUsageViewHolder2) holder).male.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        list.get(position).setGender(isChecked);
                    }
                });


                ((SimpleUsageViewHolder2) holder).female.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        list.get(position).setGender(isChecked);
                    }
                });

            }
        }

    }

    public void updateData(List<SimpleUsageBean > list){
        this.list = list;
        notifyDataSetChanged();
    }

    public void addData(SimpleUsageBean bean){
        list.add(0,bean);
        notifyDataSetChanged();
    }

    public void deleteData(SimpleUsageBean bean){
        list.remove(bean);
        notifyDataSetChanged();
    }

    public void deleteData(int index){
        list.remove(index);
        notifyDataSetChanged();
    }

    /**
     * 设置RecyclerView的点击事件
     * @param view
     * @param position
     */
    private void setRecyclerViewClick(final View view,final int position){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClick != null){
                    onItemClick.onItemClick(view,position);
                }
            }
        });

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemClick != null){
                    onItemClick.onItemLongClick(v,position);
                }
                return true;
            }
        });

    }

    /**
     * 确定子布局的总数
     * @return  子布局的总数
     */
    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    /**
     * item的单行布局viewHolder 类型1
     */
    static class SimpleUsageViewHolder extends RecyclerView.ViewHolder{
        private ImageView iv;
        private TextView tv;

        public SimpleUsageViewHolder(View itemView) {
            super(itemView);
            tv = ((TextView) itemView.findViewById(R.id.textView2));
        }
    }

    /**
     * item的单行布局viewHolder 类型2
     */
    static class SimpleUsageViewHolder2 extends RecyclerView.ViewHolder{
        private ImageView iv;
        private TextView tv;
        private RadioButton male;
        private RadioButton female;

        public SimpleUsageViewHolder2(View itemView) {
            super(itemView);
            tv = ((TextView) itemView.findViewById(R.id.textView));
            male = (RadioButton) itemView.findViewById(R.id.male);
            female = (RadioButton) itemView.findViewById(R.id.female);
        }
    }


    public interface OnItemClick{
        void onItemClick(View view,int position);
        void onItemLongClick(View view,int position);
    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick){
        this.onItemClick = onItemClick;
    }
}
