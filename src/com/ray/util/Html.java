package com.ray.util;

public class Html {

    private static final String H1 = "<h1>";
    private static final String H2 = "<h2>";
    private static final String H3 = "<h3>";
    private static final String H4 = "<h4>";
    private static final String H5 = "<h5>";

    private static final String P = "<p>";
    
    private static final String STRONG = "<strong>";
    /* --------------------- H -------------------- */

    public static String h1(String mensagem) {
	return H1 + mensagem + H1.substring(0, 1).concat("/").concat(H1.substring(1));
    }

    public static String h2(String mensagem) {
	return H2 + mensagem + H2.substring(0, 1).concat("/").concat(H2.substring(1));
    }

    public static String h3(String mensagem) {
	return H3 + mensagem + H3.substring(0, 1).concat("/").concat(H3.substring(1));
    }

    public static String h4(String mensagem) {
	return H4 + mensagem + H4.substring(0, 1).concat("/").concat(H4.substring(1));
    }

    public static String h5(String mensagem) {
	return H5 + mensagem + H5.substring(0, 1).concat("/").concat(H5.substring(1));
    }

    /* --------------------- P -------------------- */

    public static String p(String mensagem) {
	return P + mensagem + P.substring(0, 1).concat("/").concat(P.substring(1));
    }
    
    /* --------------------- A -------------------- */
    public static String a(String mensagem, String href) {
	return "<a href=\"" + href + "\">" + mensagem + "</a>";
    }
    
    /* --------------------- STRONG -------------------- */
    public static String strong(String mensagem) {
	return STRONG + mensagem + STRONG.substring(0, 1).concat("/").concat(STRONG.substring(1));
    }
    
    

}
