package cn.onlyloveyd.customview.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Class Description
 *
 * @author yidong (yidong@gz.csg.cn)
 * @date 2018/6/5 09:49
 */
public class TextAdapter extends RecyclerView.Adapter<TextAdapter.TextViewHolder> {

    List<String> mTextContent;

    public TextAdapter(List data) {
        mTextContent = data;
    }

    @NonNull
    @Override
    public TextViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView textView = new TextView(parent.getContext());
        textView.setTextSize(16);
        textView.setGravity(Gravity.CENTER);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new TextViewHolder(textView);
    }

    @Override
    public void onBindViewHolder(@NonNull TextViewHolder holder, int position) {
        holder.textView.setText(mTextContent.get(position));
    }

    @Override
    public int getItemCount() {
        return mTextContent.size();
    }

    class TextViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public TextViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView;
        }
    }
}
