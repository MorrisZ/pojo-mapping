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

    @Parameter(required = true, property = "modelCls")
    private String modelClassName;

    @Parameter(required = true, property = "out")
    private String out;

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

            File outFile = new File(out);
            FreeMarkerHelper freeMarkerHelper = new FreeMarkerHelper();
            freeMarkerHelper.processToFile("model.ftl", data, outFile);
            getLog().info("generated file: " + outFile.getAbsolutePath());
        } catch (Exception e) {
            getLog().error(e);
            throw new MojoExecutionException(e.toString());
        }
    }

}
