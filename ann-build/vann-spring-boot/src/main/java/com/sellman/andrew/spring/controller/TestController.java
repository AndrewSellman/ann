package com.sellman.andrew.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sellman.andrew.ann.core.math.MathOperations;
import com.sellman.andrew.ann.core.math.Vector;
import com.sellman.andrew.spring.config.MathOperationsBeanNames;

@RestController
public class TestController {
	private static final String HTML_TEMPLATE = "<table cellpadding=\"10\"><tr><td valign=\"top\">left</td><td valign=\"center\">operator</td><td valign=\"top\">right</td><td valign=\"center\">=</td><td valign=\"top\">answer</td></tr></table>";

	@Autowired
	@Qualifier(MathOperationsBeanNames.HIGH_PRIORITY_WAIT_FOR_COMPLETION)
	private MathOperations mathOps;

	@RequestMapping(value = "/ann/test/math/ops/add/vector")
	public String addVectors(@RequestParam("left") String left, @RequestParam("right") String right) {
		Vector l = getVector(left);
		Vector r = getVector(right);
		Vector answer = mathOps.add(l, r);

		String htmlLeft = replaceLineBreakWithHtmlBreak(l.toString());
		String htmlRight = replaceLineBreakWithHtmlBreak(r.toString());
		String htmlAnswer = replaceLineBreakWithHtmlBreak(answer.toString());
		return populateHtmlTemplate(htmlLeft, htmlRight, htmlAnswer, "+");
	}

	private Vector getVector(String values) {
		String[] splitValues = values.split(",");
		Vector v = new Vector(splitValues.length);
		for (int i = 0; i < splitValues.length; i++) {
			v.setValue(i, Double.parseDouble(splitValues[i]));
		}

		return v;
	}

	private String replaceLineBreakWithHtmlBreak(String s) {
		return s.replaceAll("\n", "<br>");
	}

	private String populateHtmlTemplate(String htmlLeft, String htmlRight, String htmlAnswer, String operator) {
		return HTML_TEMPLATE.replaceAll("left", htmlLeft).replaceAll("operator", operator).replaceAll("right", htmlRight).replaceAll("answer", htmlAnswer);
	}


}
