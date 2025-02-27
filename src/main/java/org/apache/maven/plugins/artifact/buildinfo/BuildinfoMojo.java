/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.maven.plugins.artifact.buildinfo;

import java.util.Map;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProjectHelper;

/**
 * Creates a buildinfo file recording build environment and output, as specified in
 * <a href="https://reproducible-builds.org/docs/jvm/">Reproducible Builds for the JVM</a>
 * for mono-module build, and extended for multi-module build.
 */
@Mojo(name = "buildinfo", defaultPhase = LifecyclePhase.VERIFY, threadSafe = true)
public class BuildinfoMojo extends AbstractBuildinfoMojo {
    /**
     * Specifies whether to attach the generated buildinfo file to the project.
     */
    @Parameter(property = "buildinfo.attach", defaultValue = "true")
    private boolean attach;

    /**
     * Used for attaching the buildinfo file in the project.
     */
    @Component
    private MavenProjectHelper projectHelper;

    @Override
    public void execute(Map<Artifact, String> artifacts) throws MojoExecutionException {
        // eventually attach
        if (attach) {
            getLog().info("Attaching buildinfo");
            projectHelper.attachArtifact(project, "buildinfo", buildinfoFile);
        } else {
            getLog().info("NOT adding buildinfo to the list of attached artifacts.");
        }
    }
}
