package com.hitomi.basic.manager.update;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

class UpdateDownloader extends AsyncTask<Void, Integer, Long> {

    private static final int TIME_OUT = 30000;
    private static final int BUFFER_SIZE = 1024 * 10;

    private final static int EVENT_START = 1;
    private final static int EVENT_PROGRESS = 2;
    private final static int EVENT_COMPLETE = 3;

    private UpdateAgent mAgent;
    private String mUrl;
    private File mTemp;

    private long mBytesLoaded = 0;
    private long mBytesTotal = 0;
    private long mBytesTemp = 0;
    private long mTimeBegin = 0;
    private long mTimeLast = 0;
    private long mSpeed = 0;

    private HttpURLConnection mConnection;

    public UpdateDownloader(UpdateAgent agent, File file) {
        super();
        mAgent = agent;
        mUrl = agent.getUpdateInfo().getUrl();
        mTemp = file;
        if (mTemp.exists()) {
            mBytesTemp = mTemp.length();
        }
    }

    private long getBytesLoaded() {
        return mBytesLoaded + mBytesTemp;
    }


    @Override
    protected Long doInBackground(Void... params) {
        mTimeBegin = System.currentTimeMillis();
        try {
            long result = download();
            if (isCancelled()) {
                mAgent.setError(new UpdateError(UpdateError.DOWNLOAD_CANCELLED));
            } else if (result == -1) {
                mAgent.setError(new UpdateError(UpdateError.DOWNLOAD_UNKNOWN));
            } else if (!mAgent.verify(mTemp, mTemp.getName())) {
                mAgent.setError(new UpdateError(UpdateError.DOWNLOAD_VERIFY));
            }
        } catch (UpdateError e) {
            mAgent.setError(e);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            mAgent.setError(new UpdateError(UpdateError.DOWNLOAD_DISK_IO));
        } catch (IOException e) {
            e.printStackTrace();
            mAgent.setError(new UpdateError(UpdateError.DOWNLOAD_NETWORK_IO));
        } finally {
            if (mConnection != null) {
                mConnection.disconnect();
            }
        }
        return null;
    }


    @Override
    protected void onProgressUpdate(Integer... progress) {
        switch (progress[0]) {
            case EVENT_START:
                mAgent.downloadStart();
                break;
            case EVENT_PROGRESS:
                long now = System.currentTimeMillis();
                if (now - mTimeLast < 900) {
                    break;
                }
                mTimeLast = now;
                long mTimeUsed = now - mTimeBegin;
                mSpeed = mBytesLoaded * 1000 / mTimeUsed;
                mAgent.downloadProgress((int) (this.getBytesLoaded() * 100 / mBytesTotal));
                break;
        }
    }

    @Override
    protected void onPostExecute(Long result) {
        mAgent.downloadFinish();
    }

    private void checkNetwork() throws UpdateError {
        if (!mAgent.checkNetwork()) {
            throw new UpdateError(UpdateError.DOWNLOAD_NETWORK_BLOCKED);
        }
    }

    private void checkStatus() throws IOException, UpdateError {
        int statusCode = mConnection.getResponseCode();
        if (statusCode != 200 && statusCode != 206) {
            throw new UpdateError(UpdateError.DOWNLOAD_HTTP_STATUS, "" + statusCode);
        }
    }

    private void checkSpace(long loaded, long total) throws UpdateError {
        long storage = getAvailableStorage();
        if (total - loaded > storage) {
            throw new UpdateError(UpdateError.DOWNLOAD_DISK_NO_SPACE);
        }
    }

    private HttpURLConnection create(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Accept", "application/*");
        connection.setConnectTimeout(10000);
        return connection;
    }

    private long download() throws IOException, UpdateError {
        checkNetwork();

        mConnection = create(new URL(mUrl));
        mConnection.connect();

        checkStatus();

        mBytesTotal = mConnection.getContentLength();

        checkSpace(mBytesTemp, mBytesTotal);


        if (mBytesTemp == mBytesTotal) {
            publishProgress(EVENT_START);
            return 0;
        }
        if (mBytesTemp > 0) {

            mConnection.disconnect();
            mConnection = create(mConnection.getURL());
            mConnection.addRequestProperty("Range", "bytes=" + mBytesTemp + "-");
            mConnection.connect();

            checkStatus();
        }

        publishProgress(EVENT_START);

        int bytesCopied = copy(mConnection.getInputStream(), new LoadingRandomAccessFile(mTemp));

        if ((mBytesTemp + bytesCopied) != mBytesTotal && mBytesTotal != -1) {
            throw new UpdateError(UpdateError.DOWNLOAD_INCOMPLETE);
        }

        return bytesCopied;

    }

    private int copy(InputStream in, RandomAccessFile out) throws IOException, UpdateError {

        byte[] buffer = new byte[BUFFER_SIZE];
        BufferedInputStream bis = new BufferedInputStream(in, BUFFER_SIZE);
        try {

            out.seek(out.length());

            int bytes = 0;
            long previousBlockTime = -1;

            while (!isCancelled()) {
                int n = bis.read(buffer, 0, BUFFER_SIZE);
                if (n == -1) {
                    break;
                }
                out.write(buffer, 0, n);
                bytes += n;

                checkNetwork();

                if (mSpeed != 0) {
                    previousBlockTime = -1;
                } else if (previousBlockTime == -1) {
                    previousBlockTime = System.currentTimeMillis();
                } else if ((System.currentTimeMillis() - previousBlockTime) > TIME_OUT) {
                    throw new UpdateError(UpdateError.DOWNLOAD_NETWORK_TIMEOUT);
                }
            }
            return bytes;
        } finally {
            out.close();
            bis.close();
            in.close();
        }
    }

    private long getAvailableStorage() {
        try {
            StatFs stat = new StatFs(Environment.getExternalStorageDirectory().toString());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                return stat.getAvailableBlocksLong() * stat.getBlockSizeLong();
            } else {
                return (long) stat.getAvailableBlocks() * (long) stat.getBlockSize();
            }
        } catch (RuntimeException ex) {
            return 0;
        }
    }

    private final class LoadingRandomAccessFile extends RandomAccessFile {

        public LoadingRandomAccessFile(File file) throws FileNotFoundException {
            super(file, "rw");
        }

        @Override
        public void write(byte[] buffer, int offset, int count) throws IOException {

            super.write(buffer, offset, count);
            mBytesLoaded += count;
            publishProgress(EVENT_PROGRESS);
        }
    }
}