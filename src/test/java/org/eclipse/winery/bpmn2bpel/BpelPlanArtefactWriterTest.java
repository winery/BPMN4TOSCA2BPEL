/**
 *
 */
package org.eclipse.winery.bpmn2bpel;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import org.eclipse.winery.bpmn2bpel.planwriter.BpelPlanArtefactWriter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.eclipse.winery.bpmn2bpel.model.ManagementFlow;
import org.eclipse.winery.bpmn2bpel.parser.Bpmn4JsonParser;
import org.eclipse.winery.bpmn2bpel.parser.ParseException;


/**
 * Copyright 2015 IAAS University of Stuttgart <br>
 * <br>
 *
 * @author Sebastian Wagner
 *
 */
public class BpelPlanArtefactWriterTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link BpelPlanArtefactWriter#writePlan(ManagementFlow, java.net.URL)}.
	 * @throws MalformedURLException
	 * @throws ParseException
	 * @throws URISyntaxException
	 */
	@Test
	public void testWritePlan() throws MalformedURLException, ParseException, URISyntaxException {
		Bpmn4JsonParser parser = new Bpmn4JsonParser();
		URI uri = Paths.get("src/test/resources/bpmn4tosca/bppmn4tosca.json").toUri();
		//Path testBpmn4JsonFile = Paths.get("C:/temp/bpmn4tosca/bppmn4tosca.json");
		ManagementFlow mngmtFlow = parser.parse(uri);

//		BpelPlanArtefactWriter writer = new BpelPlanArtefactWriter();
//		writer.writePlan(mngmtFlow, null);
	}

}
