package org.rikushiru.play.rikushirutter;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.image.SmartImageView;

import java.util.List;
import java.util.Random;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;



public class MainActivity extends ListActivity {

    private TweetAdapter mAdapter;
    private Twitter mTwitter;
    private Paging paging;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        paging = new Paging();
        paging.setCount(100);

        if (!TwitterUtils.hasAccessToken(this)) {
            Intent intent = new Intent(this, TwitterOAuthActivity.class);
            startActivity(intent);
            finish();
        }else{
            mAdapter = new TweetAdapter(this);
            setListAdapter(mAdapter);

            mTwitter = TwitterUtils.getTwitterInstance(this);
            reloadTimeLine();
        }


    }

    private class TweetAdapter extends ArrayAdapter<twitter4j.Status>{

        private LayoutInflater mInflater;

        public TweetAdapter(Context context){
            super(context, android.R.layout.simple_list_item_1);
            mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public View getView(int position, View convertView , ViewGroup parent){

            if(convertView == null){
                convertView = mInflater.inflate(R.layout.list_item_tweet, null);
            }
            Status item = getItem(position);
            TextView name = (TextView) convertView.findViewById(R.id.name);
            name.setText(item.getUser().getName());
            TextView screenName = (TextView)convertView.findViewById(R.id.screen_name);
            screenName.setText("@"+item.getUser().getScreenName());
            TextView text = (TextView)convertView.findViewById(R.id.text);
            text.setText(item.getText());
            SmartImageView icon = (SmartImageView) convertView.findViewById(R.id.icon);
            icon.setImageUrl(item.getUser().getProfileImageURL());
            return convertView;
        }
    }



    private void reloadTimeLine(){
        AsyncTask<Void, Void, List<twitter4j.Status>> task = new AsyncTask<Void, Void, List<twitter4j.Status>>() {
        @Override
            protected List<twitter4j.Status> doInBackground(Void... params) {
                try{
                    return mTwitter.getHomeTimeline(paging);
                } catch (TwitterException e) {
                    e.printStackTrace();
                }
                return null;
            }

        @Override
        protected void onPostExecute(List<twitter4j.Status> result){
                if(result != null){
                    mAdapter.clear();
                    for (twitter4j.Status status : result){
                        mAdapter.add(status);
                    }
                    getListView().setSelection(0);
                } else{
                    showToast("Failed to get TimeLine. by Rikushirutter ");
                }
            }
        };
        task.execute();
    }

    private void showToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }


        switch (item.getItemId()) {
            case R.id.menu_refresh:
                reloadTimeLine();
                return true;
            case R.id.menu_tweet:
                Intent intent = new Intent(this, TweetActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_barusu:
                mTwitter = TwitterUtils.getTwitterInstance(this);
                AsyncTask<String, Void, Boolean>task = new AsyncTask<String, Void, Boolean>() {
                    @Override
                    protected Boolean doInBackground(String... params) {
                        try {
                            Random rdm = new Random();
                            int ran = rdm.nextInt(1000);
                            String kai = System.getProperty("line.separator");//改行文字をプロパティから取得
                            mTwitter.updateStatus("バルス!!" + kai + kai + ran);
                            return true;
                        } catch (TwitterException e) {
                            e.printStackTrace();
                            return false;
                        }
                    }
                    @Override
                    protected void onPostExecute(Boolean result){
                        if(result){
                            showToast("Tweet success!");
                        } else{
                            showToast("Failed...");
                        }
                    }
                };
                task.execute();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }
}
