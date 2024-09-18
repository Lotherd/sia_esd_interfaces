package trax;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;



public abstract class TraxObject implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String id;

        
	public TraxObject() {
		id = "$" + UUID.randomUUID().toString().replace("-", "");
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	//Constants
	
	public static final String greyedOut = "rgba(0, 0, 0, 0.024)";
	
	//Helper Functions
	

	
	
	//Date Functions



	public Date now() {
		return new Date();
	}
	
	public Date Now() {
		return new Date();
	}
	
	public Date NOW() {
		return new Date();
	}
	
	public Date today() {
		return new Date();
	}
	
	public Date TODAY() {
		return today();
	}
	
	public Date date(Object date) {
		if (date != null) return (Date) date;
		return null;
	}
	
	public Date date(Date date) {
		if (date != null) return new Date(date.getTime());
		return null;
	}
	
	
	
	public Date datetime(Date date, Date time) {
		Calendar cal = Calendar.getInstance();
		if (date != null) cal.setTime(date);
		if (time != null) {
			Calendar calTime = Calendar.getInstance();
			calTime.setTime(time);
			cal.set(Calendar.HOUR_OF_DAY, calTime.get(Calendar.HOUR_OF_DAY));
			cal.set(Calendar.MINUTE, calTime.get(Calendar.MINUTE));
			cal.set(Calendar.SECOND, calTime.get(Calendar.SECOND));
		}
		return cal.getTime();
	}
	
	public Date DATETIME(Date date, Date time) {
		return datetime(date, time);
	}
	
	public Date datetime(Date date) {
		if (date != null) return new Date(date.getTime());
		return null;
	}
	

	
	public Date datetime(Date date, int hours, int minutes, int seconds) {
		Calendar cal = Calendar.getInstance();
		if (date != null) cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, hours);
		cal.set(Calendar.MINUTE, minutes);
		cal.set(Calendar.SECOND, seconds);
		return cal.getTime();
	}
	
	public Date DATE(Date date) {
		return date(date);
	}
	
	public Date date(Integer y, Integer m, Integer d) {
		Calendar c = Calendar.getInstance();
		
		if (y == null || y <= 0) y = 1900;
		if (m == null || m <= 0) m = 1;
		if (d == null || d <= 0) d = 1;
		
		c.set(y, m, d);
		
		return c.getTime();
	}
	
	public Date DATE(Integer y, Integer m, Integer d) {
		return date(y, m, d);
	}
	
	public Date time(Integer hour, Integer minutes, Integer seconds) {
	
		return null;
	}
	
	public Date time(String time) {
	
		return null;
	}
	
	public Date Time(String time) {
		return time(time);
	}
	
	public Date time(Date time) {
		if (time != null) return new Date(time.getTime());
		return null;
	}
	
	public Date Time(Date time) {
		return time(time);
	}
	
	public Date TIME(Date time) {
		return time(time);
	}
	
	public Integer hour(Date date) {
		if (date != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			return cal.get(Calendar.HOUR_OF_DAY);
		}
		return 0;
	}
	
	public Integer day(Date date) {
		if (date != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			return cal.get(Calendar.DAY_OF_MONTH);
		}
		return 0;
	}
	
	public Integer month(Date date) {
		if (date != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			return cal.get(Calendar.MONTH);
		}
		return 0;
	}
	
	public Integer year(Date date) {
		if (date != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			return cal.get(Calendar.YEAR);
		}
		return 0;
	}
	
	public String dayName(Date d) {
		if (d != null) {
			SimpleDateFormat format = new SimpleDateFormat("EEEE");
			return format.format(d);
		}
		
		return null;
	}
	
	public String dayname(Date d) {
		return dayName(d);
	}
	
	public String DayName(Date d) {
		return dayName(d);
	}
	
	public Integer dayNumber(Date d) {
		if (d != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(d);
			return cal.get(Calendar.DAY_OF_WEEK);
		}
		
		return null;
	}
	
	public Integer DayNumber(Date d) {
		return dayNumber(d);
	}
	
	public Date relativeDate(Date date, int days) {
		if (date != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.DATE, days);

			return cal.getTime();
		}
		
		return null;
	}
	
	public Date relativedate(Date date, int days) {
		return relativeDate(date, days);
	}
	
	public Date RelativeDate(Date date, int days) {
		return relativeDate(date, days);
	}
	
	public Date relativeDate(Date date, Double days) {
		if (date != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.DATE, days.intValue());
		}
		
		return null;
	}
	
	
	
	public Date relativedate(Date date, Double days) {
		return relativeDate(date, days);
	}
	
	public Date RelativeDate(Date date, Double days) {
		return relativeDate(date, days);
	}
	
	
	
	public Date relativeTime(Date date, int milliseconds) {
		return new Date(date.getTime() + milliseconds);
	}
	
	public Date relativetime(Date date, int milliseconds) {
		return relativeTime(date, milliseconds);
	}
	
	public Date RelativeTime(Date date, int milliseconds) {
		return relativeTime(date, milliseconds);
	}
	
	public Integer minute(Date date) {
		if (date != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			return cal.get(Calendar.MINUTE);
		}
		return 0;
	}
	
	
	public Integer second(Date date) {
		if (date != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			return cal.get(Calendar.SECOND);
		}
		return 0;
	}
	
	public Integer daysafter(Date d1, Date d2) {
		Integer r = 0;
		
		if (d1 != null && d2 != null)
		{
			 long diff = d2.getTime() - d1.getTime();
			 r = (int)(diff/(1000 * 60 * 60 * 24));
		};
		
		return r;
	}
	
	public Integer DaysAfter(Date d1, Date d2) {
		return daysafter(d1, d2);
	}
	

	
	public Integer secondsafter(Date d1, Date d2) {
		Integer r = 0;
		
		if (d1 != null && d2 != null) {
			return Long.valueOf((d2.getTime() - d1.getTime())/1000).intValue();
		}
		
		return r;
	}
	
	//String Functions
	
	

	
	public String bitmap(Object bMap) {
		if (bMap != null && bMap.getClass() == String.class) return (String) bMap;
		return null;
	}
	
	public String Bitmap(Object bMap) {
		return bitmap(bMap);
	}
	
	public String BITMAP(Object bMap) {
		return bitmap(bMap);
	}
	
	public String lower(String s) {
		if (s != null) s = s.toLowerCase();
		return s;
	}
	
	public String Lower(String s) {
		return lower(s);
	}
	
	public String upper(String s) {
		if (s != null) s = s.toUpperCase();
		return s;
	}
	
	public String Upper(String s) {
		return upper(s);
	}
	
	public String UPPER(String s) {
		return upper(s);
	}
	
	public Boolean match(String s1, String s2) {
		if (s1 != null && s2 != null) return s1.contains(s2);
		return false;
	}
	
	public Boolean MATCH(String s1, String s2) {
		return match(s1, s2);
	}
	
	public String space(int sCount) {
		String s = "";
		if (sCount > 0) {
			for (int i = 0; i < sCount; i++) {
				s += " ";
			}
		}
		return s;
	}
	
	public String string(int string) {
		return String.valueOf(string);
	}
	
	public String string(long string) {
		return String.valueOf(string);
	}
	
	public String string(long string, String format) {
		return String.valueOf(string);
	}
	
	public String string(double string) {
		return String.valueOf(string);
	}
	
	public String string(double string, String format) {
		return String.valueOf(string);
	}
	
	public String string(String string) {
		return string;
	}
	

	
	public String string(Date string, String format) {
		if (string == null) return "";

		DateFormat dFormat = new SimpleDateFormat(format);
		return dFormat.format(string);
	}
	
	public String string(Object string) {
		return String.valueOf(string);
	}
	
	public String String(int string) {
		return string(string);
	}
	
	public String String(long string) {
		return string(string);
	}
	
	public String String(long string, String format) {
		return string(string, format);
	}
	
	public String String(double string) {
		return string(string);
	}
	
	public String String(double string, String format) {
		return string(string, format);
	}
	
	public String String(String string) {
		return string(string);
	}
	
	public String String(Date string) {
		return string(string);
	}
	
	public String String(Date string, String format) {
		return string(string, format);
	}
	
	public String String(Object string) {
		return string(string);
	}
	
	public String STRING(int string) {
		return string(string);
	}
	
	public String STRING(long string) {
		return string(string);
	}
	
	public String STRING(long string, String format) {
		return string(string, format);
	}
	
	public String STRING(double string) {
		return string(string);
	}
	
	public String STRING(double string, String format) {
		return string(string, format);
	}
	
	public String STRING(String string) {
		return string(string);
	}
	
	public String STRING(Date string) {
		return string(string);
	}
	
	public String STRING(Date string, String format) {
		return string(string, format);
	}
	
	public String STRING(Object string) {
		return string(string);
	}
	
	public String asc(Object string) {
		return string(string);
	}
	
	public String trim(String string) {
		if (string != null) return string.trim();
		return string;
	}
	
	public String Trim(String string) {
		return trim(string);
	}
	
	public String TRIM(String string) {
		return trim(string);
	}
	
	public String left(String string, int end) {
		if (string == null) return string;
		end = (end < string.length()) ? end : string.length();
		return string.substring(0, end);
	}
	
	public String Left(String string, int end) {
		return left(string, end);
	}
	
	public String LEFT(String string, int end) {
		return left(string, end);
	}
	
	public String right(String string, int start) {
		if (string != null) {
			start = (start - 1 < string.length() - 1) ? start - 1 : string.length() - 1;
			return string.substring(start);
		}
		return string;
	}
	
	public String Right(String string, int start) {
		return right(string, start);
	}
	
	public String RIGHT(String string, int start) {
		return right(string, start);
	}
	
	public String mid(String string, int start) {
		if (string != null) {
			start = (start - 1 < string.length()) ? start - 1 : string.length() - 1;
			return string.substring(start);
		}
		return string;
	}
	
	public String mid(String string, int start, int length) {
		if (string == null) return string;
		start = (start - 1 < string.length()) ? start - 1 : string.length() - 1;
		int end = (start + length < string.length() ? start + length : string.length());
		return string.substring(start, end);
	}
	
	public String Mid(String string, int start) {
		return mid(string, start);
	}
	
	public String Mid(String string, int start, int length) {
		return mid(string, start, length);
	}
	
	public Integer len(String string) {
		if (string != null) return string.length();
		return 0;
	}
	
	public Integer Len(String string) {
		return len(string);
	}
	
	public Integer LEN(String string) {
		return len(string);
	}
	
	public Integer pos(String string, String s) {
		if (string != null) return string.indexOf(s) + 1;
		return 0;
	}
	
	public Integer pos(String string, String s, Integer start) {
		if (string != null) return string.indexOf(s, start - 1) + 1;
		return 0;
	}
	
	public Integer Pos(String string, String s) {
		return pos(string, s);
	}
	
	public Integer lastPos(String string, String s) {
		if (string != null) return string.lastIndexOf(s) + 1;
		return 0;
	}
	
	public Integer lastpos(String string, String s) {
		return lastPos(string, s);
	}
	
	public String replace( String string1, int start, int n, String string2 ) {
		if (start <= 0) start = 1;
		return string1.substring(0, start - 1) + string2 + string1.substring(start + n - 1);
	}
	
	public String replace( String string1, Object start, int n, String string2 ) {
		int start1 = (Integer) start;
		return replace(string1, start1, n, string2);
	}
	
	public String replace( String string1, int start, Object n, String string2 ) {
		int n1 = (Integer) n;
		return replace(string1, start, n1, string2);
	}
	
	public String replace( String string1, Object start, Object n, String string2 ) {
		int start1 = (Integer) start;
		int n1 = (Integer) n;
		return replace(string1, start1, n1, string2);
	}
	
	public String Replace( String string1, int start, int n, String string2 ) {
		return replace(string1, start, n, string2);
	}
	
	public String Replace( String string1, Object start, int n, String string2 ) {
		return replace(string1, start, n, string2);
	}
	
	public String Replace( String string1, int start, Object n, String string2 ) {
		return replace(string1, start, n, string2);
	}
	
	public String Replace( String string1, Object start, Object n, String string2 ) {
		return replace(string1, start, n, string2);
	}
	
	public String WordCap(String originalString) {
		String finalString = "";
		
		if (originalString != null && originalString.length() > 0) {
			String s = originalString.replace("_", " ");
			String[] sArray = s.split(" ");
			
			if (sArray != null && sArray.length > 0) {
				for (int iLbl = 0; iLbl < sArray.length; iLbl++) {
					finalString += sArray[iLbl].substring(0, 1).toUpperCase();								
					if (sArray[iLbl].length() > 1) finalString += sArray[iLbl].substring(1).toLowerCase();
					finalString += " ";
				}
			} else {
				finalString = s.substring(0, 1).toUpperCase();								
				if (s.length() > 1) finalString += s.substring(1).toLowerCase();
			}
			
			finalString = finalString.trim();
			
			if (finalString.length() <= 0) finalString = null;
		}
		
		return finalString;
	}
	
	public String wordcap(String originalString) {
		return WordCap(originalString);
	}
	
	public String wordCap(String originalString) {
		return WordCap(originalString);
	}
	
	public String Wordcap(String originalString) {
		return WordCap(originalString);
	}
	
	//Boolean Functions
	
	public Object choose(boolean condition, Object isTrue, Object isFalse) {
		if (condition)
			return isTrue;
		return isFalse;
	}
	
	public String choose(boolean condition, String isTrue, String isFalse) {
		if (condition)
			return isTrue;
		return isFalse;
	}
	
	public Integer choose(boolean condition, Integer isTrue, Integer isFalse) {
		if (condition)
			return isTrue;
		return isFalse;
	}
	
	public Double choose(boolean condition, Double isTrue, Double isFalse) {
		if (condition)
			return isTrue;
		return isFalse;
	}
	
	public Boolean like(String s1, String s2) {
		if (s1 != null && s2 != null) {
			if (s2.contains("%")) {
				if (s2.startsWith("%") && s2.endsWith("%")) {
					return s1.contains(s2.replace("%", ""));
				} else if (s2.startsWith("%")) {
					return s1.endsWith(s2.replace("%", ""));
				} else if (s2.endsWith("%")) {
					return s1.startsWith(s2.replace("%", ""));
				}
			} else {
				return s1.equals(s2);
			}
		}
		return false;
	}
	
	public Boolean in(String s1, String... sIn) {
		
		if (s1 != null && sIn != null && sIn.length > 0) {
			for (int i = 0; i < sIn.length; i++) {
				if (s1.equals(sIn[i])) return true;
			}
		}
		
		return false;
	}
	
	public boolean isNull(Object o) {
		if (o == null) return true;
		if (o.getClass() == String.class) {
			if (((String) o).length() <= 0) return true;
		}
		if (o.getClass() == Integer.class || o.getClass() == int.class) {
			if (((Integer) o) == 0) return true;
		}
		if (o.getClass() == Long.class || o.getClass() == long.class) {
			if (((Long) o) == 0) return true;
		}
		if (o.getClass() == Double.class || o.getClass() == double.class) {
			if (((Double) o) == 0) return true;
		}
		return false;
	}
	
	public boolean IsNull(Object o) {
		return isNull(o);
	}
	
	public boolean ISNUMBER(String s){
		try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    return true;
	}
	
	public boolean isnumber(String s){
		return ISNUMBER(s);
	}
	
	public boolean IsNumber(String s){
		return ISNUMBER(s);
	}	

	
	public boolean not(boolean b) {
		return !b;
	}
	
	//Numeric Functions
	
	public Integer truncate(int n, int d) {
		return n;
	}
	
	public Integer Truncate(Integer n, int d) {
		return truncate(n, d);
	}
	
	public Double truncate(Double n, int d) {
		if (n != null) {
			BigDecimal bd = new BigDecimal(n);
		    bd = bd.setScale(d, RoundingMode.HALF_UP);
		    return bd.doubleValue();
		}
		return n;
	}
	
	public Double Truncate(Double n, int d) {
		return truncate(n, d);
	}
	
	public Integer truncate(int n) {
		return n;
	}
	
	public Integer integer(String string) {
		if (string != null && string.length() > 0) return Integer.valueOf(string);
		return 0;
	}
	
	public Integer Integer(String string) {
		return integer(string);
	}
	
	public Integer Integer(int i) {
		return i;
	}
	
	public Integer Int(int i) {
		return i;
	}
	
	public Integer INT(int i) {
		return Int(i);
	}
	
	public Integer number(String string) {
		if (string != null && string.length() > 0) return Integer.valueOf(string);
		return 0;
	}
	
	public Double dec(String string) {
		if (string != null && string.length() > 0) return Double.valueOf(string);
		return 0.0;
	}
	
	public Integer mod(int n, int m) {
		return n % m;
	}
	
	public Integer Mod(int n, int m) {
		return mod(n, m);
	}
	
	public Integer MOD(int n, int m) {
		return mod(n, m);
	}
	
	public Double mod(Double n, Double m) {
		return n % m;
	}
	public Double mod(Double n, int m) {
		return n % m;
	}
	
	public Double Mod(Double n, int m) {
		return mod(n, m);
	}
	
	public Double MOD(Double n, int m) {
		return mod(n, m);
	}
	
	public Long abs(Long l) {
		if (l != null) return Math.abs(l);
		return l;
	}
	
	public Double abs(Double d) {
		if (d != null) return Math.abs(d);
		return d;
	}
	
	public Integer abs(Integer i) {
		if (i != null) return Math.abs(i);
		return i;
	}
	
	public Double abs(String s) {
		if (s != null) return Math.abs(Double.valueOf(s));
		return null;
	}
	
	public Double Abs(Double d) {
		if (d != null) return Math.abs(d);
		return d;
	}
	
	public Integer Abs(Integer i) {
		if (i != null) return Math.abs(i);
		return i;
	}
	
	public Double sqrt(Double n) {
		return Math.sqrt(n);
	}
	
	public Double sqrt(Integer n) {
		return Math.sqrt(n);
	}
	
	public Double SQRT(Double n) {
		return Math.sqrt(n);
	}
	
	public Double SQRT(Integer n) {
		return Math.sqrt(n);
	}
	
	public String round(String s, Integer i) {
		return String.valueOf(round(Double.valueOf(s), i));
	}
	
	public String Round(String s, Integer i) {
		return round(s, i);
	}
	
	public String ROUND(String s, Integer i) {
		return round(s, i);
	}
	
	public Double round(Double d, Integer i) {
		if (d == null) return d;
		if (i <= 0) return (double) Math.round(d);
		Integer x = i * 10;
		return (double) (Math.round(d * x) / x);
	}
	
	public Double Round(Double d, Integer i) {
		return round(d, i);
	}
	
	public Double ROUND(Double d, Integer i) {
		return round(d, i);
	}
	
	public Integer round(Integer n, Integer i) {
		return n;
	}
	
	public Integer Round(Integer n, Integer i) {
		return round(n, i);
	}
	
	public Integer ROUND(Integer n, Integer i) {
		return round(n, i);
	}
	
	public Integer ceiling(Integer n) {
		if (n != null) return Double.valueOf(Math.ceil(n)).intValue();
		return n;
	}
	
	//Other Functions
	
	public String rgb(int r, int g, int b) {
		return null;
	}
	
	public String RGB(int r, int g, int b) {
		return null;
	}
	
	public String Rgb(int r, int g, int b) {
		return null;
	}
        
    public boolean isValid(Object obj){
        return (obj!=null);
    }
	
    public String className(Object obj) {
    	return obj.getClass().getSimpleName().toLowerCase();
    }
    
    public String classname(Object obj) {
    	return className(obj);
    }
    
    public String ClassName(Object obj) {
    	return className(obj);
    }
    
   
    
  //String comparison
  	public boolean equal(String p1, String p2) {
  		if (p1 == null && p2 == null) return true;
  		
  		if (p1 == null || p2 == null) return false;
  		
  		return p1.equals(p2);
  	}
  	
  	public boolean notEqual(String p1, String p2) {
		return !equal(p1, p2);
	}
  	public boolean greaterThan(String p1, String p2) {
		if (notEqual(p1, p2)) {
			if (p1 == null && p2 == null) return false;
			
			if (p1 == null) return false;
			
			if (p2 == null) return true;
		} else {
			return false;
		}
		
		return p1.compareTo(p2) > 0;
	}
	
	public boolean greaterEqualThan(String p1, String p2) {
		return (equal(p1, p2) || greaterThan(p1, p2));
	}
	
	
	public boolean lessEqualThan(String p1, String p2) {
		return (equal(p1, p2) || lessThan(p1, p2));
	}
	//End String comparison
  
  //int comparison
  	public boolean equal(int p1, int p2) {
  		return p1 == p2;
  	}
  	
  	public boolean notEqual(int p1, int p2) {
  		return !equal(p1, p2);
  	}
  	
  	public boolean equal(double p1, double p2) {
		return p1 == p2;
	}
  	
  	public boolean greaterThan(int p1, int p2) {
  		return p1 > p2;
  	}
  	
  	public boolean greaterThan(double p1, double p2) {
		return p1 > p2;
	}
  	
  	public boolean greaterEqualThan(int p1, int p2) {
  		return (equal(p1, p2) || greaterThan(p1, p2));
  	}
  	
  	public boolean lessThan(int p1, int p2) {
  		return p1 < p2;
  	}
  	
  	public boolean lessThan(double p1, double p2) {
		return p1 < p2;
	}
  	
  	public boolean lessThan(String p1, String p2) {
		if (notEqual(p1, p2)) {
			if (p1 == null && p2 == null) return false;
			
			if (p1 == null) return true;
			
			if (p2 == null) return false;
		} else {
			return false;
		}
		
		return p1.compareTo(p2) < 0;
	}
  	public boolean lessEqualThan(int p1, int p2) {
  		return (equal(p1, p2) || lessThan(p1, p2));
  	}
  	
  	public boolean lessEqualThan(double p1, double p2) {
		return (equal(p1, p2) || lessThan(p1, p2));
	}
  	//End int comparison
  	
  //Date comparison
  	public boolean equal(Date p1, Date p2) {
  		if (p1 == null && p2 == null) return true;
  		
  		if (p1 == null || p2 == null) return false;
  		
  		return equal(daysafter(p1, p2), 0);
  	}
  	public boolean notEqual(Date p1, Date p2) {
		return !equal(p1, p2);
	}
	
	public boolean greaterThan(Date p1, Date p2) {
		if (notEqual(p1, p2)) {
			if (p1 == null && p2 == null) return false;
			
			if (p1 == null) return false;
			
			if (p2 == null) return true;
		} else {
			return false;
		}
		
		return greaterThan(daysafter(p2, p1), 0);
	}
	
	public boolean greaterEqualThan(Date p1, Date p2) {
		return (equal(p1, p2) || greaterThan(p1, p2));
	}
	
	public boolean lessThan(Date p1, Date p2) {
		if (notEqual(p1, p2)) {
			if (p1 == null && p2 == null) return false;
			
			if (p1 == null) return true;
			
			if (p2 == null) return false;
		} else {
			return false;
		}
		
		return lessThan(daysafter(p2, p1), 0);
	}
	
	public boolean lessEqualThan(Date p1, Date p2) {
		return (equal(p1, p2) || lessThan(p1, p2));
	}
	//End Date comparison
  	
  	public String reverse(String s) {
  		return new StringBuilder(s).reverse().toString();
  	}
  	
  	public boolean fileExists (String s){
    	File f = new File(s);
    	return (f.exists() && !f.isDirectory());
    }
  	
  	public boolean fileExists (File s){
    	return (s.exists() && !s.isDirectory());
    }
  	
  	public boolean changeDirectory (String s){
    	File f = new File(s);
    	if (f.exists() && f.isDirectory()){
    		System.setProperty("user.dir", f.getAbsolutePath());
    		return true;
    	}
    	return false;
    }
    
    public boolean changeDirectory (File f){
    	if (f.exists() && f.isDirectory()){
    		System.setProperty("user.dir", f.getAbsolutePath());
    		return true;
    	}
    	return false;
    }
    
    public boolean createDirectory (String s_path) {
    	Path path = Paths.get(s_path);
     	 
     	 if (Files.notExists(path, LinkOption.NOFOLLOW_LINKS))
			try {
				Files.createDirectory(Paths.get(s_path));
			} catch (IOException ioe) {
				return false;
			}
     	 return true;
    }
    
    public boolean fileCopy (String source, String target, Boolean replace){

    	try {
    		if (replace){
    			Files.copy(Paths.get(source), Paths.get(target), StandardCopyOption.REPLACE_EXISTING);
    		}else
    		{
    			Files.copy(Paths.get(source), Paths.get(target));
    		}
    	} catch (IOException e) {
    		return false;
    	}
    	return true;
    }
    
    public <T> T gf_nvl(T a, T b) {
		if (a == null)
			return b;
		else if (a != null && (a instanceof String) && ((String) a).trim().length() == 0)
			return b;
		else
			return a;
	}

}
