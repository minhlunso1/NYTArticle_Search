package minhna.submission.nytarticle_minh;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import minhna.submission.nytarticle_minh.net.MyHttp;
import minhna.submission.nytarticle_minh.recyclerview.DatePickerFragment;


/**
 * Created by Administrator on 15-Feb-16.
 */
public class SettingDialogFragment extends DialogFragment {

    @Bind(R.id.btn_save)
    Button saveBtn;
    public static EditText edtDate;
    @Bind(R.id.spinner_sort)
    Spinner spnSort;
    @Bind(R.id.chk_arts)
    CheckBox chkArts;
    @Bind(R.id.chk_fashion_style)
    CheckBox chkFashionStyle;
    @Bind(R.id.chk_sports)
    CheckBox chkSports;

    public static SettingDialogFragment newInstance() {
        SettingDialogFragment frag = new SettingDialogFragment();
        Bundle args = new Bundle();
        frag.setArguments(args);

        return frag;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_setting, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        getDialog().setTitle("Setting");
        edtDate = (EditText) view.findViewById(R.id.time_picker);
    }

    @Override
    public void onStart() {
        super.onStart();
        edtDate.setText(MyHttp.begin_date);
        prepareSpinner();
        setupCheckbox();
    }

    private void setupCheckbox() {
        chkArts.setChecked(MyHttp.isArtChecked);
        chkFashionStyle.setChecked(MyHttp.isFSChecked);
        chkSports.setChecked(MyHttp.isSportChecked);
    }

    private void prepareSpinner() {
        List<String> list = new ArrayList<>();
        list.add("latest");
        list.add("oldest");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnSort.setAdapter(dataAdapter);
        spnSort.setSelected(true);
        if (MyHttp.sort_mode.equals("oldest"))
            spnSort.setSelection(1);
    }

    @OnClick({R.id.btn_save})
    public void save(){
        getDialog().dismiss();
        MyHttp.begin_date = edtDate.getText().toString();

        if (spnSort.getSelectedItemPosition()==0)
            MyHttp.sort_mode="newest";
        else
            MyHttp.sort_mode="oldest";

        MyHttp.isArtChecked= chkArts.isChecked();
        MyHttp.isFSChecked= chkFashionStyle.isChecked();
        MyHttp.isSportChecked= chkSports.isChecked();
    }
    @OnClick(R.id.time_picker)
    public void showDateDialog(){
        DatePickerFragment.newInstance().show(getActivity().getSupportFragmentManager(), "datePicker");
    }

}
