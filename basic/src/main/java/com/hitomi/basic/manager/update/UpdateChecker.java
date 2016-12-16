package com.hitomi.basic.manager.update;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

class UpdateChecker extends AsyncTask<Void, Integer, Void> {

    final UpdateAgent mAgent;

    public UpdateChecker(UpdateAgent agent) {
        mAgent = agent;
    }

    @Override
    protected Void doInBackground(Void... params) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(mAgent.getUrl()).openConnection();
            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                mAgent.parse(connection.getInputStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
            mAgent.setError(new UpdateError(UpdateError.CHECK_NETWORK_IO));
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        mAgent.checkFinish();
    }
}