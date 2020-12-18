package com.zlxn.dl.baijiakang.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Motee
 */
public abstract class BaseContentAdapter<T,H extends BaseContentViewHolder> extends RecyclerView.Adapter<BaseContentViewHolder>{



    protected static final String TAG = BaseContentAdapter.class.getSimpleName();

    protected final Context context;

    protected  int layoutResId;

    protected List<T> datas;


    private OnItemClickListener mOnItemClickListener = null;
    private OnLongItemClicListner onItemLongClickListener;


    public  interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public  interface OnLongItemClicListner {
        void onItemClick(View view, int position);
    }

    public BaseContentAdapter(Context context, int layoutResId) {
        this(context, layoutResId, null);
    }


    public BaseContentAdapter(Context context, int layoutResId, List<T> datas) {
        this.datas = datas == null ? new ArrayList<T>() : datas;
        this.context = context;
        this.layoutResId = layoutResId;
    }




    @Override
    public BaseContentViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(layoutResId, null);
        BaseContentViewHolder vh = new BaseContentViewHolder(view,mOnItemClickListener,onItemLongClickListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(BaseContentViewHolder viewHoder, int position) {
        T item = getItem(position);
        convert((H)viewHoder, item);
    }



    @Override
    public int getItemCount() {
        if(datas==null || datas.size()<=0)
            return 0;

        return datas.size();
    }


    public T getItem(int position) {
        if (position >= datas.size()) return null;
        return datas.get(position);
    }


    public void clear(){
//        int itemCount = datas.size();
//        datas.clear();
//        this.notifyItemRangeRemoved(0,itemCount);

        for (Iterator it = datas.iterator(); it.hasNext();){

            T t = (T) it.next();
            int position = datas.indexOf(t);
            it.remove();
            notifyItemRemoved(position);
        }
    }

    /**
     * 从列表中删除某项
     * @param t
     */
    public  void removeItem(T t){

        int position = datas.indexOf(t);
        datas.remove(position);
        notifyItemRemoved(position);
    }



    public List<T> getDatas(){

        return  datas;
    }




    public void addData(List<T> datas){

        this.datas.addAll(datas);
        notifyDataSetChanged();

        //addData(0,datas);
    }

    public void addData(int position, List<T> list){

        if(list !=null && list.size()>0) {

            for (T t:list) {
                datas.add(position, t);
                notifyItemInserted(position);
            }

        }

    }



    public void refreshData(List<T> list){

        if(list !=null && list.size()>0){

            clear();
            int size = list.size();
            for (int i=0;i<size;i++){
                datas.add(i,list.get(i));
                notifyItemInserted(i);
            }

        }
    }

    public void loadMoreData(List<T> list){

        if(list !=null && list.size()>0){

            int size = list.size();
            int begin = datas.size();
            for (int i=0;i<size;i++){
                datas.add(list.get(i));
                notifyItemInserted(i+begin);
            }

        }

    }



    /**
     * Implement this method and use the helper to adapt the view to the given item.
     * @param viewHoder A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    protected abstract void convert(H viewHoder, T item);




    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;

    }

    public void setOnLongItemClickListener(OnLongItemClicListner listener) {
        this.onItemLongClickListener = listener;
    }


}
