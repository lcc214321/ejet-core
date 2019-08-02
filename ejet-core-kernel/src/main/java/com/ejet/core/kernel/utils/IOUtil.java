package com.ejet.core.kernel.utils;



/**
 * IOUtil
 *
 */
public class IOUtil {
	/**
	 * 自动关闭
	 *
	 * @param stm
	 */
	public static void closeQuietly(AutoCloseable stm) {
		try {
			if (stm != null) {
				stm.close();
				stm = null;
			}
		} catch (Exception ioe) {
			// ignore
		}
	}
	/**
	 * 自动关闭
	 */
	public static void closeQuietly(Closeable closeable) {
		try {
			if (closeable != null) {
				closeable.close();
				closeable = null;
			}
		} catch (IOException ioe) {
			// ignore
		}
	}
	/**
	 * error message
	 *
	 * @param ex
	 * @return
	 */
	public static String getError(Throwable ex) {
		StringWriter sw = new StringWriter();
		try {
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			Throwable cause = ex.getCause();
			while (null != cause) {
				cause.printStackTrace(pw);
				cause = cause.getCause();
			}
			pw.close();
			sw.append(pw.toString()).append("\r\n");
		} catch (Exception e) {
			// Log.e("", "", e);
			// ignore
		}
		return sw.toString();
	}
//	/**
//	 * InputStream to String utf-8
//	 *
//	 * @param input the <code>InputStream</code> to read from
//	 * @return the requested String
//	 */
//	public static String toString(InputStream input) {
//		return toString(input, Charsets.UTF_8);
//	}

//	/**
//	 * InputStream to String
//	 *
//	 * @param input   the <code>InputStream</code> to read from
//	 * @param charset the <code>Charsets</code>
//	 * @return the requested String
//	 */
//	public static String toString( InputStream input, java.nio.charset.Charset charset) {
//		try {
//			return IOUtil.copyToString(input, charset);
//		} catch (IOException e) {
//			throw Exceptions.unchecked(e);
//		} finally {
//			IOUtil.closeQuietly(input);
//		}
//	}
//
//	public static byte[] toByteArray( InputStream input) {
//		try {
//			return IOUtil.copyToByteArray(input);
//		} catch (IOException e) {
//			throw Exceptions.unchecked(e);
//		} finally {
//			IOUtil.closeQuietly(input);
//		}
//	}

	/**
	 * Writes chars from a <code>String</code> to bytes on an
	 * <code>OutputStream</code> using the specified character encoding.
	 * <p>
	 * This method uses {@link String#getBytes(String)}.
	 *
	 * @param data     the <code>String</code> to write, null ignored
	 * @param output   the <code>OutputStream</code> to write to
	 * @param encoding the encoding to use, null means platform default
	 * @throws IOException if an I/O error occurs
	 */
	public static void write(final String data, final OutputStream output, final java.nio.charset.Charset encoding) throws IOException {
		if (data != null) {
			output.write(data.getBytes(encoding));
		}
	}
}
