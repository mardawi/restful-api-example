package com.example.ammar.restfulexample;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class ArticlesFragment extends Fragment {

    private static final String EXTRA_PK = "pk";
    private ArrayAdapter<Article> mAdapter;
    final private List<Article> mArticles = new ArrayList<>();
    private int mPk;

    public ArticlesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (savedInstanceState != null && savedInstanceState.getInt(EXTRA_PK, -1) != -1)
            mPk = savedInstanceState.getInt(EXTRA_PK, -1);


        final View root = inflater.inflate(R.layout.fragment_main, container, false);
        if(mPk == -1){
            getActivity().finish();
            return root;
        }

        final ListView lv = (ListView) root.findViewById(R.id.listview);
        mAdapter = new ArrayAdapter<Article>(getActivity(), R.layout.article_row, R.id.listview_row_textview, mArticles){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                final Article article = mArticles.get(position);
                final View view = super.getView(position, convertView, parent);
                final ImageView image = (ImageView) view.findViewById(R.id.imageView);
                Picasso.with(getActivity()).load(article.imageUrl).fit().into(image);
                return view;
            }
        };
        lv.setAdapter(mAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Article article = mArticles.get(position);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(article.articleUrl));
                startActivity(i);
            }
        });
        getArticles();
        return root;
    }

    private void getArticles() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                List<Article> result = ServerManager.getInstance().fetchArticles(mPk);
                if (result != null) {
                    mArticles.clear();
                    mArticles.addAll(result);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                mAdapter.notifyDataSetChanged();
            }
        }.execute();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(EXTRA_PK, mPk);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public void setPk(int pk) {
        mPk = pk;
    }
}
