package model;

/**
 * 文字列を消毒するクラス。
 *
 *
 * @author miyajima
 */
public class Sanitize {

	public String execute(String str) {
		if(str == null) {
	        return null;
	    } else {
		    str = str.replaceAll("&" , "&amp;" );
		    str = str.replaceAll("<" , "&lt;"  );
		    str = str.replaceAll(">" , "&gt;"  );
		    str = str.replaceAll("\"", "&quot;");
		    str = str.replaceAll("\'" , "&＃39;" );
	    }

		return str;
	}

}
