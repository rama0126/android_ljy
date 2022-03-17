package com.cookandroid.bdchat.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cookandroid.bdchat.Models.MessageModel;
import com.cookandroid.bdchat.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter {

    ArrayList<MessageModel> messageModels;
    Context context;
    String recId;


    int SENDER_VIEW_TYPE = 1;
    int RECEIVER_VIEW_TYPE = 2;


    public ChatAdapter(ArrayList<MessageModel> messageModels, Context context) {
        this.messageModels = messageModels;
        this.context = context;
    }

    public ChatAdapter(ArrayList<MessageModel> messageModels, Context context, String recId) {
        this.messageModels = messageModels;
        this.context = context;
        this.recId = recId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == SENDER_VIEW_TYPE){
            View view = LayoutInflater.from(context).inflate(R.layout.sample_sender,parent,false);
            return new SenderViewHolder(view);
        }
        else{
            View view = LayoutInflater.from(context).inflate(R.layout.sample_reciever,parent,false);
            return new RecieverViewHolder(view);
        }
    }
    //뷰 타입을 알아내서 보낸 사람 메시지인지 받는사람 메시지인지 구별
    @Override
    public int getItemViewType(int position) {

        if(messageModels.get(position).getuId().equals(FirebaseAuth.getInstance().getUid()))
        {
            return SENDER_VIEW_TYPE;
        }
        else{
            return RECEIVER_VIEW_TYPE;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageModel messageModel = messageModels.get(position);

        if(holder.getClass() == SenderViewHolder.class){
            ((SenderViewHolder) holder).senderMsg.setText(messageModel.getMessage());

        }
        else{
            ((RecieverViewHolder) holder).recieverMsg.setText(messageModel.getMessage());
        }
    }
    // 메시지 모델 사이즈
    @Override
    public int getItemCount() {
        return messageModels.size();
    }

    // 받는 이 뷰
    public class RecieverViewHolder extends RecyclerView.ViewHolder{
        TextView recieverMsg, recieverTime;

        public RecieverViewHolder(@NonNull View itemView) {
            super(itemView);

            recieverMsg = itemView.findViewById(R.id.receiverText);
            recieverTime = itemView.findViewById(R.id.receiverTime);

        }
    }
    // 보내는 이 뷰
    public class SenderViewHolder extends RecyclerView.ViewHolder{
        TextView senderMsg, senderTime;
        public SenderViewHolder(@NonNull View itemView){
            super(itemView);

            senderMsg = itemView.findViewById(R.id.senderText);
            senderTime = itemView.findViewById(R.id.senderTime);
        }

    }

}
