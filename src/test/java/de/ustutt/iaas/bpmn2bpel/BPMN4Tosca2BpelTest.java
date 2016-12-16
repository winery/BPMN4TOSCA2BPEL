package de.ustutt.iaas.bpmn2bpel;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.ustutt.iaas.bpmn2bpel.parser.ParseException;
import de.ustutt.iaas.bpmn2bpel.planwriter.PlanWriterException;


public class BPMN4Tosca2BpelTest {
	
	protected static String RESOURCES_DIR = "src/test/resources/bpmn4tosca";

	
	@Before
	public void setUp() throws Exception {
		
	}
	
	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testTransform() throws ParseException, PlanWriterException, MalformedURLException, URISyntaxException {
		URI srcUri = Paths.get(RESOURCES_DIR, "bppmn4tosca.json").toUri();
		URI targetUri = Paths.get(RESOURCES_DIR, "managementplan.zip").toUri();
		BPMN4Tosca2BpelTest.class.getResource(".");
		BPMN4Tosca2BPEL transformer = new BPMN4Tosca2BPEL();
		transformer.transform(srcUri, targetUri); 
	}
	
}
