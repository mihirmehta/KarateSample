package KarateSample;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.intuit.karate.Results;
import com.intuit.karate.Runner;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;

public class TestRunner {
	@org.junit.Test
	public void testParallel() {
		String karateOutputPath = "target/cucumber-html-reports";
		Results stats = Runner.parallel(getClass(), 1, karateOutputPath);
		generateReport(karateOutputPath);
		assertTrue("there are scenario failures", stats.getFailCount() == 0);

	}

	private static void generateReport(String karateOutputPath) {

		Collection<File> jsonFiles = FileUtils.listFiles(new File(karateOutputPath), new String[] { "json" }, true);
		List<String> jsonPaths = new ArrayList<String>(jsonFiles.size());
		jsonFiles.forEach(file -> jsonPaths.add(file.getAbsolutePath()));

		jsonPaths.forEach(path -> {
			Path fullPath = Paths.get(path);
			try {
				String content = new String(Files.readAllBytes(fullPath));

				// content = content.replaceAll("(Authorization: )([ ]*[0-9A-Za-z-._=]*[ ]*)*",
				// "Authorization: xxx");

				// System.out.println(content);
				Files.write(fullPath, content.getBytes());

			} catch (IOException e) {
				e.printStackTrace();
			}

		});
		Configuration config = new Configuration(new File("target"), "Karate Sample Test");

		ReportBuilder reportBuilder = new ReportBuilder(jsonPaths, config);
		reportBuilder.generateReports();
	}
}
