package com.morrisz.tools;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.net.URL;
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
}
