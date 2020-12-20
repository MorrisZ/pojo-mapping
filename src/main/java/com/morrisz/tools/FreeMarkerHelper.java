package com.morrisz.tools;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.maven.plugin.logging.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * @author zhangyoumao
 */
public class FreeMarkerHelper {

    private Configuration conf;

    public FreeMarkerHelper() throws URISyntaxException, IOException {
        conf = new Configuration(Configuration.VERSION_2_3_28);
        conf.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_28));
        conf.setClassForTemplateLoading(this.getClass(), "/ftl");
    }

    public void processToFile(String templateName, Map<String, Object> data, File outFile) throws IOException, TemplateException {
        Template template = conf.getTemplate(templateName);
        FileWriter fileWriter = new FileWriter(outFile);
        template.process(data, fileWriter);
    }

    public void processToLogger(String templateName, Map<String, Object> data, Log logger) throws Exception {
        Template template = conf.getTemplate(templateName);
        StringWriter sw = new StringWriter();
        template.process(data, sw);
        logger.info("model output\n " + sw.toString());
    }
}
