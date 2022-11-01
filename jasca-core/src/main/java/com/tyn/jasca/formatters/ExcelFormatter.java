package com.tyn.jasca.formatters;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.io.IOUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tyn.jasca.Formatter;
import com.tyn.jasca.JascaException;
import com.tyn.jasca.Violation;
import com.tyn.jasca.common.Utils;

/**
 * 
 * @author S.J.H.
 */
public class ExcelFormatter implements Formatter {
	
	/**
	 * 
	 */
	private static final Logger log = LoggerFactory.getLogger(ExcelFormatter.class);
	
	
	private static final String TEMPLATE_DIR = "jasca-excel";
	private static final String DEFAULT_TEMPLATE = "default.xlsx";
	
	
	private String input = null;
	private String output = null;
	
	List<Violation> violations = new ArrayList<Violation>();
	
	@Override
	public void setInput(String input) {
		this.input = input;
	}

	@Override
	public void setOutput(String output) {
		this.output = output;
	}

	@Override
	public void start() {
		if (Utils.isEmpty(output)) {
			throw new JascaException("분석 결과를 저장할 파일명이 입력되지 않음");
		}
		
		violations = new ArrayList<Violation>();
		
		log.debug("Excel 레포트 생성 시작");
	}

	@Override
	public void writeDocumentHead() throws IOException {
		
	}

	@Override
	public void writeViolationHead() throws IOException {
		
	}

	@Override
	public void writeViolationBody(Violation violation) throws IOException {
		violations.add(violation);
	}

	@Override
	public void writeViolationTail() throws IOException {
		
	}

	@Override
	public void writeDocumentTail() throws IOException {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("project", input);
		model.put("date", Utils.getCurrDatetime());
		model.put("violations", violations);
		
		InputStream inputStream = null;
		OutputStream outputStream = null;
		
		try {
			inputStream = getClass().getClassLoader().getResourceAsStream(TEMPLATE_DIR + "/" + DEFAULT_TEMPLATE);
			outputStream = new BufferedOutputStream(new FileOutputStream(output));
			
			XLSTransformer xlsTransformer = new XLSTransformer();
			Workbook workbook = xlsTransformer.transformXLS(inputStream, model);
			
			workbook.write(outputStream);
			
			outputStream.flush();
		}
		catch (InvalidFormatException ife) {
			throw new JascaException("엑셀 템플릿 오류", ife);
		}
		finally {
			IOUtils.closeQuietly(inputStream);
			IOUtils.closeQuietly(outputStream);
		}
	}

	@Override
	public void finish() {
		violations.clear();
		violations = null;
		
		log.debug("Excel 레포트 생성 끝");
	}
}
