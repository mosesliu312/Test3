package com.timealbum.moses.test3;


import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

public class activity_job extends JobService {
    private static final String TAG = "activity_job";
    private boolean jobCanceled = false;

    @Override
    public boolean onStartJob(JobParameters params) {
        // 返回true，表示该工作耗时，同时工作处理完成后需要调用onStopJob销毁（jobFinished）
        // 返回false，任务运行不需要很长时间，到return时已完成任务处理
        Log.d(TAG, "onStartJob");
        doBackgroundWork(params);

        return false;
    }

    private void doBackgroundWork(final JobParameters params) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(jobCanceled){
                    return;
                }
                for(int i =0;i<10;i++){
                    Log.d(TAG, "run: "+i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Log.d(TAG, "run: job finished");
                jobFinished(params,false);
            }
        }).start();
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "onStopJob: job cancelled before completion");
        /*
        当收到取消请求时，onStopJob(JobParameters params)是系统用来取消挂起的任务的。
        重要的是要注意到，如果onStartJob(JobParameters params)返回 false，当取消请求被接收时，该系统假定没有目前运行的工作。换句话说，它根本就不调用onStopJob(JobParameters params)。那此时就需要我们手动调用jobFinished (JobParameters params, boolean needsReschedule)方法了。
        */

        // 有且仅有onStartJob返回值为true时，才会调用onStopJob来销毁job
        // 返回false来销毁这个工作
        jobCanceled = true;
        return true;
    }
}
