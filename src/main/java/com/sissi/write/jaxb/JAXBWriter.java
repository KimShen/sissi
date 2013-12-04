package com.sissi.write.jaxb;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sissi.context.JIDContext;
import com.sissi.protocol.Element;
import com.sissi.write.WriteWithOutClose;
import com.sissi.write.Writer;
import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

/**
 * @author Kim.shen 2013-10-16
 */
public class JAXBWriter implements Writer {

	private final static Log LOG = LogFactory.getLog(JAXBWriter.class);

	private final static String MAPPING_PROPERTY = "com.sun.xml.bind.namespacePrefixMapper";

	private final static JAXBContext CONTEXT;

	static {
		try {
			List<Class<?>> clazz = new ArrayList<Class<?>>();
			for (Class<?> each : ScanUtil.getClasses("com.sissi.protocol")) {
				if (each.getAnnotation(XmlRootElement.class) != null) {
					clazz.add(each);
				}
			}
			LOG.info("All classes in JAXB Context: " + clazz);
			CONTEXT = JAXBContext.newInstance(clazz.toArray(new Class[] {}));
		} catch (Exception e) {
			LOG.error(e);
			throw new RuntimeException("Can't init JAXB context", e);
		}
	}

	private final NamespacePrefixMapper mapper;

	public JAXBWriter() {
		super();
		this.mapper = new JAXBNamespaceMapping();
	}

	public JAXBWriter(NamespacePrefixMapper mapper) {
		super();
		this.mapper = mapper;
	}

	public void write(JIDContext context, Element node, OutputStream output) throws IOException {
		BufferedOutputStream bufferOut = new BufferedOutputStream(output);
		try {
			if (WriteWithOutClose.class.isAssignableFrom(node.getClass())) {
				this.writeWithOutClose(context, node, bufferOut);
			} else {
				this.writeWithFull(context, node, bufferOut);
			}
		} finally {
			bufferOut.close();
		}
	}

	public void writeWithFull(JIDContext context, Element node, OutputStream output) throws IOException {
		try {
			Marshaller marshaller = this.generateMarshaller(false);
			if (LOG.isInfoEnabled()) {
				StringBufferWriter bufferTemp = new StringBufferWriter(new StringWriter());
				marshaller.marshal(node, bufferTemp);
				bufferTemp.flush();
				String content = bufferTemp.toString();
				LOG.info("Write on " + (context.getJid() != null ? context.getJid().asString() : "N/A") + " " + content);
				output.write(content.getBytes("UTF-8"));
			} else {
				marshaller.marshal(node, output);
			}
			output.flush();
		} catch (JAXBException e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(e);
				e.printStackTrace();
			}
		}
	}

	private void writeWithOutClose(JIDContext context, Element node, OutputStream output) throws IOException {
		try {
			Marshaller marshaller = generateMarshaller(true);
			LinkedList<String> contents = this.prepareToLines(node, marshaller);
			contents.removeLast();
			StringBuffer sb = new StringBuffer();
			for (String each : contents) {
				sb.append(each);
			}
			LOG.info("Write on " + (context.getJid() != null ? context.getJid().asString() : "N/A") + " " + sb.toString());
			output.write(sb.toString().getBytes("UTF-8"));
			output.flush();
		} catch (Exception e) {
			if (LOG.isErrorEnabled()) {
				LOG.error(e);
				e.printStackTrace();
			}
		}
	}

	private LinkedList<String> prepareToLines(Element node, Marshaller marshaller) throws JAXBException, IOException {
		ByteArrayOutputStream prepare = new ByteArrayOutputStream();
		marshaller.marshal(node, prepare);
		LineIterator iterator = IOUtils.lineIterator(new ByteArrayInputStream(prepare.toByteArray()), "UTF-8");
		LinkedList<String> contents = new LinkedList<String>();
		while (iterator.hasNext()) {
			contents.add(iterator.next().trim());
		}
		return contents;
	}

	private Marshaller generateMarshaller(Boolean withOutClose) throws JAXBException, PropertyException {
		Marshaller marshaller = CONTEXT.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
		marshaller.setProperty(MAPPING_PROPERTY, mapper);
		if (withOutClose) {
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		}
		return marshaller;
	}

	private class StringBufferWriter extends BufferedWriter {

		private StringWriter out;

		public StringBufferWriter(StringWriter out) {
			super(out);
			this.out = out;
		}

		public String toString() {
			return this.out.toString();
		}
	}

	private static class ScanUtil {

		public static Set<Class<?>> getClasses(String pack) {
			Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
			boolean recursive = true;
			String packageName = pack;
			String packageDirName = packageName.replace('.', '/');
			Enumeration<URL> dirs;
			try {
				dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
				while (dirs.hasMoreElements()) {
					URL url = dirs.nextElement();
					String protocol = url.getProtocol();
					if ("file".equals(protocol)) {
						String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
						findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes);
					} else if ("jar".equals(protocol)) {
						JarFile jar;
						try {
							jar = ((JarURLConnection) url.openConnection()).getJarFile();
							Enumeration<JarEntry> entries = jar.entries();
							while (entries.hasMoreElements()) {
								JarEntry entry = entries.nextElement();
								String name = entry.getName();
								if (name.charAt(0) == '/') {
									name = name.substring(1);
								}
								if (name.startsWith(packageDirName)) {
									int idx = name.lastIndexOf('/');
									if (idx != -1) {
										packageName = name.substring(0, idx).replace('/', '.');
									}
									if ((idx != -1) || recursive) {
										if (name.endsWith(".class") && !entry.isDirectory()) {
											String className = name.substring(packageName.length() + 1, name.length() - 6);
											try {
												classes.add(Class.forName(packageName + '.' + className));
											} catch (ClassNotFoundException e) {
												e.printStackTrace();
											}
										}
									}
								}
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return classes;
		}

		public static void findAndAddClassesInPackageByFile(String packageName, String packagePath, final boolean recursive, Set<Class<?>> classes) {
			File dir = new File(packagePath);
			if (!dir.exists() || !dir.isDirectory()) {
				return;
			}
			File[] dirfiles = dir.listFiles(new FileFilter() {
				public boolean accept(File file) {
					return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));
				}
			});
			for (File file : dirfiles) {
				if (file.isDirectory()) {
					findAndAddClassesInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), recursive, classes);
				} else {
					String className = file.getName().substring(0, file.getName().length() - 6);
					try {
						classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className));
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
