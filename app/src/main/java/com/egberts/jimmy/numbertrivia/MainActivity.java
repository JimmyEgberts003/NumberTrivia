package com.egberts.jimmy.numbertrivia;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView triviaView;
    private TriviaAdapter triviaAdapter;
    private List<TriviaItem> triviaItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageButton addTriviaItemButton = findViewById(R.id.addTriviaItemButton);
        addTriviaItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRandomTrivia();
            }
        });

        triviaItems = new ArrayList<>();
        triviaAdapter = new TriviaAdapter(triviaItems);
        triviaView = findViewById(R.id.triviaView);
        triviaView.setLayoutManager(new GridLayoutManager(this, LinearLayoutManager.VERTICAL));
        triviaView.setAdapter(triviaAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getRandomTrivia() {

        NumbersAPIClient service = NumbersAPIClient.retrofit.create(NumbersAPIClient.class);
        Call<TriviaItem> call = service.getRandomTrivia(new Random().nextInt(999) + 1);
        call.enqueue(new Callback<TriviaItem>() {
            @Override
            public void onResponse(@NonNull Call<TriviaItem> call, @NonNull Response<TriviaItem> response) {
                if (response.isSuccessful()) {
                    triviaItems.add(response.body());
                    triviaAdapter.notifyDataSetChanged();
                    triviaView.scrollToPosition(triviaItems.size() - 1);
                } else {
                    showToast("Failed to retrieve trivia!");
                }
            }

            @Override
            public void onFailure(@NonNull Call<TriviaItem> call, @NonNull Throwable t) {
                showToast("Couldn't connect to trivia API!");
            }
        });
    }

    private void showToast(String message) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }
}
