package com.example.ndyducwallet;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.ndyducwallet.modles.Tokens_Data;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

public class NewsHelper {

    public static void getNews(Context context, LinearLayout parentLayout) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document document = Jsoup.connect("https://www.coindesk.com/livewire/").get();
                    Elements headlineElements = document.select(".side-cover-cardstyles__SideCoverCardWrapper-sc-1nd3s5z-0.hKtOz.side-cover-card");

                    for (Element headlineElement : headlineElements) {
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                LinearLayout zlayout = createLinearLayout(parentLayout, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,0,10);
                                zlayout.setOrientation(LinearLayout.VERTICAL);

                                LinearLayout alayout = createLinearLayout(zlayout, LinearLayout.LayoutParams.WRAP_CONTENT, 23,0,0);
                                alayout.setOrientation(LinearLayout.HORIZONTAL);
                                zlayout.addView(alayout);

                                TextView timeTextView = createTextView(alayout, 80, ViewGroup.LayoutParams.MATCH_PARENT);
                                timeTextView.setText(headlineElement.select(".typography__StyledTypography-sc-owin6q-0.iOUkmj").text());
                                timeTextView.setMaxLines(1);
                                alayout.addView(timeTextView);

                                TextView dot = createTextView(alayout, ViewGroup.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                                dot.setText(" .  ");
                                alayout.addView(dot);

                                TextView titleTextView = createTextView(alayout, 272, LinearLayout.LayoutParams.MATCH_PARENT);
                                titleTextView.setText(headlineElement.select(".typography__StyledTypography-sc-owin6q-0.dtjHgI").text());
                                titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                                titleTextView.setMaxLines(1);
                                titleTextView.setEllipsize(TextUtils.TruncateAt.END);
                                alayout.addView(titleTextView);

                                LinearLayout blayout = createLinearLayout(zlayout,ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,10,0);
                                blayout.setOrientation(LinearLayout.HORIZONTAL);
                                zlayout.addView(blayout);

                                TextView contentTextView = createTextView(blayout, 240, 80);
                                contentTextView.setText(headlineElement.select(".typography__StyledTypography-sc-owin6q-0.kDZZDY").text());
                                contentTextView.setMaxLines(5);
                                titleTextView.setEllipsize(TextUtils.TruncateAt.END);
                                blayout.addView(contentTextView);

                                ImageView pic = createimageview(blayout);
                                Picasso.get().load(headlineElement.select("img").attr("src")).into(pic);
                                blayout.addView(pic);



                                parentLayout.addView(zlayout);
                            }
                        });
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public interface NewsListener {
        void onNewsLoaded(Tokens_Data[] newsData);
    }


    @NonNull
    private static LinearLayout createLinearLayout(LinearLayout parentLayout, int widthDp, int heightDp, int leftMarginDp, int bottomMarginDp) {
        LinearLayout zlayout = new LinearLayout(parentLayout.getContext());
        int widthPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, widthDp, parentLayout.getResources().getDisplayMetrics());
        int heightPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, heightDp, parentLayout.getResources().getDisplayMetrics());
        int leftMarginPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftMarginDp, parentLayout.getResources().getDisplayMetrics());
        int bottomMarginPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, bottomMarginDp, parentLayout.getResources().getDisplayMetrics());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(widthPx, heightPx);
        layoutParams.setMargins(leftMarginPx, 0, 0, bottomMarginPx);
        zlayout.setLayoutParams(layoutParams);

        return zlayout;
    }

    @NonNull
    private static TextView createTextView(ViewGroup parentLayout, int widthDp, int heightDp) {
        TextView textView = new TextView(parentLayout.getContext());
        int widthPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, widthDp, parentLayout.getResources().getDisplayMetrics());
        int heightPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, heightDp, parentLayout.getResources().getDisplayMetrics());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(widthPx, heightPx);
        textView.setLayoutParams(layoutParams);
        textView.setTextColor(Color.parseColor("#e1f1ff"));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);

        return textView;
    }


    @NonNull
    private static ImageView createimageview(ViewGroup parentLayout) {
        ImageView imageView = new ImageView(parentLayout.getContext());
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        int widthInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 120, parentLayout.getResources().getDisplayMetrics());
        int heightInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80, parentLayout.getResources().getDisplayMetrics());
        imageView.setLayoutParams(new ViewGroup.LayoutParams(widthInDp, heightInDp));

        return imageView;
    }

}
