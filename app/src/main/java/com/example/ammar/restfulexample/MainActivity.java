package com.example.ammar.restfulexample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_TYPE = "type";
    public static final String EXTRA_PK = "pk";
    public static final String EXTRA_TYPE_CATEGORY = "category";
    public static final String EXTRA_TYPE_ARTICLE = "article";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment fragment;

        String type = getIntent().getStringExtra(EXTRA_TYPE);
        if(type == null)
            type = EXTRA_TYPE_CATEGORY;

        switch (type) {
            case EXTRA_TYPE_CATEGORY:
            default:
                fragment = new CategoriesFragment();
                break;
            case EXTRA_TYPE_ARTICLE:
                fragment = new ArticlesFragment();
                final int pk = getIntent().getIntExtra(EXTRA_PK, -1);
                ((ArticlesFragment)fragment).setPk(pk);
                break;

        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, fragment).commitAllowingStateLoss();
    }

}
