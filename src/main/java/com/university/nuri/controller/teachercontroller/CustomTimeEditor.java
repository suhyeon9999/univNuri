package com.university.nuri.controller.teachercontroller;

import java.beans.PropertyEditorSupport;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CustomTimeEditor extends PropertyEditorSupport {
    private final SimpleDateFormat dateFormat;

    public CustomTimeEditor(String pattern) {
        this.dateFormat = new SimpleDateFormat(pattern);
        this.dateFormat.setLenient(false);
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        try {
            // "HH:mm" 형식으로 시간을 파싱하여 Time 객체로 변환
            Time time = new Time(dateFormat.parse(text).getTime());
            setValue(time);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid time format. Expected format: HH:mm");
        }
    }
}