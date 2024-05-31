package com.example.ndyducwallet.viewmodles;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ndyducwallet.R;
import com.example.ndyducwallet.modles.Django;
import com.example.ndyducwallet.modles.Tokens_Data;
import com.example.ndyducwallet.modles.Users;
import com.example.ndyducwallet.views.Market;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Token_Adapter extends RecyclerView.Adapter<Token_Adapter.ViewHolder> {
    private ArrayList<Tokens_Data> Tokenlist;
    private Context context;

    public Token_Adapter(Context context, ArrayList<Tokens_Data> Tokenlist){
        this.context = context;
        this.Tokenlist = Tokenlist;
    }

    @NonNull
    @Override
    public Token_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alert_token, parent, false);
        return new ViewHolder(view);
    }

    public void updateList(List<Tokens_Data> newList) {
        Tokenlist.clear();
        Tokenlist.addAll(newList);
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(@NonNull Token_Adapter.ViewHolder holder, int position) {
        Tokens_Data token = Tokenlist.get(position);
        holder.Token_Name.setText(String.valueOf(token.getSymbol()));
        Picasso.get().load(token.getLogoUrl()).into(holder.Token_Url);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click_card(position);
            }
        });

        holder.Token_Url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click_card(position);
            }
        });

        holder.Token_Name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click_card(position);
            }
        });
    }

    public void click_card(int position){
        if (position != RecyclerView.NO_POSITION) {
            Log.d("JSON", Tokenlist.get(position).getSymbol());
            Repository repository = Repository.getInstance();
            if (repository != null) {
                repository.setUrl_logot(Tokenlist.get(position).getLogoUrl());
            }
            Market.updateCurrentToken(context, Tokenlist.get(position).getSymbol());
        }
    }

    @Override
    public int getItemCount() {
        return Tokenlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Token_Name;
        ImageView Token_Url;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Token_Name = itemView.findViewById(R.id.txtname);
            Token_Url = itemView.findViewById(R.id.imglogo);


        }
    }
}
