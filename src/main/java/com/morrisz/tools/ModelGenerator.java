package com.morrisz.tools;


import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.descriptor.PluginDescriptor;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author zhangyoumao
 */
@Mojo(name = "genModel", defaultPhase = LifecyclePhase.NONE, requiresDependencyResolution = ResolutionScope.RUNTIME)
public class ModelGenerator extends AbstractMojo {

    @Component
    private MavenProject mavenProject;

    @Component
    private PluginDescriptor pluginDescriptor;

    @Parameter(required = true, property = "class")
    private String modelClassName;

    /**
     * output to Logger by default
     */
    @Parameter(required = false, property = "out")
    private String out;

    /**
     * using extjs style by default
     */
    @Parameter(required = false, property = "style")
    private String style;

    public void execute() throws MojoExecutionException {
        getLog().info("start executing genModel");
        try {
            List<String> list = mavenProject.getRuntimeClasspathElements();
            ClassLoaderHelper classLoaderHelper = new ClassLoaderHelper(list);
            Class<?> cls = classLoaderHelper.loadClass(modelClassName);
            getLog().info(cls.toString());

            ReflectionHelper reflectionHelper = new ReflectionHelper();
            Map<String, Object> data = reflectionHelper.reflect(cls);
            data.put("pluginVersion", pluginDescriptor.getVersion());

            String templateName = "extjs.ftl";
            if (style != null) {
                templateName = style;
            }

            FreeMarkerHelper freeMarkerHelper = new FreeMarkerHelper();
            if (out != null) {
                File outFile = new File(out);
                freeMarkerHelper.processToFile(templateName, data, outFile);
                getLog().info("generated file: " + outFile.getAbsolutePath());
            } else {
                freeMarkerHelper.processToLogger(templateName, data, getLog());
            }
        } catch (Exception e) {
            getLog().error(e);
            throw new MojoExecutionException(e.toString());
        }
    }

}
