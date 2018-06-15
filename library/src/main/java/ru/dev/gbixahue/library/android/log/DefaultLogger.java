package ru.dev.gbixahue.library.android.log;

/**
 * Created by Anton Zhilenkov on 06.03.2018.
 */
public class DefaultLogger implements Logger {

	private StringBuilder builder;
	private String prefix;
	private LogHandler logHandler;

	public DefaultLogger(String prefix) {
		this(prefix, null);
	}

	public DefaultLogger(String prefix, LogHandler handler) {
		this.prefix = prefix;
		this.logHandler = handler;
		this.builder = new StringBuilder();
	}

	@Override
	public void e(Object from, String msg, Object value) {
		print(from, msg, value, LogType.e);
	}

	@Override
	public void d(Object from, String msg, Object value) {
		print(from, msg, value, LogType.d);
	}

	@Override
	public void setLogHandler(LogHandler logHandler) {
		this.logHandler = logHandler;
	}

	private void print(Object from, String msg, Object value, LogType type) {
		if (builder.capacity() > 0) builder.setLength(0);
		builder.append("[").append(getTag(from)).append("]: ").append(msg);
		if (value != null) {
			value = transformToString(value);
			builder.append(" {").append(value).append("}");
		}

		String logMessage = builder.toString();
		handleLog(logMessage);

		switch (type) {
			case d: {
				android.util.Log.d(prefix, logMessage);
				break;
			}
			case e: {
				android.util.Log.e(prefix, logMessage);
			}
		}
	}

	private String getTag(Object valueFrom) {
		if (valueFrom == null) return "";
		if (valueFrom instanceof CharSequence) return valueFrom.toString();
		if (!(valueFrom instanceof Class)) valueFrom = valueFrom.getClass();
		return ((Class) valueFrom).getSimpleName();
	}

	private void handleLog(String log) {
		if (logHandler != null) logHandler.handleLog(log);
	}

	private String transformToString(Object rawValue) {
		if (rawValue == null) {
			return "";
		} else if (rawValue instanceof String) {
			return (String) rawValue;
		}
		StringBuilder builder = new StringBuilder();
		if (rawValue instanceof Object[]) {
			Object[] value = (Object[]) rawValue;
			for (Object o : value) {
				builder.append(toString(o)).append(",");
			}
		} else if (rawValue instanceof Iterable) {
			for (Object o : ((Iterable) rawValue)) {
				builder.append(toString(o)).append(",");
			}
		} else {
			return toString(rawValue);
		}

		if (builder.length() > 0) builder.deleteCharAt(builder.length() - 1);
		return builder.toString();
	}

	private String toString(Object obj) {
		return obj != null ? obj.toString() : "";
	}

	enum LogType {
		d, e
	}
}
