package minhna.submission.nytarticle_minh.recyclerview;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import minhna.submission.nytarticle_minh.SettingDialogFragment;

public class DatePickerFragment extends DialogFragment
                            implements DatePickerDialog.OnDateSetListener {
    private int day;
    private int month;
    private int year;

    public static DatePickerFragment newInstance(){
        DatePickerFragment fragment = new DatePickerFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        this.day = day;
        this.month = month + 1;
        this.year = year;
        SimpleDateFormat format = new SimpleDateFormat("yyyy MM dd");
        Calendar cal = Calendar.getInstance();
        cal.set(year,month,day);
        String beginDate = format.format(cal.getTime());
        beginDate = beginDate.replaceAll("\\s+","");
        SettingDialogFragment.edtDate.setText(beginDate);
    }

}