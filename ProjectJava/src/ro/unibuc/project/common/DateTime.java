package ro.unibuc.project.common;

public class DateTime extends Date{

    // Just be aware Java pune la dispozitie clase ptr managementul datelor
    private int hour;
    private int minutes;

    public DateTime(int year, int month, int day, int hour, int minutes) {
        super(year, month, day);
        this.hour = hour;
        this.minutes = minutes;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    @Override
    public String toString() {
        String showDateTime =  super.toString() +  " " + hour + ":";
        if (minutes < 10){
            showDateTime += "0" + minutes;
        }
        else{
            showDateTime += minutes;
        }
        return showDateTime;
    }
}
