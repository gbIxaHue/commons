package ru.dev.gbixahue.library.android.log;

/**
 * Created by Anton Zhilenkov on 06.03.2018.
 */
public interface Logger {

	void e(Object from, String msg, Object value);
	void d(Object from, String msg, Object value);
	void setLogHandler(LogHandler logHandler);
}
