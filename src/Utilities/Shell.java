package Utilities;

import java.io.IOException;
import java.io.PrintWriter;

import DataBase.DBApp;
import Exceptions.DBAppException;
import Parser.Parser;

import jline.ConsoleReader;

public class Shell {
	private char mask = 0;
	private PrintWriter out;
	private ConsoleReader reader;
	private Parser parser;

	public Shell() throws IOException {
		out = new PrintWriter(System.out);
		reader = new ConsoleReader();
	}

	public void start() throws IOException {
		parser = new Parser(this);
		DBApp.getInstance();
		while (true) {
			try {
				readHandler(read());
			} catch (UnsupportedOperationException e) {
				write("Error");
			} catch (Exception e) {
				if (e.getMessage() != null)
					write(e.getMessage());
				else
					e.printStackTrace();
			}
		}
	}

	public void write(String message) throws IOException {
		out.println(message);
		out.println();
		out.flush();
	}

	private String read() throws IOException {
		return reader.readLine("Console-> ", mask);
	}

	private void readHandler(String line) throws DBAppException {
		if (line.matches("([Qq][Uu]|[Ee][Xx])[Ii][Tt]\\s*"))
			System.exit(0);
		if (line.matches("read file\\s+.*")) {
			line = line.replaceFirst("read file\\s+", "");
			parser.parseFile(line);
			return;
		}
		parser.parse(line);
	}
}
