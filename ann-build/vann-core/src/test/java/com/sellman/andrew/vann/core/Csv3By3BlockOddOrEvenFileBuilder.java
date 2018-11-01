package com.sellman.andrew.vann.core;

import java.io.PrintWriter;

public class Csv3By3BlockOddOrEvenFileBuilder {
	private static final String DOUBLE_QUOTE = "\"";
	private static final String COMMMA = ",";

	public static void main(String[] args) throws Exception {
		PrintWriter writer = new PrintWriter("c:\\temp\\3by3BlockOddOrEven.csv");
		addHeader(writer);
		addAllPossibleCases(writer);
		writer.close();
	}

	private static void addHeader(PrintWriter writer) {
		StringBuilder line = new StringBuilder();
		addToLine(line, "a");
		addToLine(line, "b");
		addToLine(line, "c");
		addToLine(line, "d");
		addToLine(line, "e");
		addToLine(line, "f");
		addToLine(line, "g");
		addToLine(line, "h");
		addToLine(line, "i");
		addToLine(line, "label");
		writer.println(line);
	}

	private static void addToLine(StringBuilder line, String s) {
		line.append(DOUBLE_QUOTE);
		line.append(s);
		line.append(DOUBLE_QUOTE);
		line.append(COMMMA);
	}

	private static void addToLine(StringBuilder line, OPTION a, OPTION b, OPTION c, OPTION d, OPTION e, OPTION f, OPTION g, OPTION h, OPTION i, String label) {
		addToLine(line, a.toString());
		addToLine(line, b.toString());
		addToLine(line, c.toString());
		addToLine(line, d.toString());
		addToLine(line, e.toString());
		addToLine(line, f.toString());
		addToLine(line, g.toString());
		addToLine(line, h.toString());
		addToLine(line, i.toString());
		addToLine(line, label);
	}

	private static void addAllPossibleCases(PrintWriter writer) {
		for (OPTION a : OPTION.values()) {
			for (OPTION b : OPTION.values()) {
				for (OPTION c : OPTION.values()) {
					for (OPTION d : OPTION.values()) {
						for (OPTION e : OPTION.values()) {
							for (OPTION f : OPTION.values()) {
								for (OPTION g : OPTION.values()) {
									for (OPTION h : OPTION.values()) {
										for (OPTION i : OPTION.values()) {

											String label = "ODD";
											int total = a.ordinal() + b.ordinal() + c.ordinal() + d.ordinal() + e.ordinal() + f.ordinal() + g.ordinal() + h.ordinal() + i.ordinal();
											if (total % 2 == 0) {
												label = "EVEN";
											}

											StringBuilder line = new StringBuilder();
											addToLine(line, a, b, c, d, e, f, g, h, i, label);
											writer.println(line);
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	private enum OPTION {
		EMPTY, FULL;
	}

}
