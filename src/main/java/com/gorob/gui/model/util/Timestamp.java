package com.gorob.gui.model.util;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@XmlRootElement
public class Timestamp implements Comparable<Timestamp>, Serializable {
    private Calendar calendar;
    private String format = "dd.MM.yyyy HH:mm:ss";

    public Timestamp() {
    	initDate((Date)null);
    }
    
    public Timestamp(Date date) {
    	initDate(date);
    }
    
    public Timestamp(String dateStrg) {
    	initDate(dateStrg, this.format);
    }

    public Timestamp(int tag, int monat, int jahr, int stunden, int minuten, int sekunden) {
        this("" + tag + "." + monat + "." + jahr + " " + stunden + ":" + minuten + ":" + sekunden);
    }

	public Timestamp(String timestampStr, String format) {
		initDate(timestampStr, format);
	}

	private void initDate(String dateStrg, String format) {
		SimpleDateFormat dateFormater = new SimpleDateFormat(format);
        try {
            Date date = dateFormater.parse(dateStrg);
            initDate(date);
        } catch (ParseException e) {
            throw new RuntimeException("Date has invalid format!", e);
        }
	}

	private void initDate(Date date) {
		if (date==null) {
		  this.getCalendar();
		} else {
		  this.getCalendar().setTime(date);
		}
	}

	public static Timestamp createTimestampFromString(String timestampStr) {
		if (timestampStr==null) {
			return null;			
		}
		
		return new Timestamp(timestampStr);
	}
	
	public static Timestamp createTimestampMitFormat(String timestampStr, String format) {
		if (timestampStr==null) {
			return null;			
		}
		
		return new Timestamp(timestampStr, format);
	}
	
	public String toString(String format) {
		SimpleDateFormat dateFormater = new SimpleDateFormat(format);
		return dateFormater.format(getDate());
	}
	
	public String toStringOhneUhrzeit() {
		return toString("dd.MM.yyyy");
	}

	public String toStringMitUhrzeitOhneSekunden() {
		return toString("dd.MM.yyyy',' HH:mm 'Uhr'");
	}

	public static String createStringFromTimestamp(Timestamp timestamp, String leerWertStr) {
		if (timestamp==null) {
			return leerWertStr;
		}
		
		return timestamp.toString();
	}

	
    private Calendar getCalendar() {
        if (calendar == null) {
            calendar = Calendar.getInstance();
        }
        return calendar;
    }

    public String getFormat() {
        return format;
    }

    public Date getDate() {
        return this.getCalendar().getTime();
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

    public int getStunden() {
        return this.getCalendar().get(Calendar.HOUR_OF_DAY);
    }

    public int getMinuten() {
        return this.getCalendar().get(Calendar.MINUTE);
    }

    public int getSekunden() {
        return this.getCalendar().get(Calendar.SECOND);
    }

	public static String[] getTimestampFormate() {
		return new String[] {
			"dd.MM.yyyy HH:mm:ss",
			"dd.MM.yyyy HHmmss",
			"ddMMyyyy HH:mm:ss",
			"ddMMyyyy HHmmss",
			"dd.MM.yyyy HH:mm",
			"dd.MM.yyyy HHmm",
			"ddMMyyyy HH:mm",
			"ddMMyyyy HHmm",
			"dd.MM.yyyy",
			"ddMMyyyyHHmmss",
			"ddMMyyyyHHmm",
			"ddMMyyyyHH",
			"ddMMyyyy"
		};
	}
	
	public static Timestamp convertToTimestamp(String text) {
		text = StringUtil.isNullOrEmpty(text) ? "" : text.trim();

		for (String format : Timestamp.getTimestampFormate()) {
			Timestamp timestamp = convertToTimestamp(text, format);
			if (timestamp!=null) {
				return timestamp;
			}
		}
		
		return null;
	}

	private static Timestamp convertToTimestamp(String text, String format) {
		Timestamp timestamp; 
		try {
			timestamp = new Timestamp(text, format);
			return new Timestamp(timestamp.getDate());
		} catch (Exception e) {
			return null;
		}
	}

    
    public Timestamp addTage(int tage) {
        Calendar einCalendar = Calendar.getInstance();
        einCalendar.setTime(this.getDate());
        einCalendar.add(Calendar.DATE, tage);
        return new Timestamp(einCalendar.getTime());
    }

    public Timestamp addMonate(int monate) {
        Calendar einCalendar = Calendar.getInstance();
        einCalendar.setTime(this.getDate());
        einCalendar.add(Calendar.MONTH, monate);
        return new Timestamp(einCalendar.getTime());
    }

    public Timestamp addJahre(int jahre) {
        Calendar einCalendar = Calendar.getInstance();
        einCalendar.setTime(this.getDate());
        einCalendar.add(Calendar.YEAR, jahre);
        return new Timestamp(einCalendar.getTime());
    }

    public Timestamp addStunden(int stunden) {
        Calendar einCalendar = Calendar.getInstance();
        einCalendar.setTime(this.getDate());
        einCalendar.add(Calendar.HOUR_OF_DAY, stunden);
        return new Timestamp(einCalendar.getTime());
    }

    public Timestamp addMinuten(int minuten) {
        Calendar einCalendar = Calendar.getInstance();
        einCalendar.setTime(this.getDate());
        einCalendar.add(Calendar.MINUTE, minuten);
        return new Timestamp(einCalendar.getTime());
    }

    public Timestamp addSekunden(int sekunden) {
        Calendar einCalendar = Calendar.getInstance();
        einCalendar.setTime(this.getDate());
        einCalendar.add(Calendar.SECOND, sekunden);
        return new Timestamp(einCalendar.getTime());
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Timestamp)) {
            return false;
        }
        Timestamp andererTimestamp = (Timestamp) obj;
        return this.getTag() == andererTimestamp.getTag()
            && this.getMonat() == andererTimestamp.getMonat()
            && this.getJahr() == andererTimestamp.getJahr()
            && this.getStunden() == andererTimestamp.getStunden()
            && this.getMinuten() == andererTimestamp.getMinuten()
            && this.getSekunden() == andererTimestamp.getSekunden();
    }

    @Override
    public int hashCode() {
        return this.getDate().hashCode();
    }

    @XmlAttribute(name="value")
    public String getTimestampAsString() {
        SimpleDateFormat dateFormater = new SimpleDateFormat(this.format);
        return dateFormater.format(this.getDate());    	
    }
    
    @Override
    public String toString() {
    	return getTimestampAsString();
    }

    public int compareTo(Timestamp timestamp) {
        return this.getDate().compareTo(timestamp.getDate());
    }

    public boolean liegtVor(Timestamp timestamp) {
        return this.getDate().compareTo(timestamp.getDate()) < 0;
    }

    public boolean liegtNach(Timestamp timestamp) {
        return this.getDate().compareTo(timestamp.getDate()) > 0;
    }

	public boolean istUhrZeitMitternacht() {
		return getSekunden()==0 && getMinuten()==0 && getStunden()==0;
	}
	
	public String getTimestampStrForFilenames() {
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return dateFormater.format(this.getDate());    	
	}
}
