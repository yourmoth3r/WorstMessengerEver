package com.yourmother.android.worstmessengerever.screens.messenger.chat;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.yourmother.android.worstmessengerever.R;
import com.yourmother.android.worstmessengerever.entities.Message;
import com.yourmother.android.worstmessengerever.screens.base.BaseViewHolder;

public class MessageHolder extends BaseViewHolder {

    private static final String TAG = "MessageHolder";

    private TextView mMessageText;
    private TextView mMessageDate;
    private TextView mMessageUser;
    private ImageView mMessageImage;
    private LinearLayout mLayout;
    private CardView mCardView;

    private FirebaseUser mFirebaseUser;

    public MessageHolder(@NonNull View itemView) {
        super(itemView);

        mCardView = itemView.findViewById(R.id.message_card);
        mLayout = itemView.findViewById(R.id.message_item_layout);
        mMessageText = itemView.findViewById(R.id.message_text);
        mMessageDate = itemView.findViewById(R.id.message_date);
        mMessageUser = itemView.findViewById(R.id.message_user);
        mMessageImage = itemView.findViewById(R.id.message_image);

        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    public void bind(Context context, Message message) {
        String text = message.getText();
        mMessageText.setVisibility(View.GONE);
        if (text != null && !text.isEmpty()) {
            mMessageText.setVisibility(View.VISIBLE);
            mMessageText.setText(text);
        }
        String date = DateFormat
                .format("HH:mm:ss", message.getDate()).toString();
        mMessageDate.setText(date);

        mMessageImage.setVisibility(View.GONE);
        String imageUrl = message.getImageUrl();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(itemView.getContext().getApplicationContext()).load(imageUrl)
                    .override(dpToPx(context, R.dimen.image_size_256dp))
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            mMessageImage.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            mMessageImage.setVisibility(View.VISIBLE);
                            return false;
                        }
                    })
                    .centerCrop()
                    .fitCenter()
                    .into(mMessageImage);
        }

        if (text.length() <= date.length() && message.getImageUrl() == null)
            mLayout.setOrientation(LinearLayout.HORIZONTAL);
        else
            mLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mCardView.getLayoutParams();
        if (message.getUserUid().equals(mFirebaseUser.getUid())) {
            mMessageUser.setVisibility(View.GONE);
            params.gravity = Gravity.END;
            params.leftMargin = dpToPx(context, R.dimen.message_margin_48dp);
            params.rightMargin = dpToPx(context, R.dimen.component_margin_8dp);
            mCardView.setCardBackgroundColor(context
                    .getResources().getColor(R.color.colorMessage));
        } else {
            mMessageUser.setVisibility(View.VISIBLE);
            mMessageUser.setText(message.getUsername());
            params.gravity = Gravity.START;
            params.leftMargin = dpToPx(context, R.dimen.component_margin_8dp);
            params.rightMargin = dpToPx(context, R.dimen.message_margin_48dp);
            mCardView.setCardBackgroundColor(Color.WHITE);
        }
        params.topMargin = 0;
        mCardView.setLayoutParams(params);
    }

    public void bindFirst(Context context, Message message) {
        bind(context, message);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mCardView.getLayoutParams();
        if (message.getUserUid().equals(mFirebaseUser.getUid())) {
            params.topMargin = dpToPx(context, R.dimen.component_margin_8dp);
        } else
            params.topMargin = 0;

        mCardView.setLayoutParams(params);
    }
}
