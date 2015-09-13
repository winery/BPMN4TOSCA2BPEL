package de.ustutt.iaas.bpmn2bpel;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.ustutt.iaas.bpmn2bpel.parser.ParseException;
import de.ustutt.iaas.bpmn2bpel.planwriter.PlanWriterException;


public class BPMN4Tosca2BpelTest {
	
	@Before
	public void setUp() throws Exception {
	}
	
	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testTransform() throws ParseException, PlanWriterException, MalformedURLException, URISyntaxException {
		URI srcUrl = new URI("file:C:/Users/wagnerse/Documents/OpenTOSCA/BPMN4TOSCA2BPEL/BPMN4TOSCA2BPEL/src/test/resources/bpmn4tosca/bppmn4tosca.json");
		URI targetUrl = new URI("file:C:/temp/bpmn4tosca/plan/");
		
		BPMN4Tosca2BPEL transformer = new BPMN4Tosca2BPEL();
		transformer.transform(srcUrl, targetUrl);
	}
	
}
