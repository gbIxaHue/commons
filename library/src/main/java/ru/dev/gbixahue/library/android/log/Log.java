package ru.dev.gbixahue.library.android.log;

/**
 * Created by Anton Zhilenkov on 06.03.2018.
 */
public class Log {

	private static Logger logger;

	public static void setLogger(Logger logger) {
		Log.logger = logger;
	}

	/************************************************************************/
	public static void d(Object from, String msg) {
		Log.d(from, msg, null);
	}

	public static void d(Object from, Object value) {
		Log.d(from, "", value);
	}

	public static void d(Object from, String msg, Object value) {
		if (logger != null) logger.d(from, msg, value);
	}

	/************************************************************************/
	public static void e(Object from, String msg) {
		Log.e(from, msg, null);
	}

	public static void e(Object from, Object value) {
		Log.e(from, "", value);
	}

	public static void e(Object from, String msg, Object value) {
		if (logger != null) logger.e(from, msg, value);
	}
}
