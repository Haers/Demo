package com.acticitytest.demo.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import com.acticitytest.demo.R;
import com.acticitytest.demo.adapter.ListViewAdapter;
import com.acticitytest.demo.entity.Message;
import com.acticitytest.demo.http.HttpMethods;
import com.acticitytest.demo.http.ProgressSubscriber;
import com.acticitytest.demo.http.SubscriberOnNextListener;
import com.acticitytest.demo.http.presenter.MessagePresenter;
import com.melnykov.fab.FloatingActionButton;
import com.melnykov.fab.ScrollDirectionListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ListViewFragment extends Fragment {

    Unbinder unbinder;
    @BindView(android.R.id.list)
    ListView list;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    private SubscriberOnNextListener getMessageOnNext;

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_listview,
                container, false);
        unbinder = ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onResume(){
        super.onResume();
        final ArrayList<String> msg = new ArrayList<>();
        getMessageOnNext = new SubscriberOnNextListener<List<Message>>(){
            @Override
            public void onNext(final List<Message> messages){
                for(int i = 0; i < messages.size(); i++)
                    msg.add(messages.get(i).getMsg());
                //final String countries[]=getResources().getStringArray(R.array.countries);
                final String[] m = msg.toArray(new String[msg.size()]);
                //ListViewAdapter listAdapter = new ListViewAdapter(getActivity(), countries);
                ListViewAdapter listAdapter = new ListViewAdapter(getActivity(), m);
                list.setAdapter(listAdapter);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        /*String value[]=countries[position].split(",");
                        int picId=getActivity().getResources().getIdentifier(value[1],
                        "drawable",getActivity().getPackageName());
                        ;*/
                        Bundle bundle = new Bundle();
                        bundle.putString("senderId", messages.get(position).getSenderId());
                        bundle.putString("sendDate", messages.get(position).getSendDate());
                        bundle.putString("sendTime", messages.get(position).getSendTime());
                        bundle.putString("msg", msg.get(position));
                        bundle.putString("fetchLocation", messages.get(position).getFetchLocation());
                        bundle.putString("sendLocation", messages.get(position).getSendLocation());
                        Intent intent=new Intent("android.intent.action.NEW_VIEW");
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });

                fab.attachToListView(list, new ScrollDirectionListener() {
                    @Override
                    public void onScrollDown() {
                        Log.d("ListViewFragment",
                                "onScrollDown()");
                    }

                    @Override
                    public void onScrollUp() {
                        Log.d("ListViewFragment",
                                "onScrollUp()");
                    }
                }, new AbsListView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(AbsListView view, int scrollState) {
                        Log.d("ListViewFragment", "onScrollStateChanged()");
                    }

                    @Override
                    public void onScroll(AbsListView view, int firstVisibleItem,
                                         int visibleItemCount, int totalItemCount) {
                        Log.d("ListViewFragment", "onScroll()");
                    }
                });
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("android.intent.action.EDIT_MESSAGE");
                        startActivity(intent);
                    }
                });
            }
        };
        getMessage();
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        unbinder.unbind();
    }

    private void getMessage(){
        HttpMethods.getInstance();
        MessagePresenter.showMessage(new
                ProgressSubscriber<List<Message>>(getMessageOnNext, getContext()));
    }
}
