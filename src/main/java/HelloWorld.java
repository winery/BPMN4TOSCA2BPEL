import java.io.StringWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;

import de.ustutt.iaas.bpmn2bpel.planwriter.BpelPlanArtefactWriter;

/**
 * Hello World Java class to demonstrate the usage of the sonar plugin
 *
 * Please delete this file in your repository
 */
public class HelloWorld {

	public static void main(String[] args) {
		
//		String str = "Ubuntu.VM.katze";
//		System.out.println(str.split("\\.", 0)[0]);
//		System.out.println(str.split("\\.", 0)[1]);
		
//		
//		BpelPlanWriter planWriter = new BpelPlanWriter();
//		planWriter.writePlan(null, null);
		
//		Path currentRelativePath = Paths.get("");
//		//System.out.println(currentRelativePath.toAbsolutePath().toString());
//		System.out.println(HelloWorld.class.getResource("./"));
		//Velocity.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, "/src/main/resources/templates");
		Velocity.init();

		VelocityContext context = new VelocityContext();
		

		context.put("name", new String("Doni"));

		Template template = null;

		try {
			template = Velocity.getTemplate("./src/main/resources/templates/mytemplate.vm");
			StringWriter sw = new StringWriter();
			template.merge( context, sw );
			template.merge( context, sw );
			
			System.out.println( sw.toString() ); 
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}