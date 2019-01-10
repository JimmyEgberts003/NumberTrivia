package com.egberts.jimmy.numbertrivia;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

class TriviaAdapter extends RecyclerView.Adapter<TriviaAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView triviaNumber;
        public TextView triviaText;

        ViewHolder(View itemView) {
            super(itemView);
            triviaNumber = itemView.findViewById(R.id.triviaNumber);
            triviaText = itemView.findViewById(R.id.triviaText);
        }
    }

    private List<TriviaItem> triviaItems;
    private Boolean reverseOrientation = false;

    TriviaAdapter(List<TriviaItem> triviaItems) {
        this.triviaItems = triviaItems;
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(i == 0 ? R.layout.trivia_item_left : R.layout.trivia_item_right, viewGroup, false);
        reverseOrientation = !reverseOrientation;
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TriviaAdapter.ViewHolder viewHolder, int i) {
        viewHolder.triviaNumber.setText(triviaItems.get(i).getNumber().toString());
        viewHolder.triviaText.setText(triviaItems.get(i).getText());
    }

    @Override
    public int getItemCount() {
        return triviaItems.size();
    }
}
