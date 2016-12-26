package by.yawningturtles.watchit.fragments;

import android.app.AlarmManager;
import android.app.Notification;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Build;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import java.util.List;

import by.yawningturtles.watchit.R;
import by.yawningturtles.watchit.dal.Film;
import by.yawningturtles.watchit.dal.FilmLoader;
import by.yawningturtles.watchit.services.NotificationService;

public class SettingsFragment extends Fragment {

    CheckBox cbEveryDay;
    CheckBox cbPlanned;
    EditText etEveryDayHours;
    EditText etEveryDayMinutes;
    EditText etPlannedHours;
    EditText etPlannedMinutes;
    Button btSave;

    static final String prefsName = "by.yawningturtles.watchit.preferences";
    static final String cbEverydayState = "by.yawningturtles.watchit.cbEverydayState";
    static final String cbPlannedState = "by.yawningturtles.watchit.cbPlannedState";
    static final String etEveryDayHoursStae = "by.yawningturtles.watchit.etEveryDayHours";
    static final String etEveryDayMinutesState = "by.yawningturtles.watchit.etEveryDayMinutes";
    static final String etPlannedHoursState = "by.yawningturtles.watchit.etPlannedHours";
    static final String etPlannedMinutesState = "by.yawningturtles.watchit.etPlannedMinutes";

    public SettingsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cbEveryDay = (CheckBox) view.findViewById(R.id.everyDayCheckBox);
        cbPlanned = (CheckBox) view.findViewById(R.id.plannedCheckBox);
        etPlannedHours = (EditText) view.findViewById(R.id.hPlannedEditText);
        etPlannedMinutes = (EditText) view.findViewById(R.id.minPlannedEditText);
        etEveryDayHours = (EditText) view.findViewById(R.id.hEditText);
        etEveryDayMinutes = (EditText) view.findViewById(R.id.minEditText);
        btSave = (Button) view.findViewById(R.id.saveButton);

        btSave.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if(checkInput()) {
                    if(cbEveryDay.isChecked()) {
                        notifyEveryDay(getNotification("It's movie time!"), Integer.parseInt(etEveryDayHours.getText().toString()), Integer.parseInt(etEveryDayMinutes.getText().toString()));
                    }
                    if(cbPlanned.isChecked()) {
                        FilmLoader filmLoader = new FilmLoader(getContext());
                        List<Film> plannedFilms = filmLoader.getPlannedFilms();
                        for(Film film : plannedFilms) {
                            long plannedDate = film.getPlanningDate().getTimeInMillis();
                            scheduleNotification(getNotification("You've scheduled a movie! " + film.getTitle()), plannedDate, Integer.parseInt(etPlannedHours.getText().toString()), Integer.parseInt(etPlannedMinutes.getText().toString()));
                        }
                        SharedPreferences mSettings = PreferenceManager.getDefaultSharedPreferences(getContext());
                        SharedPreferences.Editor editor = mSettings.edit();
                        editor.putInt(etEveryDayMinutesState, Integer.parseInt(etEveryDayMinutes.getText().toString()));
                        editor.putInt(etEveryDayHoursStae, Integer.parseInt(etEveryDayHours.getText().toString()));
                        editor.putInt(etPlannedMinutesState, Integer.parseInt(etPlannedMinutes.getText().toString()));
                        editor.putInt(etPlannedHoursState, Integer.parseInt(etPlannedHours.getText().toString()));
                        editor.putBoolean(cbPlannedState, cbPlanned.isChecked());
                        editor.putBoolean(cbEverydayState, cbEveryDay.isChecked());
                        editor.apply();
                    }
                } else {
                    Toast.makeText(getContext(), "Wrong input!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        SharedPreferences mSettings = PreferenceManager.getDefaultSharedPreferences(getContext());
        cbPlanned.setChecked(mSettings.getBoolean(cbPlannedState, false));
        cbEveryDay.setChecked(mSettings.getBoolean(cbEverydayState, false));
        etEveryDayHours.setText(String.valueOf(mSettings.getInt(etEveryDayHoursStae, 0)));
        etEveryDayMinutes.setText(String.valueOf(mSettings.getInt(etEveryDayMinutesState, 0)));
        etPlannedHours.setText(String.valueOf(mSettings.getInt(etPlannedHoursState, 0)));
        etPlannedMinutes.setText(String.valueOf(mSettings.getInt(etPlannedMinutesState, 0)));
    }

    private boolean checkInput() {
        try {
            if ((Integer.parseInt(etEveryDayHours.getText().toString()) <= 23)
                    && (Integer.parseInt(etEveryDayMinutes.getText().toString()) <= 59)
                    && (Integer.parseInt(etPlannedHours.getText().toString()) <= 23)
                    && (Integer.parseInt(etPlannedMinutes.getText().toString()) <= 59)) {
                return true;
            }
        } catch (Exception ex){
            Log.d("etEveryDayHours", etEveryDayHours.getText().toString());
            Log.d("checkInput", " exception");
            return false;
        }
        return false;
    }

    private void notifyEveryDay(Notification notification, int hours, int minutes) {
        Intent notificationIntent = new Intent(getContext(), NotificationService.class);
        notificationIntent.putExtra(NotificationService.NOTIFICATION_ID, 11);
        notificationIntent.putExtra(NotificationService.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, hours);
            calendar.set(Calendar.MINUTE, minutes);
            calendar.set(Calendar.SECOND, 0);
            AlarmManager alarmManager = (AlarmManager)getContext().getSystemService(Context.ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

    private void scheduleNotification(Notification notification, long plannedTime, int hours, int minutes) {
        Intent notificationIntent = new Intent(getContext(), NotificationService.class);
        notificationIntent.putExtra(NotificationService.NOTIFICATION_ID, 21);
        notificationIntent.putExtra(NotificationService.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            calendar = Calendar.getInstance();
            calendar.setTimeInMillis(plannedTime);
            calendar.set(Calendar.HOUR_OF_DAY, hours);
            calendar.set(Calendar.MINUTE, minutes);
            calendar.set(Calendar.SECOND, 0);
            AlarmManager alarmManager = (AlarmManager)getContext().getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
    }

    private Notification getNotification(String content) {
        Notification.Builder builder = new Notification.Builder(getContext());
        builder.setContentTitle("WatchIt!");
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.notification);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            return builder.build();
        }
        return null;
    }
}
