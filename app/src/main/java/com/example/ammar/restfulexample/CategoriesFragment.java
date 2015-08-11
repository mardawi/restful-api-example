package com.example.ammar.restfulexample;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class CategoriesFragment extends Fragment {

    private ArrayAdapter<Category> mAdapter;
    final private List<Category> mCategories = new ArrayList<>();

    public CategoriesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_main, container, false);

        final ListView lv = (ListView) root.findViewById(R.id.listview);

        mAdapter = new ArrayAdapter<>(getActivity(), R.layout.category_row, R.id.listview_row_textview, mCategories);
        lv.setAdapter(mAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), MainActivity.class);
                i.putExtra(MainActivity.EXTRA_TYPE, MainActivity.EXTRA_TYPE_ARTICLE);
                i.putExtra(MainActivity.EXTRA_PK, mCategories.get(position).pk);
                getActivity().startActivity(i);
            }
        });
        getCategories();
        return root;
    }

    private void getCategories() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                List<Category> result = ServerManager.getInstance().fetchCategories();
                if (result != null) {
                    mCategories.clear();
                    mCategories.addAll(result);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {

                if (isAdded())
                    mAdapter.notifyDataSetChanged();
            }
        }.execute();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
}
}
