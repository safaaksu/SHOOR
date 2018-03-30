package com.shoor.shoor;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

public class HLVAdapter extends RecyclerView.Adapter<HLVAdapter.ViewHolder> {

        ArrayList<Review> reviews;
        Context context;

public HLVAdapter(Context context, ArrayList<Review> data) {
        super();
        this.context = context;
        this.reviews =data;
        }

@Override
public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.review, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
        }

@Override
public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.UserName.setText(reviews.get(i).getUserName());
        viewHolder.Comment.setText(reviews.get(i).getComment());
        viewHolder.RateScore.setRating(reviews.get(i).getRateScore());
    }

@Override
public int getItemCount() {
        return reviews.size();
        }

public static class ViewHolder extends RecyclerView.ViewHolder   {

    public TextView UserName;
    public TextView Comment;
    public RatingBar RateScore;

    public ViewHolder(View itemView) {
        super(itemView);
        UserName = (TextView) itemView.findViewById(R.id.UserNameReview);
        Comment = (TextView) itemView.findViewById(R.id.UserCommentReview);
        RateScore =(RatingBar) itemView.findViewById(R.id.UserRatingReview);

    }


}

}
