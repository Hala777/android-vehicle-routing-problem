package org.tomasdavid.vehicleroutingproblem.tasks;

import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ProgressBar;

import org.tomasdavid.vehicleroutingproblem.R;
import org.tomasdavid.vehicleroutingproblem.fragments.VrpFragment;

/**
 * Progress bar task for change time progress of progress bar when solver is solving.
 *
 * @author Tomas David
 */
/*
 * Copyright 2015 Tomas David
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class ProgressBarTask extends AsyncTask<Integer, Void, Void> {

    /**
     * Class tag.
     */
    private static final String TAG = "ProgressBarTask";

    /**
     * One second = 1000 milliseconds
     */
    private static final int SECOND = 1000;

    /**
     * Actual progress of progress bar.
     */
    private int progress;

    /**
     * Vrp fragment where progress bar is placed.
     */
    private VrpFragment fragment;

    /**
     * Constructor of progress bar task.
     * @param fragment Vrp fragment where progress bar is placed.
     */
    public ProgressBarTask(VrpFragment fragment) {
        this.fragment = fragment;
        this.progress = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Void doInBackground(Integer... params) {

        // initialize progress bar
        progress = 0;
        ProgressBar progressBar = (ProgressBar) fragment.getActivity().findViewById(R.id.progress_bar);
        progressBar.setProgress(progress);

        // while solver task is running change progress every 1 second
        while (fragment.getVrpSolverTask().isRunning() && progress < progressBar.getMax()) {
            try {
                Thread.sleep(SECOND);
            } catch (InterruptedException e) {
                Log.e(TAG, "Thread sleep error.", e);
            }

            if (fragment != null) {
                FragmentActivity activity =  fragment.getActivity();
                if (activity != null) {
                    progressBar = (ProgressBar) activity.findViewById(R.id.progress_bar);
                    if (progressBar != null) {
                        progressBar.incrementProgressBy(1);
                    }
                }
            }
            progress++;
        }

        return null;
    }
}
