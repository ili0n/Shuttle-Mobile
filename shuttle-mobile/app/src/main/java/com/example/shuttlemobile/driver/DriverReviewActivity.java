package com.example.shuttlemobile.driver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shuttlemobile.R;
import com.example.shuttlemobile.passenger.Passenger;
import com.example.shuttlemobile.review.Review;
import com.example.shuttlemobile.ride.Ride;
import com.example.shuttlemobile.util.MockupData;

import java.util.ArrayList;
import java.util.List;

public class DriverReviewActivity extends AppCompatActivity {
    Ride ride;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_review);

        Intent in = getIntent();
        ride = (Ride)in.getSerializableExtra("ride");

        Toolbar toolbar = findViewById(R.id.toolbar_driver);
        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitle(R.string.reviews);

        initList();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initList() {
        ListView reviewList = findViewById(R.id.driver_review_list);

        List<Review> reviews = MockupData.getReviews(ride);

        reviewList.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return reviews.size();
            }

            @Override
            public Object getItem(int i) {
                return reviews.get(i);
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                View vi;
                if (view == null) {
                    vi = DriverReviewActivity.this.getLayoutInflater().inflate(R.layout.list_ride_reviews, null);
                } else {
                    vi = view;
                }

                Review obj = (Review)getItem(i);

                TextView rating = vi.findViewById(R.id.list_ride_reviews_rating);
                TextView comment = vi.findViewById(R.id.list_ride_reviews_comment);
                ImageView pfp = vi.findViewById(R.id.list_ride_reviews_pfp);
                TextView name = vi.findViewById(R.id.list_ride_reviews_name);

                obj.getPassenger();

                rating.setText(obj.getGrade() + " / 5");
                comment.setText(obj.getComment());
                // pfp.setImageResource();
                name.setText("Name Lastname");

                return vi;
            }
        });
    }
}