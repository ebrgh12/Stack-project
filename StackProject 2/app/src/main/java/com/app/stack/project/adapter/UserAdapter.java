package com.app.stack.project.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.app.stack.project.R;
import com.app.stack.project.model.Item;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<Item> items;

    public UserAdapter(List<Item> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new UserViewHolder(
                LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.layout_listitem, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final UserViewHolder userViewHolder, int i) {
        Item item = items.get(i);
        userViewHolder.userTV.setText(item.getDisplayName());
        userViewHolder.goldTV.setText("Gold - "+item.getBadgeCounts().getGold());
        userViewHolder.silverTV.setText("Silver - "+item.getBadgeCounts().getSilver());
        userViewHolder.bronzeTV.setText("Bronze - "+item.getBadgeCounts().getBronze());
        if(item.getProfileImage() != null) {
            Glide.with(userViewHolder.itemView.getContext())
                    .load(item.getProfileImage())
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            Log.e("UserAdapter","Exception",e);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            userViewHolder.progressBar.setVisibility(View.GONE);
                            userViewHolder.userIV.setImageDrawable(resource);
                            return true;
                        }
                    })
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .crossFade()
                    .into(userViewHolder.userIV);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {

        private TextView userTV;
        private TextView goldTV;
        private TextView silverTV;
        private TextView bronzeTV;
        private ImageView userIV;
        private ProgressBar progressBar;


        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            userTV = itemView.findViewById(R.id.userTV);
            goldTV = itemView.findViewById(R.id.goldTV);
            silverTV = itemView.findViewById(R.id.silverTV);
            bronzeTV = itemView.findViewById(R.id.bronzeTV);
            userIV = itemView.findViewById(R.id.userIV);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }
}
