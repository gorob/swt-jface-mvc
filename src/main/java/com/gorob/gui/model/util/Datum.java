package com.gorob.gui.model.util;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@SuppressWarnings({ "serial", "rawtypes" })
public class Datum implements Comparable, Serializable {

    public static final Datum MAX = new Datum(31, 12, 2099);
    public static final Datum MIN = new Datum(1, 1, 1900);
    public static final Datum ANFANG_DATUM = new Datum(1, 1, 1);
    public static final Datum HEUTE = new Datum();

    private Calendar calendar;
    private String format = "dd.MM.yyyy";

    public Datum() {
        this.getCalendar();
        this.resetZeitVonDatum();
    }

    public Datum(int tag, int monat, int jahr) {
        this.getCalendar().set(jahr, monat - 1, tag);
        this.resetZeitVonDatum();
    }

    public Datum(Date date) {
        this.getCalendar().setTime(date);
        this.resetZeitVonDatum();
    }

    public Datum(String dateStrg, String format) {
        SimpleDateFormat dateFormater = new SimpleDateFormat(format);
        try {
            Date date = dateFormater.parse(dateStrg);
            this.getCalendar().setTime(date);
            this.resetZeitVonDatum();
        } catch (ParseException e) {
            throw new RuntimeException("Date has invalid format!", e);
        }
    }

    public Datum(String dateStrg) {
        SimpleDateFormat dateFormater = new SimpleDateFormat(format);
        try {
            Date date = dateFormater.parse(dateStrg);
            this.getCalendar().setTime(date);
            this.resetZeitVonDatum();
        } catch (ParseException e) {
            throw new RuntimeException("Date has invalid format!", e);
        }
    }

    public int getTag() {
        return this.getCalendar().get(Calendar.DAY_OF_MONTH);
    }

    public int getMonat() {
        return 1 + this.getCalendar().get(Calendar.MONTH);
    }

    public int getJahr() {
        return this.getCalendar().get(Calendar.YEAR);
    }

    private Calendar getCalendar() {
        if (calendar == null) {
            calendar = Calendar.getInstance();
        }
        return calendar;
    }

    public Date getDate() {
        return this.getCalendar().getTime();
    }

    public void setDate(Date date) {
        this.getCalendar().setTime(date);
        this.resetZeitVonDatum();
    }

    public String toString() {
        if (this.equals(ANFANG_DATUM)) {
            return "";
        }
        SimpleDateFormat dateFormater = new SimpleDateFormat(format);
        return dateFormater.format(this.getDate());

    }

    public String getDatumStrImFormatJJJJTTMM() {
    	return "" + this.getJahr() + StringUtil.padl(getMonat(), 2, '0') + StringUtil.padl(getTag(), 2, '0');
    }
    
    public static Datum createDatumMitFormat(String datum, String format) {
    	if (datum==null) {
    		return null;
    	}
    	return new Datum(datum, format);
    }
    
	public String toString(String format) {
		SimpleDateFormat dateFormater = new SimpleDateFormat(format);
		return dateFormater.format(getDate());
	}

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Datum createClone() {
        return this.addTage(0);
    }

    public Datum addTage(int tage) {
        Calendar einCalendar = Calendar.getInstance();
        einCalendar.setTime(this.getDate());
        einCalendar.add(Calendar.DATE, tage);
        return new Datum(einCalendar.getTime());
    }

    public Datum addMonate(int monate) {
        Calendar einCalendar = Calendar.getInstance();
        einCalendar.setTime(this.getDate());
        einCalendar.add(Calendar.MONTH, monate);
        return new Datum(einCalendar.getTime());
    }

    public Datum addJahre(int jahre) {
        Calendar einCalendar = Calendar.getInstance();
        einCalendar.setTime(this.getDate());
        einCalendar.add(Calendar.YEAR, jahre);
        return new Datum(einCalendar.getTime());
    }

    public boolean istImSelbenMonat(Datum datum) {
        return (this.getJahr() == datum.getJahr())
                && (this.getMonat() == datum.getMonat());
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Datum)) {
            return false;
        }
        Datum einDatum = (Datum) obj;
        return this.getTag() == einDatum.getTag()
                && this.getMonat() == einDatum.getMonat()
                && this.getJahr() == einDatum.getJahr();
    }

    public int hashCode() {
        return this.getDate().hashCode();
    }

    public int getAnzahlTageImMonat() {
        return this.getCalendar().getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public int getTagDesJahres() {
        return this.getCalendar().get(Calendar.DAY_OF_YEAR);
    }

    public int compareTo(Datum einDatum) {
        return this.getDate().compareTo(einDatum.getDate());
    }

    public int compareTo(Object obj) {
        return this.compareTo((Datum) obj);
    }

    public boolean istMonatsultimo() {
        return this.getAnzahlTageImMonat() == this.getTag();
    }

    public Datum getMonatsultimo() {
        int dd = this.getAnzahlTageImMonat();
        return new Datum(dd, this.getMonat(), this.getJahr());
    }

    public Datum getMonatsultimoFolgemonat() {
        Datum folgeMonat = this.addMonate(1);
        return folgeMonat.getMonatsultimo();
    }

    private void resetZeitVonDatum() {
        int day = this.getCalendar().get(Calendar.DAY_OF_YEAR);
        int year = this.getCalendar().get(Calendar.YEAR);
        this.getCalendar().setTimeInMillis(-3600000);
        this.getCalendar().set(Calendar.DAY_OF_YEAR, day);
        this.getCalendar().set(Calendar.YEAR, year);
    }

    public int getRestMonateZuJahresEnde() {
        return 12 - this.getMonat();
    }

    public boolean istJahresAnfang() {
        return this.getTag() == 1 && this.getMonat() == 1;
    }

    public static Datum nullDatum() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(0);
        return new Datum(calendar.getTime());
    }

    public boolean liegtVor(Datum anotherDatum) {
        return this.getDate().compareTo(anotherDatum.getDate()) < 0;
    }

    public boolean liegtNach(Datum anotherDatum) {
        return this.getDate().compareTo(anotherDatum.getDate()) > 0;
    }

    public static Datum max(Datum datum1, Datum datum2) {
        if (datum1.equals(datum2)) {
            return datum1;
        }
        if (datum1.liegtNach(datum2)) {
            return datum1;
        }
        return datum2;
    }

    public static Datum min(Datum datum1, Datum datum2) {
        if (datum1.equals(datum2)) {
            return datum1;
        }
        if (datum1.liegtVor(datum2)) {
            return datum1;
        }
        return datum2;
    }

    @SuppressWarnings("unchecked")
	public static Datum max(List<Datum> datumList) {
        if (datumList.isEmpty()) {
            return Datum.MAX;
        }
        return Collections.max(datumList);
    }

    @SuppressWarnings("unchecked")
	public static Datum min(List<Datum> datumList) {
        if (datumList.isEmpty()) {
            return Datum.MIN;
        }
        return Collections.min(datumList);
    }
    
    public static String[] getDatumFormate() {
		return new String[] {
			"dd.MM.yyyy",
			"ddMMyyyy"
		};
	}

	public static Datum convertToDatum(String text) {
		text = StringUtil.isNullOrEmpty(text) ? "" : text.trim();

		for (String format : Datum.getDatumFormate()) {
			Datum datum = convertToDatum(text, format);
			if (datum!=null) {
				return datum;
			}
		}
		
		return null;
	}

	private static Datum convertToDatum(String text, String format) {
		Datum datum; 
		try {
			datum = new Datum(text, format);
			return new Datum(datum.getDate());
		} catch (Exception e) {
			return null;
		}
	}

}
